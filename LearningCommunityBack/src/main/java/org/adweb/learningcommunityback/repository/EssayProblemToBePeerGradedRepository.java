package org.adweb.learningcommunityback.repository;

import org.adweb.learningcommunityback.entity.db.EssayProblemToBePeerGraded;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface EssayProblemToBePeerGradedRepository extends MongoRepository<EssayProblemToBePeerGraded, String> {
    public List<EssayProblemToBePeerGraded> findAllByCourseCodeAndAnswererUsernameNot(String courseCode, String answerUsername);

    List<EssayProblemToBePeerGraded> findAllByCourseCodeOrderByDateDesc(String courseCode);

    public EssayProblemToBePeerGraded findByEssayProblemToBePeerGradedID(String essayProblemToBePeerGradedID);
}
