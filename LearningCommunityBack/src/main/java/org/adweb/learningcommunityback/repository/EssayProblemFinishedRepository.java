package org.adweb.learningcommunityback.repository;

import org.adweb.learningcommunityback.entity.db.EssayProblemFinished;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface EssayProblemFinishedRepository extends MongoRepository<EssayProblemFinished, String> {
    public Page<EssayProblemFinished> findAllByCourseCodeAndAnswerUsernameOrderByDateDesc(String courseCode, String answerUsername, Pageable pageable);
}
