package com.got.mixsql.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * nsql 对应的实体类。
 * @author zzwu
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface NoSqlEntityClass {
	@SuppressWarnings("rawtypes")
	Class value();
}
