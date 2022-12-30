package org.adweb.learningcommunityback.entity.db;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@Document(collection = "essay_problem_to_be_done")
public class EssayProblemToBeDone {
    @Id
    private String essayProblemToBeDoneID;

    private String essayProblemID;

    @Indexed(name = "username_index")
    private String username;

    @Indexed(name = "courseCode_index")
    private String courseCode;

    private Boolean peerGrading;

    private Double point;

    private Date date;

    public EssayProblemToBeDone(String essayProblemID, String username, String courseCode, boolean peerGrading, double point, Date date) {
        this.essayProblemID = essayProblemID;
        this.username = username;
        this.courseCode = courseCode;
        this.peerGrading = peerGrading;
        this.point = point;
        this.date = date;
    }
}
