package org.adweb.learningcommunityback.business.homeworkmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.adweb.learningcommunityback.entity.db.Homework;
import org.adweb.learningcommunityback.entity.db.HomeworkVersion;
import org.adweb.learningcommunityback.utils.HJFieldInjectionUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HomeworkBriefDTO {
    private String homeworkID;
    private String courseCode;
    private String sectionID;
    private String title;

    public static HomeworkBriefDTO from(Homework homework){
        var dto = new HomeworkBriefDTO();
        HJFieldInjectionUtils.inject(homework, dto);
        return dto;
    }
}
