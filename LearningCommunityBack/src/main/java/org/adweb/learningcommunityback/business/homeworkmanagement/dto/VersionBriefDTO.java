package org.adweb.learningcommunityback.business.homeworkmanagement.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.adweb.learningcommunityback.entity.db.HomeworkVersion;
import org.adweb.learningcommunityback.utils.HJFieldInjectionUtils;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VersionBriefDTO {
    private String homeworkVersionID;
    private String homeworkID;
    private String fromUsername;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date expireTime;

    public static VersionBriefDTO from(HomeworkVersion homeworkVersion){
        var dto = new VersionBriefDTO();
        HJFieldInjectionUtils.inject(homeworkVersion, dto);
        return dto;
    }
}
