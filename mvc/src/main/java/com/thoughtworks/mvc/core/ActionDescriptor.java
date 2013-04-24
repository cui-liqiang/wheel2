package com.thoughtworks.mvc.core;

import com.thoughtworks.mvc.annotation.Param;
import com.thoughtworks.mvc.core.param.Params;
import com.thoughtworks.mvc.util.DefaultValue;
import com.thoughtworks.mvc.util.ObjectBindingUtil;
import core.IocContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ActionDescriptor {
    private final Class controllerClass;
    private final Method action;

    public ActionDescriptor(Class controllerClass, Method action) {
        this.controllerClass = controllerClass;
        this.action = action;
    }

    public void exec(HttpServletRequest req, HttpServletResponse resp, IocContainer container, Params params) throws Exception {
        BaseController controller = (BaseController) container.getBean(controllerClass);

        controller.init(req, resp, params);
        invokeAction(controller, params);
        controller.render(action.getName());
    }

    private void invokeAction(Object bean, Params httpParams) throws IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException, NoSuchFieldException {
        invokeActionWithParams(bean, getActionParams(httpParams));
    }

    private List<Object> getActionParams(Params httpParams) throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchFieldException, ClassNotFoundException {
        Annotation[][] parameterAnnotations = action.getParameterAnnotations();
        Class<?>[] parameterTypes = action.getParameterTypes();
        Type[] genericParameterTypes = action.getGenericParameterTypes();

        List<Object> actionParams = new ArrayList<Object>();

        for (int i = 0; i < parameterAnnotations.length; i++) {
            Annotation[] parameterAnnotation = parameterAnnotations[i];
            Class<?> parameterType = parameterTypes[i];

            Object param = DefaultValue.defaultValueOf(parameterType);
            String key = getParamsKey(parameterAnnotation);

            if (key != null) {
                Object value = httpParams.get(key);
                if (value instanceof Params) {
                    param = ObjectBindingUtil.toObject(parameterType, (Params) value);
                } else if (value instanceof List) {
                    Type genericParameterType = genericParameterTypes[i];
                    ParameterizedType pt = (ParameterizedType) genericParameterType;
                    // pt.getActualTypeArguments()[0].toString() will be "class #{some qualified name}", so 6 is to skip the leading "class "
                    param = ObjectBindingUtil.toList(Class.forName(pt.getActualTypeArguments()[0].toString().substring(6)), (List) value);
                } else if (value instanceof String) {
                    param = ObjectBindingUtil.toSimpleObject(parameterType, (String) value);
                }
            }

            actionParams.add(param);
        }
        return actionParams;
    }

    private void invokeActionWithParams(Object bean, List<Object> actionParams) throws IllegalAccessException, InvocationTargetException {
        switch (actionParams.size()) {
            case 0:
                action.invoke(bean);
                return;
            case 1:
                action.invoke(bean, actionParams.get(0));
                return;
            case 2:
                action.invoke(bean, actionParams.get(0), actionParams.get(1));
                return;
            case 3:
                action.invoke(bean, actionParams.get(0), actionParams.get(1), actionParams.get(2));
                return;
            case 4:
                action.invoke(bean, actionParams.get(0), actionParams.get(1), actionParams.get(2), actionParams.get(3));
                return;
            case 5:
                action.invoke(bean, actionParams.get(0), actionParams.get(1), actionParams.get(2), actionParams.get(3), actionParams.get(4));
                return;
        }
    }

    private String getParamsKey(Annotation[] annotations) {
        for (Annotation annotation : annotations) {
            if (annotation instanceof Param) {
                return ((Param) annotation).value();
            }
        }
        return null;
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
