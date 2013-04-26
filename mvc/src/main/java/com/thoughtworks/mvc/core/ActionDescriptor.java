package com.thoughtworks.mvc.core;

import com.thoughtworks.mvc.annotation.Param;
import com.thoughtworks.mvc.annotation.Respond;
import com.thoughtworks.mvc.core.param.Params;
import com.thoughtworks.mvc.mime.MimeType;
import com.thoughtworks.mvc.util.DefaultValue;
import com.thoughtworks.mvc.util.ObjectBindingUtil;
import core.IocContainer;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ActionDescriptor {
    private final Class controllerClass;
    private final Method action;
    private Set<MimeType> supportedMimes = new HashSet<MimeType>();

    public ActionDescriptor(Class controllerClass, Method action) {
        this.controllerClass = controllerClass;
        this.action = action;
        addMimeTypes();
    }

    private void addMimeTypes() {
        Respond actionRespond = action.getAnnotation(Respond.class);
        Respond classRespond = (Respond) controllerClass.getAnnotation(Respond.class);

        if (actionRespond == null && classRespond == null) {
            supportedMimes.add(MimeType.HTML);
            return;
        }

        if (classRespond != null) {
            addMimeTypes(classRespond);
        }

        if (actionRespond != null) {
            addMimeTypes(actionRespond);
        }
    }

    private void addMimeTypes(Respond actionRespond) {
        MimeType[] value = actionRespond.value();

        for (MimeType mimeType : value) {
            supportedMimes.add(mimeType);
        }
    }

    public void exec(HttpServletRequest req, HttpServletResponse resp, MimeType mimeType, IocContainer container, Params params) throws Exception {
        BaseController controller = (BaseController) container.getBean(controllerClass);

        controller.init(req, resp, params, mimeType);
        Object o = invokeAction(controller, params);
        if ("void".equals(action.getReturnType().toString())) {
            controller.render(action.getName());
        } else {
            Map map = new HashMap();
            map.put(getModelName(), o);
            controller.render(action.getName(), map);
        }
    }

    private String getModelName() {
        if (action.getGenericReturnType() instanceof ParameterizedTypeImpl) {
            Class returnType = (Class) ((ParameterizedTypeImpl) action.getGenericReturnType()).getActualTypeArguments()[0];
            return returnType.getSimpleName().toLowerCase() + "s";
        } else {
            return action.getReturnType().getSimpleName().toLowerCase();
        }
    }

    private Object invokeAction(Object bean, Params httpParams) throws IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException, NoSuchFieldException {
        return invokeActionWithParams(bean, getActionParams(httpParams));
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

    private Object invokeActionWithParams(Object bean, List<Object> actionParams) throws IllegalAccessException, InvocationTargetException {
        switch (actionParams.size()) {
            case 0:
                return action.invoke(bean);
            case 1:
                return action.invoke(bean, actionParams.get(0));
            case 2:
                return action.invoke(bean, actionParams.get(0), actionParams.get(1));
            case 3:
                return action.invoke(bean, actionParams.get(0), actionParams.get(1), actionParams.get(2));
            case 4:
                return action.invoke(bean, actionParams.get(0), actionParams.get(1), actionParams.get(2), actionParams.get(3));
            case 5:
                return action.invoke(bean, actionParams.get(0), actionParams.get(1), actionParams.get(2), actionParams.get(3), actionParams.get(4));
        }
        return null;
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

    public boolean support(MimeType mimeType) {
        for (MimeType supportedMime : supportedMimes) {
            if (mimeType.equals(supportedMime))
                return true;
        }
        return false;
    }
}
