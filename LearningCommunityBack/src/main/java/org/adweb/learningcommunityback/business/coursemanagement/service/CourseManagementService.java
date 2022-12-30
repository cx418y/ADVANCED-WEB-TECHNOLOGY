package org.adweb.learningcommunityback.business.coursemanagement.service;

import org.adweb.learningcommunityback.business.coursemanagement.dto.CourseDTO;
import org.adweb.learningcommunityback.business.coursemanagement.dto.MemberDTO;
import org.adweb.learningcommunityback.entity.db.Course;
import org.adweb.learningcommunityback.entity.db.Take2;
import org.adweb.learningcommunityback.entity.db.User;
import org.adweb.learningcommunityback.entity.response.SimpleSuccessfulPostResponse;
import org.adweb.learningcommunityback.exception.AdWebBaseException;
import org.adweb.learningcommunityback.page.PageQueryResult;
import org.adweb.learningcommunityback.preensure.access.*;
import org.adweb.learningcommunityback.preensure.course.CourseCode;
import org.adweb.learningcommunityback.preensure.course.EnsureCourseExists;
import org.adweb.learningcommunityback.preensure.course.EnsureCourseNotExists;
import org.adweb.learningcommunityback.preensure.user.*;
import org.adweb.learningcommunityback.repository.CourseRepository;
import org.adweb.learningcommunityback.repository.Take2Repository;
import org.adweb.learningcommunityback.repository.UserRepository;
import org.adweb.learningcommunityback.utils.EntityDTOMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class CourseManagementService {

    @Resource
    UserRepository userRepository;

    @Resource
    CourseRepository courseRepository;

    @Resource
    Take2Repository take2Repository;

    // 教师添加课程
    @EnsureCourseNotExists
    @EnsureTeacherExists
    public SimpleSuccessfulPostResponse addCourse(@CourseCode String courseCode,
                                                  String courseName,
                                                  String description,
                                                  @TeacherUsername String teacherName) {
        User teacher = userRepository.findByUsername(teacherName);

        Course course = new Course(courseCode, courseName, description);

        courseRepository.save(course);

        Take2 take2 = new Take2(course.getId(), teacher.getId());

        take2Repository.save(take2);

        return new SimpleSuccessfulPostResponse("添加课程成功");
    }

    // 教师/助教/学生查看课程成员
    @EnsureCourseExists
    @EnsureUserHasCourseAccess
    public List<MemberDTO> courseMember(@CourseCode String courseCode,
                                        @Username String username) {
        Course course = courseRepository.findByCourseCode(courseCode);

        List<Take2> take2s = take2Repository.findAllByCourseID(course.getId());

        List<MemberDTO> memberDTOS = new ArrayList<>();

        take2s.forEach(take2 -> {
            User user = userRepository.findById(take2.getUserID()).orElse(null);

            EntityDTOMapper mapper = EntityDTOMapper.builder().setEntityClass(User.class).setDtoClass(MemberDTO.class).build();

            if (user != null) {
                memberDTOS.add((MemberDTO) mapper.map(user));
            }
        });

        return memberDTOS;
    }

    // 教师/助教/学生查看和自己关联的所有课程
    @EnsureUserExists
    public List<CourseDTO> myCourse(@Username String username) {
        User user = userRepository.findByUsername(username);
        List<Take2> take2s = take2Repository.findAllByUserID(user.getId());

        List<CourseDTO> courseDTOList = new ArrayList<>();

        take2s.forEach(take2 -> {
            Course course = courseRepository.findById(take2.getCourseID()).orElse(null);
            EntityDTOMapper mapper = EntityDTOMapper.builder().setEntityClass(Course.class).setDtoClass(CourseDTO.class).build();
            if (course != null) {
                courseDTOList.add((CourseDTO) mapper.map(course));
            }
        });

        return courseDTOList;
    }


    /**
     * 根据课程代码，获得我关于这个课程代码的课程
     *
     * @param courseCode
     * @param username
     * @return
     */
    @EnsureCourseExists
    @EnsureUserHasCourseAccess
    public CourseDTO myCourseByCourseCode(@CourseCode String courseCode,
                                          @Username String username) {
        Course course = courseRepository.findByCourseCode(courseCode);

        EntityDTOMapper mapper = EntityDTOMapper.builder().setEntityClass(Course.class).setDtoClass(CourseDTO.class).build();

        return (CourseDTO) mapper.map(course);
    }

    // 教师给课程添加助教
    @EnsureTeacherExists
    @EnsureCourseExists
    @EnsureTAExists
    @EnsureTeacherHasCourseAccess
    @EnsureTANotHasCourseAccess
    public SimpleSuccessfulPostResponse addTAtoCourse(@CourseCode String courseCode,
                                                      @TeacherUsername String teacherUsername,
                                                      @TAUsername String taUsername) {
        User ta = userRepository.findByUsername(taUsername);
        Course course = courseRepository.findByCourseCode(courseCode);

        Take2 take2 = new Take2(course.getId(), ta.getId());

        // 添加助教
        take2Repository.save(take2);

        return new SimpleSuccessfulPostResponse("添加助教成功");
    }

    // 教师从课程中删除助教/学生
    @EnsureCourseExists
    @EnsureTeacherExists
    @EnsureUserExists
    @EnsureTeacherHasCourseAccess
    public SimpleSuccessfulPostResponse removeCourseStudentTA(@CourseCode String courseCode,
                                                              @TeacherUsername String teacherUsername,
                                                              @Username String studentTAUsername) {
        User TAStudent = userRepository.findByUsername(studentTAUsername);
        Course course = courseRepository.findByCourseCode(courseCode);

        if (!TAStudent.getRole().equals(User.ROLE_STUDENT) && !TAStudent.getRole().equals(User.ROLE_TA)) {
            throw new AdWebBaseException(IllegalArgumentException.class, "ROLE_ERROR", "不能从课程中删除教师" + teacherUsername);
        }

        Take2 take2 = take2Repository.findByUserIDAndCourseID(TAStudent.getId(), course.getId());
        take2Repository.delete(take2);

        return new SimpleSuccessfulPostResponse("删除成功");
    }


    @EnsureCourseExists
    @EnsureStudentExists
    @EnsureStudentNotHasCourseAccess
    // 学生加入课程
    public SimpleSuccessfulPostResponse joinCourse(@CourseCode String courseCode,
                                                   @StudentUsername String studentUsername) {

        Course course = courseRepository.findByCourseCode(courseCode);
        User student = userRepository.findByUsername(studentUsername);

        Take2 take2 = new Take2(course.getId(), student.getId());

        take2Repository.save(take2);

        return new SimpleSuccessfulPostResponse("加入课程成功");
    }

    @EnsureCourseExists
    @EnsureStudentExists
    @EnsureStudentHasCourseAccess
    // 学生退出课程
    public SimpleSuccessfulPostResponse exitCourse(@CourseCode String courseCode,
                                                   @StudentUsername String username) {
        Course course = courseRepository.findByCourseCode(courseCode);

        User student = userRepository.findByUsername(username);

        Take2 take2 = take2Repository.findByUserIDAndCourseID(student.getId(), course.getId());

        take2Repository.delete(take2);

        return new SimpleSuccessfulPostResponse("退出课程成功");
    }

    @EnsureUserExists
    public PageQueryResult<CourseDTO> searchCourses(@Username String username, String courseName, int pageNum, int pageSize) {
        pageNum--;

        Page<Course> courses = null;
        if (courseName.isBlank()){//courseName为空则返回所有课程
            courses = courseRepository.findAll(PageRequest.of(pageNum, pageSize));
        }
        else {
            courses = courseRepository.findAllByCourseNameLike(courseName, PageRequest.of(pageNum, pageSize));
        }

        List<CourseDTO> data = courses.stream()
                .map(course -> {
                    return new CourseDTO(course.getCourseCode(), course.getCourseName(), course.getDescription());
                })
                .toList();

        int totalPage = courses.getTotalPages();

        return PageQueryResult.of(totalPage, data);
    }
}
