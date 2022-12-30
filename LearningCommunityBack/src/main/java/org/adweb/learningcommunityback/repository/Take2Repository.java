package org.adweb.learningcommunityback.repository;

import org.adweb.learningcommunityback.entity.db.Take2;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface Take2Repository extends MongoRepository<Take2, String> {

    List<Take2> findAllByCourseID(String courseID);

    List<Take2> findAllByUserID(String userID);

    Take2 findByUserIDAndCourseID(String userID, String courseID);

}
