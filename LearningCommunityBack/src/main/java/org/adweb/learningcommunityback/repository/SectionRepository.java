package org.adweb.learningcommunityback.repository;

import org.adweb.learningcommunityback.entity.db.Course;
import org.adweb.learningcommunityback.entity.db.Section;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SectionRepository extends MongoRepository<Section, String> {
    public List<Section> findAllByChapterID(String chapterID);
    public Section findBySectionID(String sectionID);
}
