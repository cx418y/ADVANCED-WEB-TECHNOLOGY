package org.adweb.learningcommunityback.business.homeworkmanagement.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReleaseHomeworkRequest {
    @NotBlank(message = "课程代码不能为空")
    @Pattern(regexp = "[A-Z]{4}[0-9]{6}", message = "课程代码必须为4位大写字母+6位数字")
    private String courseCode;

    @NotBlank(message = "章节ID不能为空")
    private String sectionID;

    @NotBlank(message = "标题不能为空")
    private String title;

    private String details;

    @Min(value = 0, message = "最多允许的旧版本数量最小为0")
    private int oldMax;

    @Min(value = 0, message = "旧版本的最多存活时间最小为0")
    private int oldTTL;
}
