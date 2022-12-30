package org.adweb.learningcommunityback.preensure.access;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/***
 * 该注解打在一个方法上
 * 例如
 * @EnsureUserHasHomeworkAccess
 * public void func(@HomeworkID String homeworkID, @Username username){}
 * 在调用func前，会先确保 1.用户名为username的用户存在 2.homeworkID对应的作业存在 3.（1）中的用户已经加入（2）中对应作业所属的课程
 * 否则抛异常
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnsureUserHasHomeworkAccess {
}
