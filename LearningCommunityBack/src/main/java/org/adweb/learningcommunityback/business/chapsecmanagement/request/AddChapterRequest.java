package org.adweb.learningcommunityback.business.chapsecmanagement.request;

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
public class AddChapterRequest {
    @NotBlank(message = "章节名称不能为空")
    private String chapterName;

    @Min(value = 1, message = "章节序号必须为大于等于1的正整数")
    private int chapterNum;

    @NotBlank(message = "课程代码不能为空")
    @Pattern(regexp = "[A-Z]{4}[0-9]{6}", message = "课程代码必须为4位大写字母+6位数字")
    private String courseCode;
}
