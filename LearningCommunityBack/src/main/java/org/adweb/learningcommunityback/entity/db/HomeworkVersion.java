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

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@CompoundIndexes(
        {
                @CompoundIndex(
                        name = "homeworkID_fromUsername_index",
                        def = "{homeworkID:1, fromUsername:1}",
                        unique = true)
        }
)
@Document(collection = "homework_version")
public class HomeworkVersion {
    @Id
    private String homeworkVersionID;

    private String homeworkID;

    private String content;

    private String fromUsername;

    private Date createdTime;

    @Indexed(name = "expire_time_index", expireAfterSeconds = 0)
    private Date expireTime;

    public HomeworkVersion(String homeworkID, String content, String fromUsername, Date createdTime, Date expireTime){
        this.homeworkID = homeworkID;
        this.content = content;
        this.fromUsername = fromUsername;
        this.createdTime = createdTime;
        this.expireTime = expireTime;
    }
}
