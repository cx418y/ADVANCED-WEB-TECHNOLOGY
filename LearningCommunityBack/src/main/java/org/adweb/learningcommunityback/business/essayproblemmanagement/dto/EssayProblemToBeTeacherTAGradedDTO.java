package org.adweb.learningcommunityback.business.essayproblemmanagement.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.adweb.learningcommunityback.entity.db.EssayProblem;
import org.adweb.learningcommunityback.entity.db.EssayProblemToBeTeacherTAGraded;
import org.adweb.learningcommunityback.utils.HJFieldInjectionUtils;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EssayProblemToBeTeacherTAGradedDTO {
    private String essayProblemToBeTeacherTAGradedID;
    private String title;
    private Double maxPoint;
    private String answererUsername;
    private String answer;
    private String referenceAnswer;
    private String courseCode;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date date;

    public static EssayProblemToBeTeacherTAGradedDTO from(EssayProblemToBeTeacherTAGraded essayProblemToBeTeacherTAGraded,
                                                          EssayProblem essayProblem) {
        var dto = new EssayProblemToBeTeacherTAGradedDTO();
        HJFieldInjectionUtils.inject(essayProblemToBeTeacherTAGraded, dto);
        HJFieldInjectionUtils.inject(essayProblem, dto);
        return dto;
    }
}
