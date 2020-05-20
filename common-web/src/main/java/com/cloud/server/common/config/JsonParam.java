package com.cloud.server.common.config;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JsonParam {

    String value() default "";

    boolean required() default true;

    String defaultValue() default "";
}