package org.adweb.learningcommunityback.business.essayproblemmanagement.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EssayProblemFinishedDTO {
    private String essayProblemToBePeerGradedID;
    private String title;
    private Double maxPoint;
    private List<Double> gradedPointList;
    private String answerUsername;
    private String answer;
    private String referenceAnswer;
    protected String courseCode;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date date;

}
