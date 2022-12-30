package org.adweb.learningcommunityback.repository;

import org.adweb.learningcommunityback.entity.db.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    User findByUsername(String username);
}
