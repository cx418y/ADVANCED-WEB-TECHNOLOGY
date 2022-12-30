package org.adweb.learningcommunityback.entity.db;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Getter
@Setter
@Document(collection = "essay_problem_finished")
public class EssayProblemFinished {
    @Id
    private String essayProblemFinishedID;

    private String essayProblemID;

    private Double maxPoint;

    private Double gottenPoint;

    private String answer;

    private String answerUsername;

    private String courseCode;

    private Date date;

    public EssayProblemFinished(String essayProblemID, Double maxPoint, Double gottenPoint,
                                String answer, String answerUsername, String courseCode, Date date) {
        this.essayProblemID = essayProblemID;
        this.maxPoint = maxPoint;
        this.gottenPoint = gottenPoint;
        this.answer = answer;
        this.answerUsername = answerUsername;
        this.courseCode = courseCode;
        this.date = date;
    }
}
