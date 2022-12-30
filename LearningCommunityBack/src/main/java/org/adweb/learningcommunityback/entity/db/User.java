package org.adweb.learningcommunityback.entity.db;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@Document(collection = "user")
public class User {

    public static String ROLE_ADMIN = "Admin";
    public static String ROLE_STUDENT = "Student";
    public static String ROLE_TEACHER = "Teacher";
    public static String ROLE_TA = "TA";

    @Id
    private String id;

    @Indexed(name = "username_index", unique = true)
    @Field(name = "username")
    private String username;

    @Field("realname")
    @Indexed(name = "realname_index")
    private String realname;

    @Field("email")
    private String email;

    @Field("password")
    private String password;

    @Field("role")
    private String role;

    @Field("avatar")
    private String avatar;

    public User(String username, String realname, String email, String password, String role) {
        this.username = username;
        this.realname = realname;
        this.email = email;
        this.password = password;
        this.role = role;
        this.avatar = null;
    }
}
