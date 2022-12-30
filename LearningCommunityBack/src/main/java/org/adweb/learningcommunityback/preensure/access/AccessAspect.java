package org.adweb.learningcommunityback.preensure.access;

import lombok.extern.slf4j.Slf4j;
import org.adweb.learningcommunityback.entity.db.*;
import org.adweb.learningcommunityback.exception.AdWebBaseException;
import org.adweb.learningcommunityback.preensure.chapter.ChapterID;
import org.adweb.learningcommunityback.preensure.course.CourseCode;
import org.adweb.learningcommunityback.preensure.homework.HomeworkID;
import org.adweb.learningcommunityback.preensure.section.SectionID;
import org.adweb.learningcommunityback.preensure.user.StudentUsername;
import org.adweb.learningcommunityback.preensure.user.TAUsername;
import org.adweb.learningcommunityback.preensure.user.TeacherUsername;
import org.adweb.learningcommunityback.preensure.user.Username;
import org.adweb.learningcommunityback.repository.*;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Aspect
@Component
@Slf4j
public class AccessAspect {

    @Resource
    public Take2Repository take2Repository;

    @Resource
    public UserRepository userRepository;

    @Resource
    public CourseRepository courseRepository;

    @Resource
    public ChapterRepository chapterRepository;

    @Resource
    public SectionRepository sectionRepository;

    @Resource
    public HomeworkRepository homeworkRepository;

    @Pointcut("@annotation(EnsureStudentHasCourseAccess)")
    public void ensureStudentHasCourseAccessPointCut() {
    }

    @Pointcut("@annotation(EnsureStudentNotHasCourseAccess)")
    public void ensureStudentNotHasCourseAccessPointCut() {
    }

    @Pointcut("@annotation(EnsureTAHasCourseAccess)")
    public void ensureTAHasCourseAccessPointCut() {
    }

    @Pointcut("@annotation(EnsureTANotHasCourseAccess)")
    public void ensureTANotHasCourseAccessPointCut() {
    }

    @Pointcut("@annotation(EnsureTeacherHasCourseAccess)")
    public void ensureTeacherHasCourseAccessPointCut() {
    }

    @Pointcut("@annotation(EnsureTeacherHasChapterAccess)")
    public void ensureTeacherHasChapterAccessPointCut() {
    }

    @Pointcut("@annotation(EnsureTeacherNotHasCourseAccess)")
    public void ensureTeacherNotHasCourseAccessPointCut() {
    }

    @Pointcut("@annotation(EnsureUserHasCourseAccess)")
    public void ensureUserHasCourseAccessPointCut() {
    }

    @Pointcut("@annotation(EnsureUserHasChapterAccess)")
    public void ensureUserHasChapterAccessPointCut() {
    }

    @Pointcut("@annotation(EnsureUserHasSectionAccess)")
    public void ensureUserHasSectionAccessPointCut() {
    }

    @Pointcut("@annotation(EnsureUserHasHomeworkAccess)")
    public void ensureUserHasHomeworkAccessPointCut() {
    }

    private Map<String, List<String>> getCourseCodeListAndUsernameListAndChapterIDList(JoinPoint point,
                                                                                       Class<? extends Annotation> usernameAnnotationClass) {
        List<String> usernameList = new ArrayList<>();
        List<String> courseCodeList = new ArrayList<>();
        List<String> chapterIDList = new ArrayList<>();
        List<String> sectionIDList = new ArrayList<>();
        List<String> homeworkIDList = new ArrayList<>();

        //获得被调用的方法对象
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();

        //获得形参列表
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {

            //如果这个参数被@CourseCode标注
            if (parameters[i].getAnnotation(CourseCode.class) != null) {
                courseCodeList.add(point.getArgs()[i].toString());
            }

            //如果这个参数被@ChapterID标注
            if (parameters[i].getAnnotation(ChapterID.class) != null) {
                chapterIDList.add(point.getArgs()[i].toString());
            }

            //如果这个参数被@SectionID标注
            if (parameters[i].getAnnotation(SectionID.class) != null) {
                sectionIDList.add(point.getArgs()[i].toString());
            }

            //如果这个参数被@HomeworkID标注
            if (parameters[i].getAnnotation(HomeworkID.class) != null) {
                homeworkIDList.add(point.getArgs()[i].toString());
            }

            //如果这个参数被@Username或@TAUsername或@StudentUsername或@TeacherUsername标注
            if (parameters[i].getAnnotation(usernameAnnotationClass) != null) {
                usernameList.add(point.getArgs()[i].toString());
            }
        }

        Map<String, List<String>> map = new HashMap<>();
        map.put("usernameList", usernameList);
        map.put("courseCodeList", courseCodeList);
        map.put("chapterIDList", chapterIDList);
        map.put("sectionIDList", sectionIDList);
        map.put("homeworkIDList", homeworkIDList);

        return map;
    }

