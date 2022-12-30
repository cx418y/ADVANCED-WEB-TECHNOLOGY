package org.adweb.learningcommunityback.business.essayproblemmanagement.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoEssayProblemRequest {
    @NotBlank(message = "试题编号不能为空")
    private String essayProblemToBeDoneID;

    private String answer;
}
