package com.thoughtworks.mvc.core;

import com.thoughtworks.mvc.core.param.Params;
import com.thoughtworks.mvc.util.StringUtil;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.context.Context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class BaseController {

    private boolean rendered = false;
    private HttpServletRequest request;
    protected HttpServletResponse response;
    protected Params params;

    void init(HttpServletRequest request, HttpServletResponse response, Params params) {
        this.request = request;
        this.response = response;
        this.params = params;
    }

    protected void render(String action) throws Exception {
        doRender(action, new HashMap());
    }

    protected void render(String action, Map locals) throws Exception {
        doRender(action, locals);
    }

    private void doRender(String action, Map locals) throws Exception {
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
        String controller = StringUtil.extractControllerName(this.getClass().getName());
        return TemplateRepository.getInstance().getTemplate(controller, action);
    }

}
