package com.thoughtworks.mvc.util;

public class DefaultValue {
    static public Object defaultValueOf(Class<?> clazz) {
        if (clazz.equals(boolean.class)) {
            return false;
        } else if (clazz.equals(byte.class)) {
            return 0;
        } else if (clazz.equals(short.class)) {
            return 0;
        } else if (clazz.equals(int.class)) {
            return 0;
        } else if (clazz.equals(long.class)) {
            return 0;
        } else if (clazz.equals(float.class)) {
            return 0.0;
        } else if (clazz.equals(double.class)) {
            return 0.0;
        } else {
            return null;
        }
    }
}
