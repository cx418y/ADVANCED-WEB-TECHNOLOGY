package org.adweb.learningcommunityback.business.essayproblemmanagement.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.adweb.learningcommunityback.entity.db.EssayProblem;
import org.adweb.learningcommunityback.entity.db.EssayProblemFinished;
import org.adweb.learningcommunityback.repository.EssayProblemRepository;
import org.adweb.learningcommunityback.utils.HJFieldInjectionUtils;

import java.util.Date;
import java.util.Optional;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudentEssayProblemFinishedDTO {
    private String essayProblemFinishedID;
    private Double maxPoint;
    private Double gottenPoint;
    private String title;
    private String answer;
    private String referenceAnswer;
    private String answererUsername;
    private String courseCode;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date date;


    public static StudentEssayProblemFinishedDTO from(EssayProblemFinished essayProblemFinished,
                                                      EssayProblem essayProblem) {
        StudentEssayProblemFinishedDTO dto = new StudentEssayProblemFinishedDTO();

        HJFieldInjectionUtils.inject(essayProblemFinished, dto);
        HJFieldInjectionUtils.inject(essayProblem, dto);

        return dto;

    }

}
