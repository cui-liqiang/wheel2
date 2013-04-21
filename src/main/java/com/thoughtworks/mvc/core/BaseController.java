package com.thoughtworks.mvc.core;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.context.Context;
import sun.beans.editors.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyEditor;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
    static private Map<Class, Class<? extends PropertyEditor>> editorMap = new HashMap<Class, Class<? extends PropertyEditor>>();

    static {
        editorMap.put(int.class, IntEditor.class);
        editorMap.put(Integer.class, IntEditor.class);
        editorMap.put(short.class, ShortEditor.class);
        editorMap.put(Short.class, ShortEditor.class);
        editorMap.put(long.class, LongEditor.class);
        editorMap.put(Long.class, LongEditor.class);
        editorMap.put(byte.class, ByteEditor.class);
        editorMap.put(Byte.class, ByteEditor.class);
        editorMap.put(double.class, DoubleEditor.class);
        editorMap.put(Double.class, DoubleEditor.class);
        editorMap.put(String.class, StringEditor.class);
    }

    static void addPropertyEditor(Class clazz, Class<? extends PropertyEditor> editor) {
        editorMap.put(clazz, editor);
    }

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

    public <T> T toObject(Class<T> clazz, Map map) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        T t = clazz.newInstance();
        if(map == null) return t;

        List<Method> setters = getSetters(clazz.getMethods());
        for (Method setter : setters) {
            String name = setter.getName().substring(3).toLowerCase();
            Object value = map.get(name);
            if(value == null) continue;

            Object obj = null;
            Class<?> type = setter.getParameterTypes()[0];
            if(value instanceof String) {
                obj = toSimpleObject(type, (String) value);
            } else if(value instanceof Map){
                obj = toObject(type, (Map)value);
            }

            setter.invoke(t, obj);
        }

        return t;
    }

    private Object toSimpleObject(Class<?> type, String value) throws IllegalAccessException, InstantiationException {
        Class<? extends PropertyEditor> aClass = editorMap.get(type);
        if(aClass == null) return null;

        PropertyEditor propertyEditor = aClass.newInstance();
        propertyEditor.setAsText(value);
        return propertyEditor.getValue();
    }

    private List<Method> getSetters(Method[] allMethods) {
        List<Method> filtered = new ArrayList<Method>();
        for (Method method : allMethods) {
            if(method.getName().matches("set[A-Z].*")) {
                filtered.add(method);
            }
        }

        return filtered;
    }


}
