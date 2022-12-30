package org.adweb.learningcommunityback.repository;

import org.adweb.learningcommunityback.entity.db.Chapter;
import org.adweb.learningcommunityback.entity.db.Course;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChapterRepository extends MongoRepository<Chapter, String> {
    public List<Chapter> findAllByCourseID(String courseID);
    public Chapter findByChapterID(String chapterID);
}
