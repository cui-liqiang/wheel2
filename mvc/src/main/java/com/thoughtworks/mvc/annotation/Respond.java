package com.thoughtworks.mvc.annotation;

import com.thoughtworks.mvc.mime.MimeType;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target({METHOD})
public @interface Respond {
    MimeType[] value();
}
