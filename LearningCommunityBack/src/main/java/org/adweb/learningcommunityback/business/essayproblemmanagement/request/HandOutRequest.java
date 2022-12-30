package org.adweb.learningcommunityback.business.essayproblemmanagement.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class HandOutRequest {
    @NotBlank(message = "题目id不能为空")
    private String essayProblemID;

    private boolean peerGrading;

    private double point;
}
