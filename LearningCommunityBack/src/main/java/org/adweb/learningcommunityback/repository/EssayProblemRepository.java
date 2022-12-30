package org.adweb.learningcommunityback.repository;

import org.adweb.learningcommunityback.entity.db.Course;
import org.adweb.learningcommunityback.entity.db.EssayProblem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface EssayProblemRepository extends MongoRepository<EssayProblem, String> {
    public List<EssayProblem> findAllByCourseCode(String courseCode);
    public EssayProblem findByEssayProblemID(String essayProblemID);
}
