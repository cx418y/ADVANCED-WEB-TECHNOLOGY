package org.adweb.learningcommunityback.repository;

import org.adweb.learningcommunityback.entity.db.Homework;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HomeworkRepository extends MongoRepository<Homework, String> {
    public List<Homework> findAllBySectionID(String sectionID);
    public Homework findByHomeworkID(String homeworkID);
    public List<Homework> findAllByCourseCode(String courseCode)
}
