package org.adweb.learningcommunityback.repository;

import org.adweb.learningcommunityback.entity.db.HomeworkNotification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HomeworkNotificationRepository extends MongoRepository<HomeworkNotification, String> {
    public List<HomeworkNotification> findAllByHomeworkID(String homeworkID);
    public HomeworkNotification findByHomeworkNotificationID(String homeNotificationID);
    public List<HomeworkNotification> findAllByCourseCodeAndUsername(String courseCode, String username);
}
