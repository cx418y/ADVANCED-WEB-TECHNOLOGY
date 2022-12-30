package org.adweb.learningcommunityback.preensure.chapter;

import lombok.extern.slf4j.Slf4j;
import org.adweb.learningcommunityback.entity.db.Chapter;
import org.adweb.learningcommunityback.entity.db.Course;
import org.adweb.learningcommunityback.exception.AdWebBaseException;
import org.adweb.learningcommunityback.preensure.course.CourseCode;
import org.adweb.learningcommunityback.repository.ChapterRepository;
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
import java.util.*;

@Aspect
@Component
@Slf4j
public class EnsureChapterAspect {
    @Resource
    CourseRepository courseRepository;

    @Resource
    ChapterRepository chapterRepository;

    private Map<String, List<String>> getCourseCodeListAndChapterNameListAndChapterIDList(JoinPoint point) {
        List<String> chapterNameList = new ArrayList<>();
        List<String> courseCodeList = new ArrayList<>();
        List<String> chapterIDList = new ArrayList<>();
        List<String> chapterNumList = new ArrayList<>();

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

            //如果这个参数被@ChapterName标注
            if (parameters[i].getAnnotation(ChapterName.class) != null) {
                chapterNameList.add(point.getArgs()[i].toString());
            }

            if (parameters[i].getAnnotation(ChapterNum.class) != null) {
                chapterNumList.add(point.getArgs()[i].toString());
            }
        }

        Map<String, List<String>> res = new HashMap<>();
        res.put("chapterNameList", chapterNameList);
        res.put("chapterNumList", chapterNumList);
        res.put("chapterIDList", chapterIDList);
        res.put("courseCodeList", courseCodeList);

        return res;
    }

    @Pointcut("@annotation(org.adweb.learningcommunityback.preensure.chapter.EnsureChapterIDExistsInCourse)")
    public void ensureChapterIDExistsInCoursePointCut() {
    }

    @Pointcut("@annotation(org.adweb.learningcommunityback.preensure.chapter.EnsureChapterNameNotExistsInCourse)")
    public void ensureChapterNameNotExistsInCoursePointCut() {
    }

    @Before("ensureChapterIDExistsInCoursePointCut()")
    public void ensureChapterIDExistsInCourse(JoinPoint point) {
        List<String> chapterIDList = getCourseCodeListAndChapterNameListAndChapterIDList(point).get("chapterIDList");

        log.info(String.format("正在检查章节%s是否存在", chapterIDList));

        //遍历chapterIDList检查
        chapterIDList.forEach(chapterID -> {
            Chapter chapter = chapterRepository.findByChapterID(chapterID);
            if (chapter == null) {
                throw new AdWebBaseException(IllegalArgumentException.class, "CHAPTER_NOT_EXISTS", "章不存在");
            }
        });
    }

    @Before("ensureChapterNameNotExistsInCoursePointCut()")
    public void ensureChapterNameNotExistsInCourse(JoinPoint point) {
        Map<String, List<String>> res = getCourseCodeListAndChapterNameListAndChapterIDList(point);
        List<String> courseCodeList = res.get("courseCodeList");
        List<String> chapterNameList = res.get("chapterNameList");

        log.info(String.format("正在检查章节名%s是否不存在", chapterNameList));

        for (String s : courseCodeList) {
            Course course = courseRepository.findByCourseCode(s);

            if (course == null) {
                throw new AdWebBaseException(IllegalArgumentException.class, "COURSE_NOT_EXISTS", "课程不存在");
            }

            for (String chapterName : chapterNameList) {
                List<Chapter> chapters = chapterRepository.findAllByCourseID(course.getId());

                chapters.forEach(chapter -> {
                    if (Objects.equals(chapter.getChapterName(), chapterName)) {
                        throw new AdWebBaseException(IllegalArgumentException.class, "CHAPTER_ALREADY_EXISTS", "章节名已存在");
                    }
                });
            }
        }
    }


    @Before("@annotation(org.adweb.learningcommunityback.preensure.chapter.EnsureChapterNumNotExistsInCourse)")
    public void ensureChapterNumNotExistsInCourse(JoinPoint joinPoint) {
        Map<String, List<String>> res = getCourseCodeListAndChapterNameListAndChapterIDList(joinPoint);
        List<String> courseCodeList = res.get("courseCodeList");
        List<String> chapterNumList = res.get("chapterNumList");

        log.info(String.format("正在检查章号%s是否不存在", chapterNumList));

        for (String s : courseCodeList) {
            Course course = courseRepository.findByCourseCode(s);

            if (course == null) {
                throw new AdWebBaseException(IllegalArgumentException.class, "COURSE_NOT_EXISTS", "课程不存在");
            }

            for (String chapterNum : chapterNumList) {
                List<Chapter> chapters = chapterRepository.findAllByCourseID(course.getId());

                chapters.forEach(chapter -> {
                    if (Objects.equals(chapter.getChapterNum(), Integer.parseInt(chapterNum))) {
                        throw new AdWebBaseException(IllegalArgumentException.class, "CHAPTER_ALREADY_EXISTS", "章号已存在");
                    }
                });
            }
        }

    }
}
