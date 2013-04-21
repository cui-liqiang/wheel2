package com.thoughtworks.mvc.core;

import core.IocContainer;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.context.Context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ActionDescriptor {
    private final Class controllerClass;
    private final Method action;
    private Template template;

    public ActionDescriptor(Class controllerClass, Method action, Template template) {
        this.controllerClass = controllerClass;
        this.action = action;
        this.template = template;
    }

    public void exec(HttpServletRequest req, HttpServletResponse resp, IocContainer container) throws Exception {
        Object bean = container.getBean(controllerClass);
        action.invoke(bean, req, resp);


        Context context = new VelocityContext();
        for (Field field : controllerClass.getDeclaredFields()) {
            field.setAccessible(true);
            context.put(field.getName(), field.get(bean));
        }

        template.merge(context, resp.getWriter());
        resp.getWriter().flush();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActionDescriptor)) return false;

        ActionDescriptor that = (ActionDescriptor) o;

        if (action != null ? !action.equals(that.action) : that.action != null) return false;
        if (controllerClass != null ? !controllerClass.equals(that.controllerClass) : that.controllerClass != null)
            return false;
        if (template != null ? !template.equals(that.template) : that.template != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = controllerClass != null ? controllerClass.hashCode() : 0;
        result = 31 * result + (action != null ? action.hashCode() : 0);
        result = 31 * result + (template != null ? template.hashCode() : 0);
        return result;
    }
}
