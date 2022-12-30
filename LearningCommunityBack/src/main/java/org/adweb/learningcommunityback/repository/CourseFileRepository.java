package org.adweb.learningcommunityback.repository;

import org.adweb.learningcommunityback.entity.db.CourseFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CourseFileRepository extends MongoRepository<CourseFile, String> {
    public Page<CourseFile> findAllByCourseCode(String courseCode, Pageable pageable);
    public CourseFile findByCourseCodeAndFileName(String courseCode, String filename);
}
