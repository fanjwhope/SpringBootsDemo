package com.gutai.Annotation;

/**
 * Created by 82421 on 2017/10/16.
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据库中字段的特殊类型，如日期格式.Double ,float 等格式化
 * 并且判断格式是否是日期格式。
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface FieldFormatter {
    String formatter();
    boolean isDate()default false;
}
