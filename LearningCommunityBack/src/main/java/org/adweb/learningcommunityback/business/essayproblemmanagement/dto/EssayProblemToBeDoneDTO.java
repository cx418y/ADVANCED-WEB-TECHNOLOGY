package org.adweb.learningcommunityback.business.essayproblemmanagement.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.adweb.learningcommunityback.entity.db.EssayProblem;
import org.adweb.learningcommunityback.entity.db.EssayProblemToBeDone;
import org.adweb.learningcommunityback.utils.HJFieldInjectionUtils;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EssayProblemToBeDoneDTO {
    private String essayProblemToBeDoneID;
    private String title;
    private Double point;
    private String username;
    private String courseCode;
    private Boolean peerGrading;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date date;

    public static EssayProblemToBeDoneDTO from(EssayProblemToBeDone essayProblemToBeDone,
                                               EssayProblem essayProblem) {
        var dto = new EssayProblemToBeDoneDTO();
        HJFieldInjectionUtils.inject(essayProblemToBeDone, dto);
        HJFieldInjectionUtils.inject(essayProblem, dto);
        return dto;
    }
}
