package com.thoughtworks.mvc.core;

import org.apache.velocity.Template;
import org.apache.velocity.app.Velocity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateRepository {
    static TemplateRepository instance = null;

    public static TemplateRepository getInstance() {
        if(instance == null)
            instance = new TemplateRepository();
        return instance;
    }

    //just for test...
    public static void setInstance(TemplateRepository otherInstance) {
        instance = otherInstance;
    }
    //just for test...
    public static void clearInstance() {
        instance = null;
    }


    public Template getTemplate(String controller, String method) throws Exception {
        return Velocity.getTemplate(controller + "/" + method + ".vm");
    }
}
