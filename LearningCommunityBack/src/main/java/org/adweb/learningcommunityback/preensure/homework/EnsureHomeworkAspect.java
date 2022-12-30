package org.adweb.learningcommunityback.preensure.homework;

import lombok.extern.slf4j.Slf4j;
import org.adweb.learningcommunityback.entity.db.Homework;
import org.adweb.learningcommunityback.exception.AdWebBaseException;
import org.adweb.learningcommunityback.repository.HomeworkRepository;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Aspect
@Component
@Slf4j
public class EnsureHomeworkAspect {
    @Resource
    HomeworkRepository homeworkRepository;

    private List<String> getHomeworkIDList(JoinPoint point) {
        List<String> homeworkIDList = new ArrayList<>();

        //获得被调用的方法对象
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();

        //获得形参列表
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {

            //如果这个参数被@HomeworkID标注
            if (parameters[i].getAnnotation(HomeworkID.class) != null) {
                homeworkIDList.add(point.getArgs()[i].toString());
            }
        }

        return homeworkIDList;
    }

    @Pointcut("@annotation(EnsureHomeworkExists)")
    public void ensureHomeworkExistsPointCut() {
    }

    @Before("ensureHomeworkExistsPointCut()")
    public void ensureSectionExists(JoinPoint point){
        List<String> homeworkIDList = getHomeworkIDList(point);
        for (String homeworkID: homeworkIDList){
            Homework homework = homeworkRepository.findByHomeworkID(homeworkID);

            if (homework == null){
                throw new AdWebBaseException(IllegalArgumentException.class, "HOMEWORK_NOT_EXISTS", "作业不存在");
            }
        }
    }
}
