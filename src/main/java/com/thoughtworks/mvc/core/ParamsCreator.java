package com.thoughtworks.mvc.core;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class ParamsCreator {
    public static Map create(HttpServletRequest req) {
        Map map = new HashMap();

        Enumeration parameterNames = req.getParameterNames();
        if(parameterNames == null) return map;

        while(parameterNames.hasMoreElements()) {
            addValueToMapHierarchy(req, map, parameterNames);
        }

        return map;
    }

    private static void addValueToMapHierarchy(HttpServletRequest req, Map map, Enumeration parameterNames) {
        String fullFormName = (String) parameterNames.nextElement();
        String[] names = fullFormName.split("\\.");
        int lastElemPos = names.length - 1;

        Map rootMap = map;
        for(int i = 0;i < lastElemPos;i++) {
            Map nextLevelMap = (Map)rootMap.get(names[i]);
            if (nextLevelMap == null) {
                nextLevelMap = new HashMap();
                rootMap.put(names[i], nextLevelMap);
            }
            rootMap = nextLevelMap;
        }
        rootMap.put(names[lastElemPos], req.getParameter(fullFormName));
    }
}
