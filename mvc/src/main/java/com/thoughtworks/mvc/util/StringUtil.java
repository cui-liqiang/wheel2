package com.thoughtworks.mvc.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    public static String extractControllerName(String controller) {
        Pattern pattern = Pattern.compile(".*\\.([A-Za-z]*)Controller");
        Matcher matcher = pattern.matcher(controller);
        matcher.matches();
        return matcher.group(1).toLowerCase();
    }

}
