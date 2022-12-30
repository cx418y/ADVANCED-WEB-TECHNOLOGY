package org.adweb.learningcommunityback.preensure.section;

/***
 * 该注解打在一个方法上
 * 例如
 * @EnsureSectionNotExists
 * public void func(@SectionName String sectionName, @ChapterID chapterID){}
 * 在调用func前，会先确保chapterID对应的章是否包含sectionName对应的小节，否则抛异常
 */
public @interface EnsureSectionNameNotExistsInChapter {
}
