package org.adweb.learningcommunityback.preensure.chapter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/***
 * 该注解打在一个方法上
 * 例如
 * @EnsureChapterExists
 * public void func(@ChapterID String chapterID){}
 * 在调用func前，会先确保chapterID对应的章是否在数据库中不存在，否则抛异常
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnsureChapterIDExistsInCourse {
}
