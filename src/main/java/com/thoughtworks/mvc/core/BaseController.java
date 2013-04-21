package com.thoughtworks.mvc.core;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.context.Context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BaseController {
    private boolean rendered = false;
    private HttpServletRequest request;
    protected HttpServletResponse response;

    void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    void setResponse(HttpServletResponse response) {
        this.response = response;
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
