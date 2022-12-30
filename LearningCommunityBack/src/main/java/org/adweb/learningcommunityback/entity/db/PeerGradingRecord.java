package org.adweb.learningcommunityback.entity.db;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Getter
@Setter
@Document(collection = "peer_grading_record")
public class PeerGradingRecord {
    @Id
    private String id;

    private String username;

    private String essayProblemToBeDoneID;

    public PeerGradingRecord(String username, String essayProblemToBeDoneID) {
        this.username = username;
        this.essayProblemToBeDoneID = essayProblemToBeDoneID;
    }
}
