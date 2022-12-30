package org.adweb.learningcommunityback.repository;

import org.adweb.learningcommunityback.entity.db.EssayProblemToBeDone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface EssayProblemToBeDoneRepository extends MongoRepository<EssayProblemToBeDone, String> {
    public Page<EssayProblemToBeDone> findAllByCourseCodeAndUsernameOrderByDateDesc(String courseCode, String username, Pageable pageable);
    public EssayProblemToBeDone findByEssayProblemToBeDoneID(String essayProblemToBeDoneID);
}
