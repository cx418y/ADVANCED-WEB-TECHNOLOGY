package org.adweb.learningcommunityback.business.filemanagement.controller;

import org.adweb.learningcommunityback.business.filemanagement.dto.FileDTO;
import org.adweb.learningcommunityback.business.filemanagement.service.FileManagementService;
import org.adweb.learningcommunityback.entity.response.SimpleSuccessfulPostResponse;
import org.adweb.learningcommunityback.page.PageQueryResult;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
public class FileManagementController {
    @Resource
    private FileManagementService fileManagementService;

    //列出课程中的文件
    @GetMapping("/{courseCode}/files")
    @PreAuthorize("hasAnyAuthority('Student') || hasAnyAuthority('Teacher') || hasAnyAuthority('TA')")
    public PageQueryResult<FileDTO> listFiles(@PathVariable("courseCode") String courseCode,
                                              @RequestParam("pageNum") int pageNum,
                                              @RequestParam("pageSize") int pageSize,
                                              @AuthenticationPrincipal String username){
        return fileManagementService.listFiles(username, courseCode, pageNum, pageSize);
    }

    //删除文件
    @DeleteMapping("/{courseCode}/files/{filename}")
    @PreAuthorize("hasAnyAuthority('Teacher') || hasAnyAuthority('TA')")
    public SimpleSuccessfulPostResponse deleteFile(@PathVariable("courseCode") String courseCode,
                                                   @PathVariable("filename") String filename,
                                                   @AuthenticationPrincipal String username){
        return fileManagementService.deleteFile(username, courseCode, filename);
    }

    //教师/助教向课程上传文件
    @PostMapping("/{courseCode}/files/{filename}")
    @PreAuthorize("hasAnyAuthority('Teacher') || hasAnyAuthority('TA')")
    public SimpleSuccessfulPostResponse uploadFile(@PathVariable("courseCode") String courseCode,
                                                   @PathVariable("filename") String filename,
                                                   @AuthenticationPrincipal String username,
                                                   @RequestParam("fileData")MultipartFile fileData) {
        return fileManagementService.uploadFile(filename, fileData, courseCode, username);
    }

    //教师/助教/学生从课程中下载文件
    @GetMapping("/{courseCode}/files/{filename}")
    @PreAuthorize("hasAnyAuthority('Teacher') || hasAnyAuthority('TA') || hasAnyAuthority('Student')")
    public ResponseEntity<?> downloadFile(@PathVariable("courseCode") String courseCode,
                                       @PathVariable("filename") String filename,
                                       @AuthenticationPrincipal String username) {
        return fileManagementService.downloadFile(courseCode, filename, username);
    }

}
