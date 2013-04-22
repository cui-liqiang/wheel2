package com.thoughtworks.mvc.core;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class ParamsCreator {
    public static Map create(HttpServletRequest req) {
        Map map = new HashMap();

        Enumeration<String> parameterNames = req.getParameterNames();
        if(parameterNames == null) return map;

        while(parameterNames.hasMoreElements()) {
            String fullFormName = parameterNames.nextElement();

            String[] parameterValues = req.getParameterValues(fullFormName);
            for(int times = 0;times < parameterValues.length;times++) {
                addValueToMapHierarchy(map, fullFormName, times, parameterValues[times]);
            }
        }

        return map;
    }

    private static void addValueToMapHierarchy(Map map, String fullFormName, int times, String valueForThisTime) {
        String[] names = fullFormName.split("\\.");
        int lastElemPos = names.length - 1;

        for(int i = 0;i < lastElemPos;i++) {
            String name = names[i];
            map = name.endsWith("[]") ? findOrCreateMapInListByNameOccurenceTimes(  times
                                                                                      , getOrCreateList(  map
                                                                                                        , excludeLastTwo(name)))
                                          : getOrCreateMap(map, name);
        }

        String lastPartName = names[lastElemPos];
        if(lastPartName.endsWith("[]")) {
            List list = getOrCreateList(map, excludeLastTwo(lastPartName));
            list.add(valueForThisTime);
            map.put(lastPartName, list);
        } else {
            map.put(lastPartName, valueForThisTime);
        }
    }

    private static String excludeLastTwo(String lastPartName) {
        return lastPartName.substring(0, lastPartName.length() - 2);
    }

    private static Map findOrCreateMapInListByNameOccurenceTimes(int times, List list) {
        Map nextLevelMap;
        Map map;
        if(times >= list.size()) {
            nextLevelMap = new HashMap();
            list.add(nextLevelMap);
        } else {
            nextLevelMap = (Map)list.get(times);
        }
        map = nextLevelMap;
        return map;
    }

    private static Map getOrCreateMap(Map map, String name) {
        Map nextLevelMap = (Map)map.get(name);
        if (nextLevelMap == null) {
            nextLevelMap = new HashMap();
            map.put(name, nextLevelMap);
        }
        return nextLevelMap;
    }

    private static List getOrCreateList(Map map, String arrayName) {
        List list = (List)map.get(arrayName);
        if(list == null) {
            list = new ArrayList();
            map.put(arrayName, list);
        }
        return list;
    }
}
