package org.adweb.learningcommunityback.business.essayproblemmanagement.controller;

import org.adweb.learningcommunityback.business.coursemanagement.dto.CourseConfig;
import org.adweb.learningcommunityback.business.essayproblemmanagement.dto.*;
import org.adweb.learningcommunityback.business.essayproblemmanagement.request.*;
import org.adweb.learningcommunityback.business.essayproblemmanagement.service.EssayProblemManagementService;
import org.adweb.learningcommunityback.entity.db.EssayProblem;
import org.adweb.learningcommunityback.entity.response.SimpleSuccessfulPostResponse;
import org.adweb.learningcommunityback.exception.AdWebBaseException;
import org.adweb.learningcommunityback.page.PageQueryResult;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
public class EssayProblemManagementController {
    @Resource
    private EssayProblemManagementService essayProblemManagementService;

    //教师获取开设的课程设置
    @GetMapping("/courseConfig")
    @PreAuthorize("hasAnyAuthority('Teacher')")
    public CourseConfig courseConfig(@RequestParam("courseCode") String courseCode,
                                     @AuthenticationPrincipal String username) {
        return essayProblemManagementService.courseConfig(courseCode, username);
    }

    //教师对自己的课程进行设置
    @PutMapping("/configMyCourse")
    @PreAuthorize("hasAnyAuthority('Teacher')")
    public SimpleSuccessfulPostResponse configMyCourse(@Valid @RequestBody ConfigCourseRequest request,
                                                       BindingResult bindingResult,
                                                       @AuthenticationPrincipal String username) {
        if (bindingResult.hasFieldErrors()) {
            throw new AdWebBaseException(IllegalArgumentException.class, "FIELD_ERROR", Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        return essayProblemManagementService.configMyCourse(username, request.getCourseCode(), request.isAllowStudentAddProblem());
    }

    //学生出题
    @PostMapping("/studentAddEssayProblem")
    @PreAuthorize("hasAnyAuthority('Student')")
    public SimpleSuccessfulPostResponse studentAddEssayProblem(@Valid @RequestBody AddEssayProblemRequest request,
                                                               BindingResult bindingResult,
                                                               @AuthenticationPrincipal String username) {
        if (bindingResult.hasFieldErrors()) {
            throw new AdWebBaseException(IllegalArgumentException.class, "FIELD_ERROR", Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        return essayProblemManagementService.studentAddEssayProblem(username, request.getCourseCode(), request.getTitle(), request.getReferenceAnswer());
    }

    //老师出题
    @PostMapping("/teacherAddEssayProblem")
    @PreAuthorize("hasAnyAuthority('Teacher')")
    public SimpleSuccessfulPostResponse teacherAddEssayProblem(@Valid @RequestBody AddEssayProblemRequest request,
                                                               BindingResult bindingResult,
                                                               @AuthenticationPrincipal String username) {
        if (bindingResult.hasFieldErrors()) {
            throw new AdWebBaseException(IllegalArgumentException.class, "FIELD_ERROR", Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        return essayProblemManagementService.teacherAddEssayProblem(username, request.getCourseCode(), request.getTitle(), request.getReferenceAnswer());
    }

    //教师获取课程下所有题
    @GetMapping("/courseEssayProblems")
    @PreAuthorize("hasAnyAuthority('Teacher')")
    public List<EssayProblem> getCourseEssayProblems(@RequestParam("courseCode") String courseCode,
                                                     @AuthenticationPrincipal String username) {
        return essayProblemManagementService.getCourseEssayProblems(courseCode, username);
    }

    //分发题目
    @PostMapping("/handoutEssayProblems")
    @PreAuthorize("hasAnyAuthority('Teacher')")
    public SimpleSuccessfulPostResponse handoutEssayProblems(@Valid @RequestBody List<HandOutRequest> request,
                                                             BindingResult bindingResult,
                                                             @AuthenticationPrincipal String username) {
        if (bindingResult.hasFieldErrors()) {
            throw new AdWebBaseException(IllegalArgumentException.class, "FIELD_ERROR", Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        return essayProblemManagementService.handoutEssayProblems(username, request);
    }

    //学生查看课程中自己待答的（论述）题
    @GetMapping("/student/essayProblemsToBeDone")
    @PreAuthorize("hasAnyAuthority('Student')")
    public PageQueryResult<EssayProblemToBeDoneDTO> getCourseEssayProblemsToBeDone(@RequestParam("courseCode") String courseCode,
                                                                                   @RequestParam("pageNum") int pageNum,
                                                                                   @RequestParam("pageSize") int pageSize,
                                                                                   @AuthenticationPrincipal String username) {
        return essayProblemManagementService.getCourseEssayProblemsToBeDone(courseCode, username, pageNum, pageSize);
    }

    //学生答题（提交自己对某一题的答案）
    @PostMapping("/student/doEssayProblem")
    @PreAuthorize("hasAnyAuthority('Student')")
    public SimpleSuccessfulPostResponse studentDoEssayProblem(@Valid @RequestBody DoEssayProblemRequest request,
                                                              BindingResult bindingResult,
                                                              @AuthenticationPrincipal String username) {
        if (bindingResult.hasFieldErrors()) {
            throw new AdWebBaseException(IllegalArgumentException.class, "FIELD_ERROR", Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        return essayProblemManagementService.studentDoEssayProblem(username, request.getEssayProblemToBeDoneID(),
                request.getAnswer());
    }

    //学生查看课程中待互评的题
    @GetMapping("/student/essayProblemsToBePeerGraded")
    @PreAuthorize("hasAnyAuthority('Student')")
    public PageQueryResult<EssayProblemToBePeerGradedDTO> getEssayProblemsToBePeerGraded(@RequestParam("courseCode") String courseCode,
                                                                                         @RequestParam("pageNum") int pageNum,
                                                                                         @RequestParam("pageSize") int pageSize,
                                                                                         @AuthenticationPrincipal String username) {
        return essayProblemManagementService.studentGetEssayProblemToBePeerGraded(courseCode, username, pageSize, pageNum);
    }

    //教师/助教查看课程中待评的题
    @GetMapping("/teacherTA/essayProblemsToBeGraded")
    @PreAuthorize("hasAnyAuthority('Teacher') || hasAnyAuthority('TA')")
    public PageQueryResult<EssayProblemToBeTeacherTAGradedDTO> getEssayProblemToBeTeacherTAGraded(@RequestParam("courseCode") String courseCode,
                                                                                                  @RequestParam("pageNum") int pageNum,
                                                                                                  @RequestParam("pageSize") int pageSize,
                                                                                                  @AuthenticationPrincipal String username) {
        return essayProblemManagementService.teacherTAGetEssayProblemToBeTeacherTAGraded(courseCode, username, pageSize, pageNum);
    }

    //学生互评
    @PostMapping("/student/peerGradeEssayProblem")
    @PreAuthorize("hasAnyAuthority('Student')")
    public SimpleSuccessfulPostResponse peerGradeEssayProblem(@Valid @RequestBody PeerGradeEssayProblemRequest request,
                                                              BindingResult bindingResult,
                                                              @AuthenticationPrincipal String username) {
        if (bindingResult.hasFieldErrors()) {
            throw new AdWebBaseException(IllegalArgumentException.class, "FIELD_ERROR", Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        return essayProblemManagementService.peerGradeEssayProblem(username, request.getEssayProblemToBePeerGradedID(), request.getPoint());
    }

    //教师/助教评题
    @PostMapping("/teacherTA/gradeEssayProblem")
    @PreAuthorize("hasAnyAuthority('Teacher') || hasAnyAuthority('TA')")
    public SimpleSuccessfulPostResponse gradeEssayProblem(@Valid @RequestBody GradeEssayProblemRequest request,
                                                          BindingResult bindingResult,
                                                          @AuthenticationPrincipal String username) {
        if (bindingResult.hasFieldErrors()) {
            throw new AdWebBaseException(IllegalArgumentException.class, "FIELD_ERROR", Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        String essayProblemToBeTeacherTAGradedID = request.getEssayProblemToBeTeacherTAGradedID();
        Double point = request.getPoint();
        return essayProblemManagementService.teacherTAGradeEssayProblem(username, essayProblemToBeTeacherTAGradedID, point);
    }

    //学生查看自己的答题记录
    @GetMapping("/student/essayProblemFinished")
    @PreAuthorize("hasAnyAuthority('Student')")
    public PageQueryResult<StudentEssayProblemFinishedDTO> studentGetEssayProblemFinished(@RequestParam("courseCode") String courseCode,
                                                                                          @RequestParam("pageNum") int pageNum,
                                                                                          @RequestParam("pageSize") int pageSize,
                                                                                          @AuthenticationPrincipal String username) {
        return essayProblemManagementService.studentGetEssayProblemFinished(courseCode, username, pageSize, pageNum);

    }

}
