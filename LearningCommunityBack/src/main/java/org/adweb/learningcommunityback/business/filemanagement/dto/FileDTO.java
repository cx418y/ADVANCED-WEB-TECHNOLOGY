package org.adweb.learningcommunityback.business.filemanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileDTO {
    private String fileName;
    private String description;
    private String courseCode;
    private String fromUsername;
    private Long fileSize;
}
