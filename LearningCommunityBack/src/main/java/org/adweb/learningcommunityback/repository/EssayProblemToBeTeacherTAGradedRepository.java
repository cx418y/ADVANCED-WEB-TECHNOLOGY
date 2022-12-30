package org.adweb.learningcommunityback.repository;

import org.adweb.learningcommunityback.entity.db.EssayProblemToBeTeacherTAGraded;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EssayProblemToBeTeacherTAGradedRepository extends MongoRepository<EssayProblemToBeTeacherTAGraded, String> {
    public Page<EssayProblemToBeTeacherTAGraded> findAllByCourseCodeOrderByDateDesc(String courseCode, Pageable pageable);

    public EssayProblemToBeTeacherTAGraded findByEssayProblemToBeTeacherTAGradedID(String essayProblemToBeTeacherTAGraded);
}
