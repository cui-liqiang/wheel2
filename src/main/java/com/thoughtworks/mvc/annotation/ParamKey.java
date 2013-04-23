package com.thoughtworks.mvc.annotation;

import com.thoughtworks.mvc.verb.HttpMethod;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

@Retention(RUNTIME)
@Target({PARAMETER})
public @interface ParamKey {
    String value();
}
