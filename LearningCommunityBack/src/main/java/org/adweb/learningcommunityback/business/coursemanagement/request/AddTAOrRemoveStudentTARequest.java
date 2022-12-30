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
public class AddTAOrRemoveStudentTARequest {
    @NotBlank(message = "课程代码不能为空")
    @Pattern(regexp = "[A-Z]{4}[0-9]{6}", message = "课程代码必须为字母或数字")
    private String courseCode;

    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = "[0-9a-zA-Z]{6,12}", message = "用户名必须为6～10位的字母或数字")
    private String username;
}
