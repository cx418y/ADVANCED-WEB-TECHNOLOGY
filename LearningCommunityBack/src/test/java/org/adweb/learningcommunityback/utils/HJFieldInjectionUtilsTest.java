package org.adweb.learningcommunityback.utils;

import lombok.*;
import org.junit.Test;

public class HJFieldInjectionUtilsTest {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    static
    class Student {
        private String name;
        private String stuNum;
        private String address;
        private String gender;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    class StudentSub1 {
        private String name;
        private String stuNum;
        private String address;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    class StudentSub2 {
        private String stuNum;
        private String address;
        private String gender;
    }

    @Test
    public void inject() {
        Student student = new Student();

        StudentSub1 sub1 = new StudentSub1();
        sub1.name = "HuaJuan";
        sub1.stuNum = "19302010021";
        sub1.address = "Shanghai";

        StudentSub2 sub2 = new StudentSub2();
        sub2.stuNum = "19302010021";
        sub2.address = "Shanghai";
        sub2.gender = "Male";

        HJFieldInjectionUtils.inject(sub1, student);
        HJFieldInjectionUtils.inject(sub2, student);

        System.out.println(student);

    }
}