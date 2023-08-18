package com.example.demo.annotation;

import com.example.demo.data.BaseTestData;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomFileSource {
    String file();

    Class<? extends BaseTestData> clazz();

    String type() default "csv";

    String delimiter() default ",";

    String encoding() default "UTF-8";

    String lineSeparator() default "\n";

    int skip() default 1;

    boolean trim() default true;

    String commentPrefix() default "#";

    String nullString() default "NULL";

    String nullNonNumeric() default "NaN";

    String dateFormat() default "yyyy-MM-dd HH:mm:ss";

    String locale() default "en_US";

    String timezone() default "GMT";

    String charset() default "UTF-8";


}

