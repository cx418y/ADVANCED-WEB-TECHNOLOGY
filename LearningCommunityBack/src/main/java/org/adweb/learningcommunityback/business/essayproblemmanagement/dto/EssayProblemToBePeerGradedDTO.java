package org.adweb.learningcommunityback.business.essayproblemmanagement.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.adweb.learningcommunityback.entity.db.EssayProblem;
import org.adweb.learningcommunityback.entity.db.EssayProblemToBePeerGraded;
import org.adweb.learningcommunityback.utils.HJFieldInjectionUtils;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

/**
 * 有待同学互评的论述题（Essay Problem） 的DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EssayProblemToBePeerGradedDTO {
    @Id
    private String essayProblemToBePeerGradedID;
    private String title;
    private Double maxPoint;
    private List<Double> gradedPointList;
    private String answererUsername;
    private String answer;
    private String referenceAnswer;
    private String courseCode;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date date;

    public static EssayProblemToBePeerGradedDTO from(EssayProblemToBePeerGraded essayProblemToBePeerGraded,
                                                     EssayProblem essayProblem) {
        var dto = new EssayProblemToBePeerGradedDTO();
        HJFieldInjectionUtils.inject(essayProblemToBePeerGraded, dto);
        HJFieldInjectionUtils.inject(essayProblem, dto);
        return dto;
    }
}
