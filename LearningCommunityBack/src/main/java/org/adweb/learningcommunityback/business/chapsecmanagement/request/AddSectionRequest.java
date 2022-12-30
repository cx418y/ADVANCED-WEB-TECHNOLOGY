package org.adweb.learningcommunityback.business.chapsecmanagement.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddSectionRequest {
    @NotBlank(message = "小节名称不能为空")
    private String sectionName;

    @Min(value = 1, message = "小节序号必须为大于等于1的正整数")
    private int sectionNum;

    @NotBlank(message = "章节ID不能为空")
    private String chapterID;
}
