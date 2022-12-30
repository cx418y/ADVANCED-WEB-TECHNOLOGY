package org.adweb.learningcommunityback.entity.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.adweb.learningcommunityback.preensure.chapter.ChapterID;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@CompoundIndexes(
        {
                @CompoundIndex(
                        name = "sectionName_chapterID_index",
                        def = "{sectionName:1, chapterID:1}",
                        unique = true),
                @CompoundIndex(
                        name = "sectionNum_chapterID_index",
                        def = "{sectionNum:1, chapterID:1}",
                        unique = true
                )
        }
)
@Document(collection = "section")
public class Section {
    @Id
    private String sectionID;

    private String sectionName;

    private Integer sectionNum;

    private String courseID;

    private String chapterID;

    public Section(String sectionName, int sectionNum, String courseID, String chapterID) {
        this.sectionName = sectionName;
        this.sectionNum = sectionNum;
        this.courseID = courseID;
        this.chapterID = chapterID;
    }

    public Integer getSectionNum() {
        return sectionNum == null ? 0 : sectionNum;
    }
}
