package org.adweb.learningcommunityback.business.essayproblemmanagement.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PeerGradeEssayProblemRequest {
    @NotBlank(message = "打分试题编号不能为空")
    private String essayProblemToBePeerGradedID;

    @Range(min = 0)
    private Double point;
}
