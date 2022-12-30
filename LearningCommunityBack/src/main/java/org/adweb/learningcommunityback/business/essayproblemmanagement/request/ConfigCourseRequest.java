package org.adweb.learningcommunityback.business.essayproblemmanagement.request;

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
public class ConfigCourseRequest {
    private boolean allowStudentAddProblem;

    @NotBlank(message = "课程代码不能为空")
    @Pattern(regexp = "[A-Z]{4}[0-9]{6}", message = "课程代码必须为4位大写字母+6位数字")
    private String courseCode;
}
