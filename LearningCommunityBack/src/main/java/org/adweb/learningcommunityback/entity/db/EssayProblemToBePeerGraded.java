package org.adweb.learningcommunityback.entity.db;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Document(collection = "essay_problem_to_be_peer_graded")
public class EssayProblemToBePeerGraded {
    @Id
    private String essayProblemToBePeerGradedID;

    private String essayProblemToBeDoneID;

    private String essayProblemID;

    private Double maxPoint;

    private List<Double> gradedPointList;

    private String answererUsername;

    private String answer;

    private String courseCode;

    private Date date;

    public EssayProblemToBePeerGraded(String essayProblemToBeDoneID, String essayProblemID, Double maxPoint,
                                      List<Double> gradedPointList, String answererUsername, String answer,
                                      String courseCode, Date date) {
        this.essayProblemToBeDoneID = essayProblemToBeDoneID;
        this.essayProblemID = essayProblemID;
        this.maxPoint = maxPoint;
        this.gradedPointList = gradedPointList;
        this.answererUsername = answererUsername;
        this.answer = answer;
        this.courseCode = courseCode;
        this.date = date;
    }

}
