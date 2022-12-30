package org.adweb.learningcommunityback.business.chapsecmanagement.controller;

import org.adweb.learningcommunityback.business.chapsecmanagement.dto.ChapterDTO;
import org.adweb.learningcommunityback.business.chapsecmanagement.request.AddChapterRequest;
import org.adweb.learningcommunityback.business.chapsecmanagement.request.AddSectionRequest;
import org.adweb.learningcommunityback.business.chapsecmanagement.service.ChapSecManagementService;
import org.adweb.learningcommunityback.entity.db.Section;
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
public class ChapSecManagementController {

    @Resource
    ChapSecManagementService chapSecManagementService;

    //获取课程的章
    @GetMapping("/courseChapter")
    @PreAuthorize("hasAnyAuthority('Teacher') || hasAnyAuthority('TA') || hasAnyAuthority('Student')")
    public List<ChapterDTO> getCourseChapter(@RequestParam("courseCode") String courseCode,
                                             @AuthenticationPrincipal String username) {
        return chapSecManagementService.getCourseChapter(courseCode,username);
    }

    //给课程添加章
    @PostMapping("/addChapter")
    @PreAuthorize("hasAnyAuthority('Teacher')")
    public SimpleSuccessfulPostResponse addChapter(@Valid @RequestBody AddChapterRequest request,
                                                      BindingResult bindingResult,
                                                      @AuthenticationPrincipal String teacherUsername) {
        if (bindingResult.hasFieldErrors()) {
            throw new AdWebBaseException(IllegalArgumentException.class, "FIELD_ERROR", Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        return chapSecManagementService.addChapter(request.getChapterName(), request.getChapterNum(), request.getCourseCode(), teacherUsername);
    }

    //获取章下面的小节
    @GetMapping("/chapterSection")
    @PreAuthorize("hasAnyAuthority('Teacher') || hasAnyAuthority('TA') || hasAnyAuthority('Student')")
    public List<Section> getChapterSection(@RequestParam("chapterID") String chapterID,
                                           @AuthenticationPrincipal String username) {
        return chapSecManagementService.getChapterSection(chapterID, username);
    }

    //给章添加小节
    @PostMapping("/addSection")
    @PreAuthorize("hasAnyAuthority('Teacher')")
    public SimpleSuccessfulPostResponse addSection(@Valid @RequestBody AddSectionRequest request,
                                                   BindingResult bindingResult,
                                                   @AuthenticationPrincipal String teacherUsername) {
        if (bindingResult.hasFieldErrors()) {
            throw new AdWebBaseException(IllegalArgumentException.class, "FIELD_ERROR", Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        return chapSecManagementService.addSection(request.getSectionName(), request.getSectionNum(), request.getChapterID(), teacherUsername);
    }
}
