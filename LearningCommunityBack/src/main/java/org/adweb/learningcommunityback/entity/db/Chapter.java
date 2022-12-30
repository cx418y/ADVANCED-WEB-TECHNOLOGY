package org.adweb.learningcommunityback.entity.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@CompoundIndexes({
        @CompoundIndex(
                name = "chapterName_courseID_index",
                def = "{chapterName:1, courseID:1}",
                unique = true),
        @CompoundIndex(
                name = "chapterNum_courseID_index",
                def = "{chapterNum:1, courseID:1}",
                unique = true
        )
})
@Document(collection = "chapter")
public class Chapter {
    @Id
    private String chapterID;

    private String chapterName;

    private Integer chapterNum;

    private String courseID;

    public Chapter(String chapterName, int chapterNum, String courseID) {
        this.chapterName = chapterName;
        this.chapterNum = chapterNum;
        this.courseID = courseID;
    }

    public Integer getChapterNum() {
        return chapterNum == null ? 0 : chapterNum;
    }
}