    @Before("ensureStudentHasCourseAccessPointCut()")
    public void ensureStudentHasCourseAccess(JoinPoint point) {
        var res = getCourseCodeListAndUsernameListAndChapterIDList(point, StudentUsername.class);
        List<String> usernameList = res.get("usernameList");
        List<String> courseCodeList = res.get("courseCodeList");

        log.info(String.format("尝试确保学生%s是否有课程%s的权限", usernameList.toString(), courseCodeList.toString()));

        for (int i = 0; i < usernameList.size(); i++) {
            String username = usernameList.get(i);
            User user = userRepository.findByUsername(username);
            if (user == null || !User.ROLE_STUDENT.equals(user.getRole())) {
                throw new AdWebBaseException(IllegalArgumentException.class, "STUDENT_NOT_EXISTS", String.format("学生%s不存在", username));
            }
            for (int j = 0; j < courseCodeList.size(); j++) {
                String courseCode = courseCodeList.get(i);
                Course course = courseRepository.findByCourseCode(courseCode);

                if (course == null) {
                    throw new AdWebBaseException(IllegalArgumentException.class, "COURSE_NOT_EXISTS", String.format("课程%s不存在", courseCode));
                }

                Take2 take2 = take2Repository.findByUserIDAndCourseID(user.getId(), course.getId());

                if (take2 == null) {
                    throw new AdWebBaseException(IllegalArgumentException.class, "ACCESS_ERROR", String.format("学生%s还没有课程%s的访问权限", username, courseCode));
                }
            }
        }
    }

    @Before("ensureStudentNotHasCourseAccessPointCut()")
    public void ensureStudentNotHasCourseAccess(JoinPoint point) {
        var res = getCourseCodeListAndUsernameListAndChapterIDList(point, StudentUsername.class);
        List<String> usernameList = res.get("usernameList");
        List<String> courseCodeList = res.get("courseCodeList");

        log.info(String.format("尝试确保学生%s是否没有课程%s的权限", usernameList.toString(), courseCodeList.toString()));

        for (int i = 0; i < usernameList.size(); i++) {
            String username = usernameList.get(i);
            User user = userRepository.findByUsername(username);
            if (user == null || !User.ROLE_STUDENT.equals(user.getRole())) {
                throw new AdWebBaseException(IllegalArgumentException.class, "STUDENT_NOT_EXISTS", String.format("学生%s不存在", username));
            }
            for (int j = 0; j < courseCodeList.size(); j++) {
                String courseCode = courseCodeList.get(i);
                Course course = courseRepository.findByCourseCode(courseCode);

                if (course == null) {
                    throw new AdWebBaseException(IllegalArgumentException.class, "COURSE_NOT_EXISTS", String.format("课程%s不存在", courseCode));
                }

                Take2 take2 = take2Repository.findByUserIDAndCourseID(user.getId(), course.getId());

                if (take2 != null) {
                    throw new AdWebBaseException(IllegalArgumentException.class, "ACCESS_ERROR", String.format("学生%s已经有课程%s的访问权限", username, courseCode));
                }
            }
        }
    }

    @Before("ensureTAHasCourseAccessPointCut()")
    public void ensureTAHasCourseAccess(JoinPoint point) {
        var res = getCourseCodeListAndUsernameListAndChapterIDList(point, TAUsername.class);
        List<String> usernameList = res.get("usernameList");
        List<String> courseCodeList = res.get("courseCodeList");

        log.info(String.format("尝试确保助教%s是否有课程%s的权限", usernameList.toString(), courseCodeList.toString()));

        for (int i = 0; i < usernameList.size(); i++) {
            String username = usernameList.get(i);
            User user = userRepository.findByUsername(username);
            if (user == null || !User.ROLE_TA.equals(user.getRole())) {
                throw new AdWebBaseException(IllegalArgumentException.class, "TA_NOT_EXISTS", String.format("助教%s不存在", username));
            }
            for (int j = 0; j < courseCodeList.size(); j++) {
                String courseCode = courseCodeList.get(i);
                Course course = courseRepository.findByCourseCode(courseCode);

                if (course == null) {
                    throw new AdWebBaseException(IllegalArgumentException.class, "COURSE_NOT_EXISTS", String.format("课程%s不存在", courseCode));
                }

                Take2 take2 = take2Repository.findByUserIDAndCourseID(user.getId(), course.getId());

                if (take2 == null) {
                    throw new AdWebBaseException(IllegalArgumentException.class, "ACCESS_ERROR", String.format("助教%s还没有课程%s的访问权限", username, courseCode));
                }
            }
        }
    }

