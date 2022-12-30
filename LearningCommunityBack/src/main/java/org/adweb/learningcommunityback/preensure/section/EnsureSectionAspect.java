package org.adweb.learningcommunityback.preensure.section;

import lombok.extern.slf4j.Slf4j;
import org.adweb.learningcommunityback.entity.db.Chapter;
import org.adweb.learningcommunityback.entity.db.Section;
import org.adweb.learningcommunityback.exception.AdWebBaseException;
import org.adweb.learningcommunityback.preensure.chapter.ChapterID;
import org.adweb.learningcommunityback.repository.ChapterRepository;
import org.adweb.learningcommunityback.repository.CourseRepository;
import org.adweb.learningcommunityback.repository.SectionRepository;
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
public class EnsureSectionAspect {
    @Resource
    CourseRepository courseRepository;

    @Resource
    ChapterRepository chapterRepository;

    @Resource
    SectionRepository sectionRepository;

    private Map<String, List<String>> getSectionListAndChapterIDList(JoinPoint point) {
        List<String> sectionNameList = new ArrayList<>();
        List<String> chapterIDList = new ArrayList<>();
        List<String> sectionNumList = new ArrayList<>();
        List<String> sectionIDList = new ArrayList<>();

        //获得被调用的方法对象
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();

        //获得形参列表
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {

            //如果这个参数被@SectionName标注
            if (parameters[i].getAnnotation(SectionName.class) != null) {
                sectionNameList.add(point.getArgs()[i].toString());
            }

            //如果这个参数被@ChapterID标注
            if (parameters[i].getAnnotation(ChapterID.class) != null) {
                chapterIDList.add(point.getArgs()[i].toString());
            }

            //如果这个参数被@SectionNum标注
            if (parameters[i].getAnnotation(SectionNum.class) != null) {
                sectionNumList.add(point.getArgs()[i].toString());
            }

            //如果这个参数被@SectionID标注
            if (parameters[i].getAnnotation(SectionID.class) != null) {
                sectionIDList.add(point.getArgs()[i].toString());
            }
        }

        Map<String, List<String>> map = new HashMap<>();
        map.put("chapterIDList", chapterIDList);
        map.put("sectionNumList", sectionNumList);
        map.put("sectionNameList", sectionNameList);
        map.put("sectionIDList", sectionIDList);

        return map;
    }

    @Pointcut("@annotation(org.adweb.learningcommunityback.preensure.section.EnsureSectionNameNotExistsInChapter)")
    public void ensureSectionNameNotExistsPointCut() {
    }

    @Pointcut("@annotation(org.adweb.learningcommunityback.preensure.section.EnsureSectionNumNotExistsInChapter)")
    public void ensureSectionNumNotExistsPointCut() {
    }

    @Pointcut("@annotation(EnsureSectionExists)")
    public void ensureSectionExistsPointCut() {
    }

    @Before("ensureSectionNameNotExistsPointCut()")
    public void ensureSectionNameNotExists(JoinPoint point) {
        var res = getSectionListAndChapterIDList(point);
        List<String> sectionNameList = res.get("sectionNameList");
        List<String> chapterIDList = res.get("chapterIDList");

        log.info(String.format("正在检查小节名%s是否不存在", sectionNameList));

        for (String chapterID : chapterIDList) {
            Chapter chapter = chapterRepository.findByChapterID(chapterID);

            if (chapter == null) {
                throw new AdWebBaseException(IllegalArgumentException.class, "CHAPTER_NOT_EXISTS", "章不存在");
            }

            for (String sectionName : sectionNameList) {
                List<Section> sections = sectionRepository.findAllByChapterID(chapterID);

                sections.forEach(section -> {
                    if (Objects.equals(section.getSectionName(), sectionName)) {
                        throw new AdWebBaseException(IllegalArgumentException.class, "SECTION_ALREADY_EXISTS", "小节已存在");
                    }
                });
            }
        }
    }


    @Before("ensureSectionNumNotExistsPointCut()")
    public void ensureSectionNumNotExists(JoinPoint point) {

        var res = getSectionListAndChapterIDList(point);
        List<String> sectionNumList = res.get("sectionNumList");
        List<String> chapterIDList = res.get("chapterIDList");

        log.info(String.format("正在检查小节号%s是否不存在", sectionNumList));

        for (String chapterID : chapterIDList) {
            Chapter chapter = chapterRepository.findByChapterID(chapterID);

            if (chapter == null) {
                throw new AdWebBaseException(IllegalArgumentException.class, "CHAPTER_NOT_EXISTS", "章不存在");
            }

            for (String sectionNum : sectionNumList) {
                List<Section> sections = sectionRepository.findAllByChapterID(chapterID);

                sections.forEach(section -> {
                    if (Objects.equals(section.getSectionNum(), Integer.parseInt(sectionNum))) {
                        throw new AdWebBaseException(IllegalArgumentException.class, "SECTION_ALREADY_EXISTS", "小节号已存在");
                    }
                });
            }
        }

    }

    @Before("ensureSectionExistsPointCut()")
    public void ensureSectionExists(JoinPoint point){
        var res = getSectionListAndChapterIDList(point);
        List<String> sectionIDList = res.get("sectionIDList");
        for (String sectionID: sectionIDList){
            Section section = sectionRepository.findBySectionID(sectionID);

            if (section == null){
                throw new AdWebBaseException(IllegalArgumentException.class, "SECTION_NOT_EXISTS", "小节不存在");
            }
        }
    }
}
