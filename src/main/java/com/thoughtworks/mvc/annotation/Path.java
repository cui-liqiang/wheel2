package com.thoughtworks.mvc.annotation;

import com.thoughtworks.mvc.verb.HttpMethod;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target({TYPE, METHOD})
public @interface Path {
    String value();
    HttpMethod httpMethod() default HttpMethod.GET;
}
