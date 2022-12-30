package org.adweb.learningcommunityback.business.chapsecmanagement.service;

import org.adweb.learningcommunityback.business.chapsecmanagement.dto.ChapterDTO;
import org.adweb.learningcommunityback.entity.db.Chapter;
import org.adweb.learningcommunityback.entity.db.Course;
import org.adweb.learningcommunityback.entity.db.Section;
import org.adweb.learningcommunityback.entity.response.SimpleSuccessfulPostResponse;
import org.adweb.learningcommunityback.preensure.access.EnsureTeacherHasChapterAccess;
import org.adweb.learningcommunityback.preensure.access.EnsureTeacherHasCourseAccess;
import org.adweb.learningcommunityback.preensure.access.EnsureUserHasChapterAccess;
import org.adweb.learningcommunityback.preensure.access.EnsureUserHasCourseAccess;
import org.adweb.learningcommunityback.preensure.chapter.*;
import org.adweb.learningcommunityback.preensure.course.CourseCode;
import org.adweb.learningcommunityback.preensure.course.EnsureCourseExists;
import org.adweb.learningcommunityback.preensure.section.EnsureSectionNameNotExistsInChapter;
import org.adweb.learningcommunityback.preensure.section.EnsureSectionNumNotExistsInChapter;
import org.adweb.learningcommunityback.preensure.section.SectionName;
import org.adweb.learningcommunityback.preensure.user.EnsureTeacherExists;
import org.adweb.learningcommunityback.preensure.user.EnsureUserExists;
import org.adweb.learningcommunityback.preensure.user.TeacherUsername;
import org.adweb.learningcommunityback.preensure.user.Username;
import org.adweb.learningcommunityback.repository.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class ChapSecManagementService {

    @Resource
    CourseRepository courseRepository;

    @Resource
    ChapterRepository chapterRepository;

    @Resource
    SectionRepository sectionRepository;

    //获取课程的章
    @EnsureCourseExists
    @EnsureUserExists
    @EnsureUserHasCourseAccess
    public List<ChapterDTO> getCourseChapter(@CourseCode String courseCode,
                                             @Username String username) {
        Course course = courseRepository.findByCourseCode(courseCode);

        List<Chapter> chapters = chapterRepository.findAllByCourseID(course.getId());

        List<ChapterDTO> chapterDTOs = new ArrayList<>();

        chapters.forEach(chapter -> {
            ChapterDTO chapterDTO = new ChapterDTO(chapter.getChapterName(), chapter.getChapterNum(), chapter.getChapterID());
            chapterDTOs.add(chapterDTO);
        });

        chapterDTOs.sort(Comparator.comparingInt(ChapterDTO::getChapterNum));

        return chapterDTOs;
    }

    //给课程添加章
    @EnsureCourseExists
    @EnsureTeacherExists
    @EnsureTeacherHasCourseAccess
    @EnsureChapterNameNotExistsInCourse
    @EnsureChapterNumNotExistsInCourse
    public SimpleSuccessfulPostResponse addChapter(@ChapterName String chapterName,
                                                   @ChapterNum int chapterNum,
                                                   @CourseCode String courseCode,
                                                   @TeacherUsername String teacherUsername) {
        Course course = courseRepository.findByCourseCode(courseCode);

        Chapter chapter = new Chapter(chapterName, chapterNum, course.getId());

        chapterRepository.save(chapter);

        return new SimpleSuccessfulPostResponse("添加章成功");
    }

    //获取章的小节
    @EnsureUserExists
    @EnsureChapterIDExistsInCourse
    @EnsureUserHasChapterAccess
    public List<Section> getChapterSection(@ChapterID String chapterID,
                                           @Username String username) {

        List<Section> sections = sectionRepository.findAllByChapterID(chapterID);
        sections.sort(Comparator.comparingInt(Section::getSectionNum));
        return sections;
    }

    //给课程添加小节
    @EnsureTeacherExists
    @EnsureChapterIDExistsInCourse
    @EnsureTeacherHasChapterAccess
    @EnsureSectionNameNotExistsInChapter
    @EnsureSectionNumNotExistsInChapter
    public SimpleSuccessfulPostResponse addSection(@SectionName String sectionName,
                                                   @SectionName int sectionNum,
                                                   @ChapterID String chapterID,
                                                   @TeacherUsername String teacherUsername) {
        Chapter chapter = chapterRepository.findByChapterID(chapterID);

        Section section = new Section(sectionName, sectionNum, chapter.getCourseID(), chapterID);

        sectionRepository.save(section);

        return new SimpleSuccessfulPostResponse("添加小节成功");
    }
}
