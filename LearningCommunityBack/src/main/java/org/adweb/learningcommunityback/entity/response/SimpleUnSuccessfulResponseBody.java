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
public class SimpleUnSuccessfulResponseBody {
    private String errorCode;
    private String message;
    private Timestamp timestamp;
}
