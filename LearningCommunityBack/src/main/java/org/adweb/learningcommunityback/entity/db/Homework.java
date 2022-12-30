package org.adweb.learningcommunityback.entity.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@CompoundIndexes(
        {
                @CompoundIndex(
                        name = "sectionID_courseCode_index",
                        def = "{sectionID:1, courseCode:1}")
        }
)
@Document(collection = "homework")
public class Homework {
    @Id
    private String homeworkID;

    private String courseCode;

    private String sectionID;

    private String title;

    private String details;

    private Integer oldMax;

    private Integer oldTTL;

    public Integer getOldMax() {
        return oldMax == null ? 0 : oldMax;
    }

    public Integer getOldTTL() {
        return oldTTL == null ? 0 : oldTTL;
    }

    public Homework(String courseCode, String sectionID, String title, String details, int oldMax, int oldTTL){
        this.courseCode = courseCode;
        this.sectionID = sectionID;
        this.title = title;
        this.details = details;
        this.oldMax = oldMax;
        this.oldTTL = oldTTL;
    }
}