    @Before("ensureTANotHasCourseAccessPointCut()")
    public void ensureTANotHasCourseAccess(JoinPoint point) {
        var res = getCourseCodeListAndUsernameListAndChapterIDList(point, TAUsername.class);
        List<String> usernameList = res.get("usernameList");
        List<String> courseCodeList = res.get("courseCodeList");

        log.info(String.format("尝试确保助教%s是否没有课程%s的权限", usernameList.toString(), courseCodeList.toString()));

        for (int i = 0; i < usernameList.size(); i++) {
            String username = usernameList.get(i);
            User user = userRepository.findByUsername(username);
            if (user == null || !User.ROLE_TA.equals(user.getRole())) {
                throw new AdWebBaseException(IllegalArgumentException.class, "TA_NOT_EXISTS", String.format("助教%s不存在", username));
            }
            for (int j = 0; j < courseCodeList.size(); j++) {
                String courseCode = courseCodeList.get(i);
                Course course = courseRepository.findByCourseCode(courseCode);

                if (course == null) {
                    throw new AdWebBaseException(IllegalArgumentException.class, "COURSE_NOT_EXISTS", String.format("课程%s不存在", courseCode));
                }

                Take2 take2 = take2Repository.findByUserIDAndCourseID(user.getId(), course.getId());

                if (take2 != null) {
                    throw new AdWebBaseException(IllegalArgumentException.class, "ACCESS_ERROR", String.format("助教%s已经有课程%s的访问权限", username, courseCode));
                }
            }
        }
    }

    @Before("ensureTeacherHasCourseAccessPointCut()")
    public void ensureTeacherHasCourseAccess(JoinPoint point) {
        var res = getCourseCodeListAndUsernameListAndChapterIDList(point, TeacherUsername.class);
        List<String> usernameList = res.get("usernameList");
        List<String> courseCodeList = res.get("courseCodeList");

        log.info(String.format("尝试确保教师%s是否有课程%s的权限", usernameList.toString(), courseCodeList.toString()));


        for (int i = 0; i < usernameList.size(); i++) {
            String username = usernameList.get(i);
            User user = userRepository.findByUsername(username);
            if (user == null || !User.ROLE_TEACHER.equals(user.getRole())) {
                throw new AdWebBaseException(IllegalArgumentException.class, "TEACHER_NOT_EXISTS", String.format("教师%s不存在", username));
            }
            for (int j = 0; j < courseCodeList.size(); j++) {
                String courseCode = courseCodeList.get(i);
                Course course = courseRepository.findByCourseCode(courseCode);

                if (course == null) {
                    throw new AdWebBaseException(IllegalArgumentException.class, "COURSE_NOT_EXISTS", String.format("课程%s不存在", courseCode));
                }

                Take2 take2 = take2Repository.findByUserIDAndCourseID(user.getId(), course.getId());

                if (take2 == null) {
                    throw new AdWebBaseException(IllegalArgumentException.class, "ACCESS_ERROR", String.format("教师%s还没有课程%s的访问权限", username, courseCode));
                }
            }
        }
    }

    @Before("ensureTeacherHasChapterAccessPointCut()")
    public void ensureTeacherHasChapterAccess(JoinPoint point) {
        var res = getCourseCodeListAndUsernameListAndChapterIDList(point, TeacherUsername.class);
        List<String> usernameList = res.get("usernameList");
        List<String> chapterIDList = res.get("chapterIDList");

        log.info(String.format("尝试确保教师%s是否有章%s的权限", usernameList.toString(), chapterIDList.toString()));


        for (int i = 0; i < usernameList.size(); i++) {
            String username = usernameList.get(i);
            User user = userRepository.findByUsername(username);
            if (user == null || !User.ROLE_TEACHER.equals(user.getRole())) {
                throw new AdWebBaseException(IllegalArgumentException.class, "TEACHER_NOT_EXISTS", String.format("教师%s不存在", username));
            }
            for (int j = 0; j < chapterIDList.size(); j++) {
                String chapterID = chapterIDList.get(i);

                Chapter chapter = chapterRepository.findByChapterID(chapterID);

                if (chapter == null){
                    throw new AdWebBaseException(IllegalArgumentException.class, "CHAPTER_NOT_EXISTS", String.format("章%s不存在", chapterID));
                }

                Course course = courseRepository.findById(chapter.getCourseID()).orElse(null);

                if (course == null) {
                    throw new AdWebBaseException(IllegalArgumentException.class, "COURSE_NOT_EXISTS", String.format("章%s对应的课程不存在", chapterID));
                }

                Take2 take2 = take2Repository.findByUserIDAndCourseID(user.getId(), course.getId());

                if (take2 == null) {
                    throw new AdWebBaseException(IllegalArgumentException.class, "ACCESS_ERROR", String.format("教师%s还没有章节%s的访问权限", username, chapterID));
                }
            }
        }
    }

