package org.adweb.learningcommunityback.entity.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@CompoundIndexes({@CompoundIndex(
        name = "courseID_userID_index",
        def = "{courseID:1, userID:1}",
        unique = true
)})
@Document
public class Take2 {

    @Id
    private String id;

    private String courseID;

    private String userID;

    public Take2(String courseID, String userID) {
        this.courseID = courseID;
        this.userID = userID;
    }
}
