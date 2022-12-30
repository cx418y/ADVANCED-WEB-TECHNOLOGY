package org.adweb.learningcommunityback.business.usermanagement.service;

import org.adweb.learningcommunityback.business.usermanagement.dto.AvatarDTO;
import org.adweb.learningcommunityback.business.usermanagement.dto.UserDTO;
import org.adweb.learningcommunityback.business.usermanagement.request.AddUserRequest;
import org.adweb.learningcommunityback.business.usermanagement.request.StudentRegisterRequest;
import org.adweb.learningcommunityback.entity.db.User;
import org.adweb.learningcommunityback.entity.response.SimpleSuccessfulPostResponse;
import org.adweb.learningcommunityback.exception.AdWebBaseException;
import org.adweb.learningcommunityback.preensure.user.EnsureUserExists;
import org.adweb.learningcommunityback.preensure.user.Username;
import org.adweb.learningcommunityback.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserManagementService {

    @Resource
    UserRepository userRepository;

    public SimpleSuccessfulPostResponse studentRegister(StudentRegisterRequest request) {

        //用户名重复问题
        if (userRepository.findByUsername(request.getUsername()) != null) {
            throw new AdWebBaseException(IllegalArgumentException.class, "USERNAME_ALREADY_USED", "该用户名已被使用");
        }

        User student = new User(
                request.getUsername(),
                request.getRealname(),
                request.getEmail(),
                request.getPassword(),
                User.ROLE_STUDENT
        );

        userRepository.save(student);

        return new SimpleSuccessfulPostResponse("注册成功");
    }


    public SimpleSuccessfulPostResponse addTeacherTAStudent(AddUserRequest request) {
        //用户名重复问题
        if (userRepository.findByUsername(request.getUsername()) != null) {
            throw new AdWebBaseException(IllegalArgumentException.class, "USERNAME_ALREADY_USED", "该用户名已被使用");
        }

        User newUser = new User(
                request.getUsername(),
                request.getRealname(),
                request.getEmail(),
                request.getPassword(),
                request.getType()
        );

        userRepository.save(newUser);

        return new SimpleSuccessfulPostResponse(String.format("添加%s成功，角色为%s", request.getUsername(), request.getType()));
    }

    @EnsureUserExists
    public UserDTO getUserInfo(@Username String username) {
        User user = userRepository.findByUsername(username);

        return new UserDTO(user.getUsername(), user.getEmail(), user.getRole());
    }

    @EnsureUserExists
    public SimpleSuccessfulPostResponse updateUserInfo(@Username String username,
                                                       String email) {
        User user = userRepository.findByUsername(username);

        user.setEmail(email);

        userRepository.save(user);

        return new SimpleSuccessfulPostResponse(String.format("修改%s成功", username));
    }

    @EnsureUserExists
    public SimpleSuccessfulPostResponse updateAvatar(@Username String username,
                                                     String avatar) {
        User user = userRepository.findByUsername(username);

        user.setAvatar(avatar);

        userRepository.save(user);

        return new SimpleSuccessfulPostResponse(String.format("修改用户%s头像成功", username));
    }

    @EnsureUserExists
    public AvatarDTO getUserAvatar(@Username String username) {
        User user = userRepository.findByUsername(username);

        return new AvatarDTO(user.getAvatar());
    }
}
