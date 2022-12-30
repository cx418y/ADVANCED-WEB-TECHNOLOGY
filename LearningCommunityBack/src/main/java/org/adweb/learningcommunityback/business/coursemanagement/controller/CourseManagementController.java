package org.adweb.learningcommunityback.business.coursemanagement.controller;

import org.adweb.learningcommunityback.business.coursemanagement.dto.CourseDTO;
import org.adweb.learningcommunityback.business.coursemanagement.dto.MemberDTO;
import org.adweb.learningcommunityback.business.coursemanagement.request.AddCourseRequest;
import org.adweb.learningcommunityback.business.coursemanagement.request.AddTAOrRemoveStudentTARequest;
import org.adweb.learningcommunityback.business.coursemanagement.request.JoinOrExitCourseRequest;
import org.adweb.learningcommunityback.business.coursemanagement.service.CourseManagementService;
import org.adweb.learningcommunityback.entity.response.SimpleSuccessfulPostResponse;
import org.adweb.learningcommunityback.exception.AdWebBaseException;
import org.adweb.learningcommunityback.page.PageQueryResult;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
public class CourseManagementController {

    @Resource
    CourseManagementService courseManagementService;

    // 教师添加课程
    @PostMapping("/addCourse")
    @PreAuthorize("hasAnyAuthority('Teacher')")
    public SimpleSuccessfulPostResponse addCourse
    (@Valid @RequestBody AddCourseRequest request,
     BindingResult bindingResult,
     @AuthenticationPrincipal String username) {

        if (bindingResult.hasFieldErrors()) {
            throw new AdWebBaseException(IllegalArgumentException.class, "FIELD_ERROR", Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        return courseManagementService.addCourse(
                request.getCourseCode(),
                request.getCourseName(),
                request.getDescription(),
                username
        );
    }

    // 教师/助教/学生查看课程成员
    @GetMapping("/courseMember")
    @PreAuthorize("hasAnyAuthority('Teacher') || hasAnyAuthority('TA') || hasAnyAuthority('Student')")
    public List<MemberDTO> courseMember(@RequestParam("courseCode") String courseCode,
                                        @AuthenticationPrincipal String username) {
        return courseManagementService.courseMember(courseCode, username);
    }

    // 教师/助教/学生查看和自己关联的所有课程
    @GetMapping("/myCourse")
    @PreAuthorize("hasAnyAuthority('Teacher') || hasAnyAuthority('TA') || hasAnyAuthority('Student')")
    public List<CourseDTO> myCourse(@AuthenticationPrincipal String username) {
        return courseManagementService.myCourse(username);
    }

    @GetMapping("/myCourseByCourseCode")
    @PreAuthorize("hasAnyAuthority('Teacher') || hasAnyAuthority('TA') || hasAnyAuthority('Student')")
    public CourseDTO myCourseByCourseCode(@RequestParam("courseCode") String courseCode,
                                          @AuthenticationPrincipal String username) {
        return courseManagementService.myCourseByCourseCode(courseCode, username);
    }

    // 教师给课程添加助教
    @PostMapping("/addTAtoCourse")
    @PreAuthorize("hasAnyAuthority('Teacher')")
    public SimpleSuccessfulPostResponse addTAtoCourse(@Valid @RequestBody AddTAOrRemoveStudentTARequest request,
                                                      BindingResult bindingResult,
                                                      @AuthenticationPrincipal String teacherUsername) {
        if (bindingResult.hasFieldErrors()) {
            throw new AdWebBaseException(IllegalArgumentException.class, "FIELD_ERROR", Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        return courseManagementService.addTAtoCourse(request.getCourseCode(), teacherUsername, request.getUsername());
    }

    // 教师从课程中删除助教/学生
    @PostMapping("/removeCourseStudentTA")
    @PreAuthorize("hasAnyAuthority('Teacher')")
    public SimpleSuccessfulPostResponse removeCourseStudentTA(@Valid @RequestBody AddTAOrRemoveStudentTARequest request,
                                                              BindingResult bindingResult,
                                                              @AuthenticationPrincipal String teacherUsername) {
        if (bindingResult.hasFieldErrors()) {
            throw new AdWebBaseException(IllegalArgumentException.class, "FIELD_ERROR", Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        return courseManagementService.removeCourseStudentTA(request.getCourseCode(), teacherUsername, request.getUsername());
    }

    // 学生加入课程
    @PostMapping("/joinCourse")
    @PreAuthorize("hasAnyAuthority('Student')")
    public SimpleSuccessfulPostResponse joinCourse(@Valid @RequestBody JoinOrExitCourseRequest request,
                                                   BindingResult bindingResult,
                                                   @AuthenticationPrincipal String studentUsername) {
        if (bindingResult.hasFieldErrors()) {
            throw new AdWebBaseException(IllegalArgumentException.class, "FIELD_ERROR", Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        return courseManagementService.joinCourse(request.getCourseCode(), studentUsername);
    }

    // 学生退出课程
    @PostMapping("/exitCourse")
    @PreAuthorize("hasAnyAuthority('Student')")
    public SimpleSuccessfulPostResponse exitCourse(@Valid @RequestBody JoinOrExitCourseRequest request,
                                                   BindingResult bindingResult,
                                                   @AuthenticationPrincipal String studentUsername) {
        if (bindingResult.hasFieldErrors()) {
            throw new AdWebBaseException(IllegalArgumentException.class, "FIELD_ERROR", Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        return courseManagementService.exitCourse(request.getCourseCode(), studentUsername);
    }

    //查看搜索课程
    @GetMapping("/courses")
    @PreAuthorize("hasAnyAuthority('Student') || hasAnyAuthority('Teacher') || hasAnyAuthority('TA')")
    public PageQueryResult<CourseDTO> searchCourses(@RequestParam("courseName") String courseName,
                                                    @RequestParam("pageNum") int pageNum,
                                                    @RequestParam("pageSize") int pageSize,
                                                    @AuthenticationPrincipal String username){
        return courseManagementService.searchCourses(username, courseName, pageNum, pageSize);
    }
}
