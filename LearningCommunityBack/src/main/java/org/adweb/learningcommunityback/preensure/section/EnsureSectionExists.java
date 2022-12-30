package org.adweb.learningcommunityback.preensure.section;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/***
 * 该注解打在一个方法上
 * 例如
 * @EnsureSectionExists
 * public void func(@SectionID String sectionID){}
 * 在调用func前，会先确保sectionID对应的小节是否在数据库中存在，否则抛异常
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnsureSectionExists {
}
