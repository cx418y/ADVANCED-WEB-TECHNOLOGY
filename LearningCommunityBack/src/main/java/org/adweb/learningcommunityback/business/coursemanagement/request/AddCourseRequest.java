package org.adweb.learningcommunityback.business.coursemanagement.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddCourseRequest {
    @NotBlank(message = "课程代码不能为空")
    @Pattern(regexp = "[A-Z]{4}[0-9]{6}", message = "课程代码必须为4位大写字母+6位数字")
    private String courseCode;

    @NotBlank(message = "课程名称不能为空")
    private String courseName;

    @NotBlank(message = "课程描述不能为空")
    private String description;
}
