package com.thoughtworks.mvc.core;

import org.apache.velocity.Template;
import org.apache.velocity.app.Velocity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateRepository {
    public Template getTemplate(String controller, String method) throws Exception {
        return Velocity.getTemplate(controller + "/" + method + ".vm");
    }
}
