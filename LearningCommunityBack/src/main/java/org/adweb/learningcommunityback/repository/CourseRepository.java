package org.adweb.learningcommunityback.repository;

import org.adweb.learningcommunityback.entity.db.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CourseRepository extends MongoRepository<Course, String> {
    public Course findByCourseCode(String courseCode);
    public Page<Course> findAll(Pageable pageable);
    public Page<Course> findAllByCourseNameLike(String courseName, Pageable pageable);
}