    @Before("ensureTeacherNotHasCourseAccessPointCut()")
    public void ensureTeacherNotHasCourseAccess(JoinPoint point) {
        var res = getCourseCodeListAndUsernameListAndChapterIDList(point, TeacherUsername.class);
        List<String> usernameList = res.get("usernameList");
        List<String> courseCodeList = res.get("courseCodeList");

        log.info(String.format("尝试确保教师%s是否没有课程%s的权限", usernameList.toString(), courseCodeList.toString()));

        for (int i = 0; i < usernameList.size(); i++) {
            String username = usernameList.get(i);
            User user = userRepository.findByUsername(username);
            if (user == null || !User.ROLE_TEACHER.equals(user.getRole())) {
                throw new AdWebBaseException(IllegalArgumentException.class, "TEACHER_NOT_EXISTS", String.format("教师%s不存在", username));
            }
            for (int j = 0; j < courseCodeList.size(); j++) {
                String courseCode = courseCodeList.get(i);
                Course course = courseRepository.findByCourseCode(courseCode);

                if (course == null) {
                    throw new AdWebBaseException(IllegalArgumentException.class, "COURSE_NOT_EXISTS", String.format("课程%s不存在", courseCode));
                }

                Take2 take2 = take2Repository.findByUserIDAndCourseID(user.getId(), course.getId());

                if (take2 != null) {
                    throw new AdWebBaseException(IllegalArgumentException.class, "ACCESS_ERROR", String.format("教师%s已经有课程%s的访问权限", username, courseCode));
                }
            }
        }
    }

    @Before("ensureUserHasCourseAccessPointCut()")
    public void ensureUserHasCourseAccess(JoinPoint point) {
        var res = getCourseCodeListAndUsernameListAndChapterIDList(point, Username.class);
        List<String> usernameList = res.get("usernameList");
        List<String> courseCodeList = res.get("courseCodeList");

        log.info(String.format("尝试确保用户%s是否有课程%s的权限", usernameList.toString(), courseCodeList.toString()));


        for (int i = 0; i < usernameList.size(); i++) {
            String username = usernameList.get(i);
            User user = userRepository.findByUsername(username);
            if (user == null) {
                throw new AdWebBaseException(IllegalArgumentException.class, "USER_NOT_EXISTS", String.format("用户%s不存在", username));
            }
            for (int j = 0; j < courseCodeList.size(); j++) {
                String courseCode = courseCodeList.get(i);
                Course course = courseRepository.findByCourseCode(courseCode);

                if (course == null) {
                    throw new AdWebBaseException(IllegalArgumentException.class, "COURSE_NOT_EXISTS", String.format("课程%s不存在", courseCode));
                }

                Take2 take2 = take2Repository.findByUserIDAndCourseID(user.getId(), course.getId());

                if (take2 == null) {
                    throw new AdWebBaseException(IllegalArgumentException.class, "ACCESS_ERROR", String.format("用户%s还没有课程%s的访问权限", username, courseCode));
                }
            }
        }
    }

