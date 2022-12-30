package org.adweb.learningcommunityback.entity.db;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "course")
public class Course {

    @Id
    private String id;

    @Indexed(name = "course_code_index", unique = true)
    private String courseCode;

    @Indexed(name = "course_name_index", unique = true)
    private String courseName;

    private String description;

    private Boolean allowStudentAddProblem;

    public Boolean getAllowStudentAddProblem(){
        return allowStudentAddProblem != null && allowStudentAddProblem;
    }


    public Course(String courseCode, String courseName, String description) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.description = description;
    }
}
