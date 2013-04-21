package com.thoughtworks.mvc.core;

import core.IocContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class ActionDescriptor {
    private final Class controllerClass;
    private final Method action;

    public ActionDescriptor(Class controllerClass, Method action) {
        this.controllerClass = controllerClass;
        this.action = action;
    }

    public void exec(HttpServletRequest req, HttpServletResponse resp, IocContainer container) throws Exception {
        Object bean = container.getBean(controllerClass);
        BaseController controller = (BaseController) bean;

        controller.setRequest(req);
        controller.setResponse(resp);

        action.invoke(bean);
        controller.render(action.getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActionDescriptor)) return false;

        ActionDescriptor that = (ActionDescriptor) o;

        if (action != null ? !action.equals(that.action) : that.action != null) return false;
        if (controllerClass != null ? !controllerClass.equals(that.controllerClass) : that.controllerClass != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = controllerClass != null ? controllerClass.hashCode() : 0;
        result = 31 * result + (action != null ? action.hashCode() : 0);
        return result;
    }
}
