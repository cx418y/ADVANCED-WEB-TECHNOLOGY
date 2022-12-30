package org.adweb.learningcommunityback.preensure.access;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/***
 * 该注解打在一个方法上
 * 例如
 * @EnsureStudentNotHasCourseAccess
 * public void func(@CourseCode String courseCode, @StudentUsername username){}
 * 在调用func前，会先确保 1.用户名为username的用户存在，且是一个学生 2.课程代码为courseCode的课程存在 3.（1）中的学生还没有加入（2）中的课程
 * 否则抛异常
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnsureStudentNotHasCourseAccess {
}
