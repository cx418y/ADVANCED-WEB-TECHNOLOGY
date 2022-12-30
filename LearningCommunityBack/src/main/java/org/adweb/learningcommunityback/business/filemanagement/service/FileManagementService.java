package org.adweb.learningcommunityback.business.filemanagement.service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.SneakyThrows;
import org.adweb.learningcommunityback.business.filemanagement.dto.FileDTO;
import org.adweb.learningcommunityback.entity.db.CourseFile;
import org.adweb.learningcommunityback.entity.response.SimpleSuccessfulPostResponse;
import org.adweb.learningcommunityback.exception.AdWebBaseException;
import org.adweb.learningcommunityback.page.PageQueryResult;
import org.adweb.learningcommunityback.preensure.access.EnsureUserHasCourseAccess;
import org.adweb.learningcommunityback.preensure.course.CourseCode;
import org.adweb.learningcommunityback.preensure.course.EnsureCourseExists;
import org.adweb.learningcommunityback.preensure.user.EnsureUserExists;
import org.adweb.learningcommunityback.preensure.user.Username;
import org.adweb.learningcommunityback.repository.CourseFileRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

@Service
public class FileManagementService {
    @Resource
    private CourseFileRepository courseFileRepository;

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private GridFsOperations operations;

    @EnsureUserExists
    @EnsureCourseExists
    @EnsureUserHasCourseAccess
    public PageQueryResult<FileDTO> listFiles(@Username String username,
                                              @CourseCode String courseCode,
                                              int pageNum,
                                              int pageSize) {
        pageNum--;

        Page<CourseFile> courseFiles = courseFileRepository.findAllByCourseCode(courseCode, PageRequest.of(pageNum, pageSize));

        List<FileDTO> data = courseFiles.stream()
                .map(courseFile -> {
                    GridFSFile gridFsFile = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(courseFile.getGridFSID())));
                    if (gridFsFile == null) {
                        throw new AdWebBaseException(IllegalArgumentException.class, "FILE_NOT_FOUND", "文件"+courseFile.getGridFSID()+"找不到");
                    }
                    return new FileDTO(courseFile.getFileName(),
                            courseFile.getDescription(),
                            courseFile.getCourseCode(),
                            courseFile.getFromUsername(),
                            gridFsFile.getLength());
                })
                .toList();

        int totalPage = courseFiles.getTotalPages();

        return PageQueryResult.of(totalPage, data);
    }

    @EnsureUserExists
    @EnsureCourseExists
    @EnsureUserHasCourseAccess
    @SneakyThrows
    public SimpleSuccessfulPostResponse deleteFile(@Username String username,
                                                   @CourseCode String courseCode,
                                                   String filename) {
        CourseFile courseFile = courseFileRepository.findByCourseCodeAndFileName(courseCode, filename);

        if (!Objects.equals(courseFile.getFromUsername(), username)){
            throw new AdWebBaseException(IllegalArgumentException.class, "FILE_DELETE_ERROR", "只能删除自己上传的文件");
        }

        gridFsTemplate.delete(new Query(Criteria.where("_id").is(courseFile.getGridFSID())));
        courseFileRepository.delete(courseFile);

        return new SimpleSuccessfulPostResponse("删除文件成功");
    }

    @EnsureUserExists
    @EnsureCourseExists
    @EnsureUserHasCourseAccess
    @SneakyThrows
    public SimpleSuccessfulPostResponse uploadFile(String fileName,
                                                   MultipartFile fileData,
                                                   @CourseCode String courseCode,
                                                   @Username String username) {
        DBObject metadata = new BasicDBObject();
        ObjectId id = gridFsTemplate.store(
                fileData.getInputStream(),
                fileData.getOriginalFilename(),
                fileData.getContentType(),
                metadata
        );

        CourseFile courseFile = new CourseFile();
        courseFile.setFileName(fileName);
        courseFile.setCourseCode(courseCode);
        courseFile.setFromUsername(username);
        courseFile.setGridFSID(id.toString());

        courseFileRepository.save(courseFile);

        return new SimpleSuccessfulPostResponse("上传成功");


    }

    @EnsureUserExists
    @EnsureCourseExists
    @EnsureUserHasCourseAccess
    @SneakyThrows
    public ResponseEntity<?> downloadFile(@CourseCode String courseCode,
                                          String filename,
                                          @Username String username) {
        CourseFile courseFile = courseFileRepository.findByCourseCodeAndFileName(courseCode, filename);
        String gridFSID = courseFile.getGridFSID();

        GridFSFile gridFSFile = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(gridFSID)));
        if (gridFSFile == null) {
            return ResponseEntity
                    .notFound()
                    .build();
        }

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentLength(gridFSFile.getLength());
        headers.add(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment;filename=%s", URLEncoder.encode(courseFile.getFileName(), StandardCharsets.UTF_8)));

        InputStreamResource resource
                = new InputStreamResource(operations.getResource(gridFSFile).getInputStream());

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(resource);
    }

}
