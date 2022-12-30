package org.adweb.learningcommunityback.entity.db;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "course_file")
@CompoundIndexes({@CompoundIndex(
        name = "fileName_courseCode_index",
        def = "{fileName:1, courseCode:1}",
        unique = true)}
)
public class CourseFile {
    @Id
    private String courseFileID;

    private String fileName;

    private String description;

    private String courseCode;

    private String fromUsername;

    @Indexed(name = "gridFsID", unique = true)
    private String gridFSID;
}
