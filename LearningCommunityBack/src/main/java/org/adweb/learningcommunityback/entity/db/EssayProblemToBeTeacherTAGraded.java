package org.adweb.learningcommunityback.entity.db;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Getter
@Setter
@Document(collection = "essay_problem_to_be_teacherTA_graded")
public class EssayProblemToBeTeacherTAGraded {
    @Id
    private String essayProblemToBeTeacherTAGradedID;

    private String essayProblemToBeDoneID;

    private String essayProblemID;

    private Double maxPoint;

    private String answererUsername;

    private String answer;

    private String courseCode;

    private Date date;

    public EssayProblemToBeTeacherTAGraded(String essayProblemToBeDoneID, String essayProblemID, Double maxPoint,
                                           String answererUsername, String answer, String courseCode, Date date) {
        this.essayProblemToBeDoneID = essayProblemToBeDoneID;
        this.essayProblemID = essayProblemID;
        this.maxPoint = maxPoint;
        this.answererUsername = answererUsername;
        this.answer = answer;
        this.courseCode = courseCode;
        this.date = date;
    }
}
