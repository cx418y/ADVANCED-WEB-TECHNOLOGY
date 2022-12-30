package org.adweb.learningcommunityback.business.chapsecmanagement.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChapterDTO {
    private String chapterName;
    private Integer chapterNum;
    private String chapterID;

    public Integer getChapterNum() {
        if (this.chapterNum == null) {
            return 0;
        }
        return this.chapterNum;
    }
}
