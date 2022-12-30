package org.adweb.learningcommunityback.business.homeworkmanagement.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class EditHomeworkRequest {
    @NotBlank(message = "作业ID不能为空")
    private String homeworkID;

    private String content;
}
