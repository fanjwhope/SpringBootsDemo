package com.gutai.Annotation;

/**
 * Created by 82421 on 2017/10/16.
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *  定义表中的字段属性
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface TableField {
    String name(); //定义字段名称,在程序中默认字段名
   // boolean isNeed();//判断字段是否必须。默认不是必须字段。
}