    @Before("ensureUserHasChapterAccessPointCut()")
    public void ensureUserHasChapterAccess(JoinPoint point) {
        var res = getCourseCodeListAndUsernameListAndChapterIDList(point, Username.class);
        List<String> usernameList = res.get("usernameList");
        List<String> chapterIDList = res.get("chapterIDList");

        log.info(String.format("尝试确保用户%s是否有章%s的权限", usernameList.toString(), chapterIDList.toString()));

        for (int i = 0; i < usernameList.size(); i++) {
            String username = usernameList.get(i);
            User user = userRepository.findByUsername(username);
            if (user == null) {
                throw new AdWebBaseException(IllegalArgumentException.class, "USER_NOT_EXISTS", String.format("用户%s不存在", username));
            }
            for (int j = 0; j < chapterIDList.size(); j++) {
                String chapterID = chapterIDList.get(i);

                Chapter chapter = chapterRepository.findByChapterID(chapterID);

                if (chapter == null){
                    throw new AdWebBaseException(IllegalArgumentException.class, "CHAPTER_NOT_EXISTS", String.format("章%s不存在", chapterID));
                }

                Course course = courseRepository.findById(chapter.getCourseID()).orElse(null);

                if (course == null) {
                    throw new AdWebBaseException(IllegalArgumentException.class, "COURSE_NOT_EXISTS", String.format("章%s对应的课程不存在", chapterID));
                }

                Take2 take2 = take2Repository.findByUserIDAndCourseID(user.getId(), course.getId());

                if (take2 == null) {
                    throw new AdWebBaseException(IllegalArgumentException.class, "ACCESS_ERROR", String.format("用户%s还没有章节%s的访问权限", username, chapterID));
                }
            }
        }
    }

    @Before("ensureUserHasSectionAccessPointCut()")
    public void ensureUserHasSectionAccess(JoinPoint point) {
        var res = getCourseCodeListAndUsernameListAndChapterIDList(point, Username.class);
        List<String> usernameList = res.get("usernameList");
        List<String> sectionIDList = res.get("sectionIDList");

        log.info(String.format("尝试确保用户%s是否有小节%s的权限", usernameList.toString(), sectionIDList.toString()));

        for (int i = 0; i < usernameList.size(); i++) {
            String username = usernameList.get(i);
            User user = userRepository.findByUsername(username);
            if (user == null) {
                throw new AdWebBaseException(IllegalArgumentException.class, "USER_NOT_EXISTS", String.format("用户%s不存在", username));
            }
            for (int j = 0; j < sectionIDList.size(); j++) {
                String sectionID = sectionIDList.get(i);

                Section section = sectionRepository.findBySectionID(sectionID);
                if (section == null){
                    throw new AdWebBaseException(IllegalArgumentException.class, "SECTION_NOT_EXISTS", String.format("小节%s不存在", sectionID));
                }

                Course course = courseRepository.findById(section.getCourseID()).orElse(null);

                if (course == null) {
                    throw new AdWebBaseException(IllegalArgumentException.class, "COURSE_NOT_EXISTS", String.format("小节%s对应的课程不存在", sectionID));
                }

                Take2 take2 = take2Repository.findByUserIDAndCourseID(user.getId(), course.getId());

                if (take2 == null) {
                    throw new AdWebBaseException(IllegalArgumentException.class, "ACCESS_ERROR", String.format("用户%s还没有章节%s的访问权限", username, sectionID));
                }
            }
        }
    }

    @Before("ensureUserHasHomeworkAccessPointCut()")
    public void ensureUserHasHomeworkAccess(JoinPoint point) {
        var res = getCourseCodeListAndUsernameListAndChapterIDList(point, Username.class);
        List<String> usernameList = res.get("usernameList");
        List<String> homeworkIDList = res.get("homeworkIDList");

        log.info(String.format("尝试确保用户%s是否有作业%s的权限", usernameList.toString(), homeworkIDList.toString()));

        for (int i = 0; i < usernameList.size(); i++) {
            String username = usernameList.get(i);
            User user = userRepository.findByUsername(username);
            if (user == null) {
                throw new AdWebBaseException(IllegalArgumentException.class, "USER_NOT_EXISTS", String.format("用户%s不存在", username));
            }
            for (int j = 0; j < homeworkIDList.size(); j++) {
                String homeworkID = homeworkIDList.get(i);

                Homework homework = homeworkRepository.findByHomeworkID(homeworkID);
                if (homework == null){
                    throw new AdWebBaseException(IllegalArgumentException.class, "HOMEWORK_NOT_EXISTS", String.format("作业%s不存在", homeworkID));
                }

                Course course = courseRepository.findByCourseCode(homework.getCourseCode());

                if (course == null) {
                    throw new AdWebBaseException(IllegalArgumentException.class, "COURSE_NOT_EXISTS", String.format("作业%s对应的课程不存在", homeworkID));
                }

                Take2 take2 = take2Repository.findByUserIDAndCourseID(user.getId(), course.getId());

                if (take2 == null) {
                    throw new AdWebBaseException(IllegalArgumentException.class, "ACCESS_ERROR", String.format("用户%s还没有作业%s的访问权限", username, homeworkID));
                }
            }
        }
    }
}
