package org.adweb.learningcommunityback.entity.db;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import org.springframework.data.annotation.Id;

@Getter
@Setter
@Document(collection = "essay_problem")
public class EssayProblem {
    @Id
    private String essayProblemID;

    private String title;

    private String referenceAnswer;

    private String fromUsername;

    private String courseCode;

    public EssayProblem(String title, String referenceAnswer, String fromUsername, String courseCode){
        this.title = title;
        this.referenceAnswer = referenceAnswer;
        this.fromUsername = fromUsername;
        this.courseCode = courseCode;
    }
}
