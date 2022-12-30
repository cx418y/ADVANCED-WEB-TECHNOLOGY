package org.adweb.learningcommunityback.business.usermanagement.controller;

import org.adweb.learningcommunityback.business.coursemanagement.request.AvatarRequest;
import org.adweb.learningcommunityback.business.usermanagement.dto.AvatarDTO;
import org.adweb.learningcommunityback.business.usermanagement.dto.UserDTO;
import org.adweb.learningcommunityback.business.usermanagement.request.AddUserRequest;
import org.adweb.learningcommunityback.business.usermanagement.request.StudentRegisterRequest;
import org.adweb.learningcommunityback.business.usermanagement.service.UserManagementService;
import org.adweb.learningcommunityback.entity.response.SimpleSuccessfulPostResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.adweb.learningcommunityback.exception.AdWebBaseException;
import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

/**
 * 和登陆注册的控制器
 */
@RestController
public class UserManagementController {

    @Resource
    UserManagementService userManagementService;

    @PostMapping("/studentRegister")
    public SimpleSuccessfulPostResponse studentRegister
            (@Valid @RequestBody StudentRegisterRequest request,
             BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            throw new AdWebBaseException(IllegalArgumentException.class, "FIELD_ERROR", Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        return userManagementService.studentRegister(request);
    }


    @PostMapping("/admin/addTeacherTAStudent")
    @PreAuthorize("hasAnyAuthority('Admin')")
    public SimpleSuccessfulPostResponse addTeacherTAStudent
            (@Valid @RequestBody AddUserRequest addUserRequest,
             BindingResult bindingResult) {

        if (bindingResult.hasFieldErrors()) {
            throw new AdWebBaseException(IllegalArgumentException.class, "FIELD_ERROR", Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        return userManagementService.addTeacherTAStudent(addUserRequest);
    }

    //获取用户个人信息
    @GetMapping("/userInfo")
    @PreAuthorize("hasAnyAuthority('Teacher') || hasAnyAuthority('TA') || hasAnyAuthority('Student')")
    public UserDTO getUserInfo(@AuthenticationPrincipal String username){
        return userManagementService.getUserInfo(username);
    }

    //修改用户个人信息
    @PutMapping("/updateUserInfo")
    @PreAuthorize("hasAnyAuthority('Teacher') || hasAnyAuthority('TA') || hasAnyAuthority('Student')")
    public SimpleSuccessfulPostResponse updateUserInfo(@AuthenticationPrincipal String username,
                                                       @NotBlank(message = "邮箱不能为空")
                                                       @Email(message = "邮件不符合格式")
                                                       @RequestParam("email") String email){
        return userManagementService.updateUserInfo(username, email);
    }

    @PutMapping("/updateAvatar")
    @PreAuthorize("hasAnyAuthority('Teacher') || hasAnyAuthority('TA') || hasAnyAuthority('Student')")
    public SimpleSuccessfulPostResponse updateAvatar(@AuthenticationPrincipal String username,
                                                     @Valid @RequestBody AvatarRequest request,
                                                     BindingResult bindingResult){
        return userManagementService.updateAvatar(username, request.getAvatar());
    }

    @GetMapping("/userAvatar")
    @PreAuthorize("hasAnyAuthority('Teacher') || hasAnyAuthority('TA') || hasAnyAuthority('Student')")
    public AvatarDTO getUserAvatar(@AuthenticationPrincipal String username){
        return userManagementService.getUserAvatar(username);
    }
}
