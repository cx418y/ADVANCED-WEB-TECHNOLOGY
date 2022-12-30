package org.adweb.learningcommunityback.preensure.user;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/***
 * 该注解打在一个方法上
 * 例如
 * @EnsureTAExists
 * public void func(@TAUsername String username){}
 * 在调用func前，会先确保username对应的用户是否在数据库中存在且角色是一个助教，否则抛异常
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EnsureTAExists {
}
