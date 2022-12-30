package org.adweb.learningcommunityback.repository;

import org.adweb.learningcommunityback.entity.db.PeerGradingRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PeerGradingRecordRepository extends MongoRepository<PeerGradingRecord, String> {
    public PeerGradingRecord findByEssayProblemToBeDoneIDAndUsername(String essayProblemToBeDoneID, String username);
}
