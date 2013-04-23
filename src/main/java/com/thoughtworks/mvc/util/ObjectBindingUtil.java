package com.thoughtworks.mvc.util;

import sun.beans.editors.*;

import java.beans.PropertyEditor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ObjectBindingUtil {
    static Logger log = Logger.getLogger(ObjectBindingUtil.class.getName());

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

    static public <T> T toObject(Class<T> clazz, Map map) throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchFieldException, ClassNotFoundException {
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
            } else if(value instanceof List) {
                ParameterizedType pt = (ParameterizedType)clazz.getDeclaredField(name).getGenericType();
                // pt.getActualTypeArguments()[0].toString() will be "class #{some qualified name}", so 6 is to skip the leading "class "
                obj = toList(Class.forName(pt.getActualTypeArguments()[0].toString().substring(6)), (List)value);
            }

            setter.invoke(t, obj);
        }

        return t;
    }

    static public <T> List<T> toList(Class<T> clazz, List maps) throws InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException, NoSuchFieldException {
        List<T> list = new ArrayList<T>();

        for (Object map : maps) {
            list.add(toObject(clazz, (Map)map));
        }

        return list;
    }

    static public Object toSimpleObject(Class<?> type, String value) throws IllegalAccessException, InstantiationException {
        Class<? extends PropertyEditor> aClass = editorMap.get(type);
        if(aClass == null) return null;

        PropertyEditor propertyEditor = aClass.newInstance();
        try{
            propertyEditor.setAsText(value);
            return propertyEditor.getValue();
        } catch (RuntimeException e) {
            log.log(Level.SEVERE, "cannot convert \"" + value + "\" to " + type.getName());
            e.printStackTrace();
        }
        return DefaultValue.defaultValueOf(type);
    }

    static private List<Method> getSetters(Method[] allMethods) {
        List<Method> filtered = new ArrayList<Method>();
        for (Method method : allMethods) {
            if(method.getName().matches("set[A-Z].*")) {
                filtered.add(method);
            }
        }

        return filtered;
    }
}
