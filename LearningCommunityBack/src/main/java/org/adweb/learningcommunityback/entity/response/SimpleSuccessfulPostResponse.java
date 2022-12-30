package org.adweb.learningcommunityback.entity.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SimpleSuccessfulPostResponse {
    private String message;
    private Timestamp timestamp;

    public SimpleSuccessfulPostResponse(String message) {
        this.timestamp = new Timestamp(System.currentTimeMillis());
        this.message = message;
    }
}
