package org.adweb.learningcommunityback.preensure.access;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/***
 * 该注解打在一个方法上
 * 例如
 * @EnsureUserHasSectionAccess
 * public void func(@SectionID String sectionID, @Username username){}
 * 在调用func前，会先确保 1.用户名为username的用户存在 2.sectionID对应的小节存在 3.（1）中的用户已经加入（2）中对应小节的课程
 * 否则抛异常
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnsureUserHasSectionAccess {
}
