package org.adweb.learningcommunityback.repository;

import org.adweb.learningcommunityback.entity.db.HomeworkVersion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HomeworkVersionRepository extends MongoRepository<HomeworkVersion, String> {
    public HomeworkVersion findByHomeworkVersionID(String homeworkVersionID);
    public List<HomeworkVersion> findAllByHomeworkIDAndExpireTimeNotNull(String homeworkID);
    public HomeworkVersion findByHomeworkIDAndExpireTimeNull(String homeworkID);
}
