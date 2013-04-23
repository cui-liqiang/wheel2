package com.thoughtworks.mvc.core;

import app.domains.Person;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.context.Context;
import sun.beans.editors.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyEditor;
import java.io.IOException;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BaseController {
    private boolean rendered = false;
    private HttpServletRequest request;
    protected HttpServletResponse response;
    protected Map params;

    void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public void setParams(Map params) {
        this.params = params;
    }

    protected void render(String action) throws Exception {
        if(rendered) return;

        try{
            Context context = new VelocityContext();
            for (Field field : this.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                context.put(field.getName(), field.get(this));
            }

            getTemplate(action).merge(context, response.getWriter());
            response.getWriter().flush();
        } finally {
            rendered = true;
        }
    }

    protected void render(String action, Map locals) throws Exception {
        if(rendered) return;

        try{
            Context context = new VelocityContext();
            for (Field field : this.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                context.put(field.getName(), field.get(this));
            }

            for (Object key : locals.keySet()) {
                context.put((String)key, locals.get(key));
            }

            getTemplate(action).merge(context, response.getWriter());
            response.getWriter().flush();
        } finally {
            rendered = true;
        }
    }

    protected void redirect(String action) throws IOException {
        String contextPath = request.getContextPath();
        String realPath = contextPath.equals("") ? action : contextPath + action;
        response.sendRedirect(realPath);
        rendered = true;
    }

    private Template getTemplate(String action) throws Exception {
        return TemplateRepository.getInstance().getTemplate(extractControllerName(this.getClass().getName()), action);
    }

    private String extractControllerName(String controller) {
        Pattern pattern = Pattern.compile(".*\\.([A-Za-z]*)Controller");
        Matcher matcher = pattern.matcher(controller);
        matcher.matches();
        return matcher.group(1).toLowerCase();
    }
}
