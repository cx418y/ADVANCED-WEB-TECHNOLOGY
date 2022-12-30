package org.adweb.learningcommunityback.preensure.user;

import lombok.extern.slf4j.Slf4j;
import org.adweb.learningcommunityback.entity.db.User;
import org.adweb.learningcommunityback.exception.AdWebBaseException;
import org.adweb.learningcommunityback.repository.UserRepository;
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
import java.util.List;

@Aspect
@Component
@Slf4j
public class EnsureUserAspect {

    @Resource
    public UserRepository userRepository;


    @Pointcut("@annotation(EnsureUserExists)")
    public void ensureUserExistsPointCut() {
    }

    @Pointcut("@annotation(EnsureUserNotExists)")
    public void ensureUserNotExistsPointCut() {
    }

    @Pointcut("@annotation(org.adweb.learningcommunityback.preensure.user.EnsureTeacherExists)")
    public void ensureTeacherExistsPointCut() {
    }

    @Pointcut("@annotation(org.adweb.learningcommunityback.preensure.user.EnsureTAExists)")
    public void ensureTAExistsPointCut() {
    }

    @Pointcut("@annotation(org.adweb.learningcommunityback.preensure.user.EnsureStudentExists)")
    public void ensureStudentExistsPointCut() {
    }


    /**
     * 找到方法上所有打了@Username注解的parameters的实参
     *
     * @param usernameAnnotationClass @StudentUsername或@TeacherUsername或@TAUsername
     */
    private List<String> getUsernameListToBeEnsured(JoinPoint point, Class<? extends Annotation> usernameAnnotationClass) {
        //要检查是否在数据库中存在的用户名列表
        List<String> usernameList = new ArrayList<>();

        //获得被注解的方法的签名
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();

        //遍历Method的参数，找到注解有@Username的parameters
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {

            //如果这个参数被标上了@Username......
            if (parameters[i].getAnnotation(usernameAnnotationClass) != null) {
                Object arg = point.getArgs()[i];
                String username = arg.toString();
                usernameList.add(username);
            }
        }

        return usernameList;
    }

    @Before("ensureUserExistsPointCut()")
    public void ensureUserExists(JoinPoint point) {
        List<String> usernameList = getUsernameListToBeEnsured(point, Username.class);

        log.info(String.format("正在检查用户%s是否存在", usernameList));

        //遍历usernameList检查
        usernameList.forEach(username -> {
            User user = userRepository.findByUsername(username);
            if (user == null) {
                throw new AdWebBaseException(IllegalArgumentException.class, "USER_NOT_EXISTS", "用户不存在");
            }
        });

    }

    @Before("ensureUserNotExistsPointCut()")
    public void ensureUserNotExists(JoinPoint point) {
        List<String> usernameList = getUsernameListToBeEnsured(point, Username.class);

        log.info(String.format("正在检查用户%s是不否存在", usernameList));

        //遍历usernameList检查
        usernameList.forEach(username -> {
            User user = userRepository.findByUsername(username);
            if (user != null) {
                throw new AdWebBaseException(IllegalArgumentException.class, "USER_ALREADY_EXISTS", "用户已存在");
            }
        });
    }


    @Before("ensureTeacherExistsPointCut()")
    public void ensureTeacherExists(JoinPoint point) {
        List<String> usernameList = getUsernameListToBeEnsured(point, TeacherUsername.class);

        log.info(String.format("正在检查教师%s是否存在", usernameList));

        //遍历usernameList检查
        usernameList.forEach(username -> {
            User user = userRepository.findByUsername(username);
            if (user == null || !User.ROLE_TEACHER.equals(user.getRole())) {
                throw new AdWebBaseException(IllegalArgumentException.class, "TEACHER_NOT_EXISTS", "教师不存在");
            }
        });
    }

    @Before("ensureTAExistsPointCut()")
    public void ensureTAExists(JoinPoint point) {
        List<String> usernameList = getUsernameListToBeEnsured(point, TAUsername.class);

        log.info(String.format("正在检查助教%s是否存在", usernameList));

        //遍历usernameList检查
        usernameList.forEach(username -> {
            User user = userRepository.findByUsername(username);
            if (user == null || !User.ROLE_TA.equals(user.getRole())) {
                throw new AdWebBaseException(IllegalArgumentException.class, "TA_NOT_EXISTS", "助教不存在");
            }
        });
    }


    @Before("ensureStudentExistsPointCut()")
    public void ensureStudentExists(JoinPoint point) {
        List<String> usernameList = getUsernameListToBeEnsured(point, StudentUsername.class);

        log.info(String.format("正在检查学生%s是否存在", usernameList));

        //遍历usernameList检查
        usernameList.forEach(username -> {
            User user = userRepository.findByUsername(username);
            if (user == null || !User.ROLE_STUDENT.equals(user.getRole())) {
                throw new AdWebBaseException(IllegalArgumentException.class, "STUDENT_NOT_EXISTS", "学生不存在");
            }
        });
    }

}
