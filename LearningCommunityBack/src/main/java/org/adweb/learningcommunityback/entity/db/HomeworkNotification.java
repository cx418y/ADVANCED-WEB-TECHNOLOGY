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
                        name = "homeworkID_fromUsername_index",
                        def = "{homeworkID:1, fromUsername:1}",
                        unique = true)
        }
)
@Document(collection = "homework_notification")
public class HomeworkNotification {
    public static String NEW_HOMEWORK = "NEW_HOMEWORK";
    public static String HOMEWORK_UPDATED = "HOMEWORK_UPDATED";

    @Id
    private String homeworkNotificationID;

    private String courseCode;

    private String username;

    private String type;

    private String message;

    private String homeworkID;

    private String homeworkTitle;

    public HomeworkNotification(String courseCode, String username, String type, String message, String homeworkID, String homeworkTitle){
        this.courseCode = courseCode;
        this.username = username;
        this.type = type;
        this.message = message;
        this.homeworkID = homeworkID;
        this.homeworkTitle = homeworkTitle;
    }
}
