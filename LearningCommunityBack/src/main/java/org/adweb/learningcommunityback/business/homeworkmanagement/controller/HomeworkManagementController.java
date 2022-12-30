package org.adweb.learningcommunityback.business.homeworkmanagement.controller;

import org.adweb.learningcommunityback.business.homeworkmanagement.dto.AllVersionBriefDTO;
import org.adweb.learningcommunityback.business.homeworkmanagement.dto.HomeworkBriefDTO;
import org.adweb.learningcommunityback.business.homeworkmanagement.request.DismissNotificationRequest;
import org.adweb.learningcommunityback.business.homeworkmanagement.request.EditHomeworkRequest;
import org.adweb.learningcommunityback.business.homeworkmanagement.request.ReleaseHomeworkRequest;
import org.adweb.learningcommunityback.business.homeworkmanagement.service.HomeworkManagementService;
import org.adweb.learningcommunityback.entity.db.Homework;
import org.adweb.learningcommunityback.entity.db.HomeworkNotification;
import org.adweb.learningcommunityback.entity.db.HomeworkVersion;
import org.adweb.learningcommunityback.entity.response.SimpleSuccessfulPostResponse;
import org.adweb.learningcommunityback.exception.AdWebBaseException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
public class HomeworkManagementController {
    @Resource
    private HomeworkManagementService homeworkManagementService;

    //老师发布作业
    @PostMapping("/releaseHomework")
    @PreAuthorize("hasAnyAuthority('Teacher')")
    public SimpleSuccessfulPostResponse releaseHomework(@Valid @RequestBody ReleaseHomeworkRequest request,
                                                        BindingResult bindingResult,
                                                        @AuthenticationPrincipal String username){
        if (bindingResult.hasFieldErrors()) {
            throw new AdWebBaseException(IllegalArgumentException.class, "FIELD_ERROR", Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        return homeworkManagementService.releaseHomework(username, request.getCourseCode(), request.getSectionID(), request.getTitle(), request.getDetails(), request.getOldMax(), request.getOldTTL());
    }

    //获得section下所有homework的简要信息（title）
    @GetMapping("/allHomeworkBriefing")
    @PreAuthorize("hasAnyAuthority('Teacher') || hasAnyAuthority('TA') || hasAnyAuthority('Student')")
    public List<HomeworkBriefDTO> getAllHomeworkBriefing(@RequestParam("sectionID") String sectionID,
                                                         @AuthenticationPrincipal String username){
        return homeworkManagementService.getAllHomeworkBriefing(username, sectionID);
    }

    //获取作业详情
    @GetMapping("/homeworkDetails")
    @PreAuthorize("hasAnyAuthority('Teacher') || hasAnyAuthority('TA') || hasAnyAuthority('Student')")
    public Homework getHomeworkDetails(@RequestParam("homeworkID") String homeworkID,
                                       @AuthenticationPrincipal String username){
        return homeworkManagementService.getHomeworkDetails(username, homeworkID);
    }

    //获取作业的所有版本简要信息（不包括content）
    @GetMapping("/allVersionBriefing")
    @PreAuthorize("hasAnyAuthority('Teacher') || hasAnyAuthority('TA') || hasAnyAuthority('Student')")
    public AllVersionBriefDTO getAllVersionBriefing(@RequestParam("homeworkID") String homeworkID,
                                                    @AuthenticationPrincipal String username){
        return homeworkManagementService.getAllVersionBriefing(username, homeworkID);
    }

    //获取版本详细信息
    @GetMapping("/versionDetails")
    @PreAuthorize("hasAnyAuthority('Teacher') || hasAnyAuthority('TA') || hasAnyAuthority('Student')")
    public HomeworkVersion getVersionDetails(@RequestParam("homeworkVersionID") String homeworkVersionID,
                                             @AuthenticationPrincipal String username){
        return homeworkManagementService.getVersionDetails(username, homeworkVersionID);
    }

    //编辑作业
    @PostMapping("/editHomework")
    @PreAuthorize("hasAnyAuthority('Teacher') || hasAnyAuthority('TA') || hasAnyAuthority('Student')")
    public SimpleSuccessfulPostResponse editHomework(@Valid @RequestBody EditHomeworkRequest request,
                                                        BindingResult bindingResult,
                                                        @AuthenticationPrincipal String username){
        if (bindingResult.hasFieldErrors()) {
            throw new AdWebBaseException(IllegalArgumentException.class, "FIELD_ERROR", Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        return homeworkManagementService.editHomework(username, request.getHomeworkID(), request.getContent());
    }

    //获得通知
    @GetMapping("/allHomeworkNotifications")
    @PreAuthorize("hasAnyAuthority('Teacher') || hasAnyAuthority('TA') || hasAnyAuthority('Student')")
    public List<HomeworkNotification> getAllHomeworkNotifications(@RequestParam("courseCode") String courseCode,
                                                                  @AuthenticationPrincipal String username){
        return homeworkManagementService.getAllHomeworkNotifications(username,courseCode);
    }

    //用户dismiss作业通知
    @DeleteMapping("/dismissHomeworkNotification")
    @PreAuthorize("hasAnyAuthority('Teacher') || hasAnyAuthority('TA') || hasAnyAuthority('Student')")
    public SimpleSuccessfulPostResponse dismissNotification(@RequestBody DismissNotificationRequest request,
                                                            @AuthenticationPrincipal String username){
        return homeworkManagementService.dismissNotification(username, request.getHomeworkNotificationID());
    }
}
