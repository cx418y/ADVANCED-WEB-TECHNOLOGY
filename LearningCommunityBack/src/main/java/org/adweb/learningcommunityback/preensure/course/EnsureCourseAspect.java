package org.adweb.learningcommunityback.preensure.course;

import lombok.extern.slf4j.Slf4j;
import org.adweb.learningcommunityback.entity.db.Course;
import org.adweb.learningcommunityback.exception.AdWebBaseException;
import org.adweb.learningcommunityback.repository.CourseRepository;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

@Aspect
@Component
@Slf4j
public class EnsureCourseAspect {

    @Resource
    public CourseRepository courseRepository;


    @Pointcut("@annotation(EnsureCourseExists)")
    public void ensureCourseExistsPointCut() {
    }

    @Pointcut("@annotation(EnsureCourseNotExists)")
    public void ensureCourseNotExistsPointCut() {
    }


    /**
     * 找到方法上所有打了@Username注解的parameters的实参
     */
    private List<String> getCourseCodeListToBeEnsured(JoinPoint point) {
        //要检查是否在数据库中存在的用户名列表
        List<String> courseCodeList = new ArrayList<>();

        //获得被注解的方法的签名
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();

        //遍历Method的参数，找到注解有@Username的parameters
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {

            //如果这个参数被标上了@CourseCode......
            if (parameters[i].getAnnotation(CourseCode.class) != null) {
                Object arg = point.getArgs()[i];
                String courseCode = arg.toString();
                courseCodeList.add(courseCode);
            }
        }

        return courseCodeList;
    }

    @Before("ensureCourseExistsPointCut()")
    public void ensureCourseExists(JoinPoint point) {
        List<String> courseCodeList = getCourseCodeListToBeEnsured(point);

        log.info(String.format("正在检查课程%s是否存在", courseCodeList));

        //遍历usernameList检查
        courseCodeList.forEach(courseCode -> {
            Course course = courseRepository.findByCourseCode(courseCode);
            if (course == null) {
                throw new AdWebBaseException(IllegalArgumentException.class, "COURSE_NOT_EXISTS", "课程不存在");
            }
        });

    }

    @Before("ensureCourseNotExistsPointCut()")
    public void ensureCourseNotExists(JoinPoint point) {
        List<String> courseCodeList = getCourseCodeListToBeEnsured(point);

        log.info(String.format("正在检查课程%s是否不存在", courseCodeList));

        //遍历usernameList检查
        courseCodeList.forEach(courseCode -> {
            Course course = courseRepository.findByCourseCode(courseCode);
            if (course != null) {
                throw new AdWebBaseException(IllegalArgumentException.class, "COURSE_ALREADY_EXISTS", "课程已存在");
            }
        });
    }
}
