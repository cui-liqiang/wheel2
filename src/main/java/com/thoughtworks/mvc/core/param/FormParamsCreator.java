package com.thoughtworks.mvc.core.param;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class FormParamsCreator {

    public static Params create(HttpServletRequest req) {
        Params params = new Params();

        Enumeration<String> parameterNames = req.getParameterNames();
        if (parameterNames == null) return params;

        while (parameterNames.hasMoreElements()) {
            String fullFormName = parameterNames.nextElement();

            String[] parameterValues = req.getParameterValues(fullFormName);
            for (int times = 0; times < parameterValues.length; times++) {
                addValueToMapHierarchy(params, fullFormName, times, parameterValues[times]);
            }
        }

        return params;
    }

    private static void addValueToMapHierarchy(Params params, String fullFormName, int times, String valueForThisTime) {
        String[] names = fullFormName.split("\\.");
        int lastElemPos = names.length - 1;

        for (int i = 0; i < lastElemPos; i++) {
            String name = names[i];
            if (name.endsWith("[]")) {
                params = findOrCreateParamsInListByNameOccurenceTimes(times, getOrCreateList(params, excludeLastTwo(name)));
            } else {
                params = getOrCreateMap(params, name);
            }
        }

        String lastPartName = names[lastElemPos];
        if (lastPartName.endsWith("[]")) {
            List list = getOrCreateList(params, excludeLastTwo(lastPartName));
            list.add(valueForThisTime);
            params.put(lastPartName, list);
        } else {
            params.put(lastPartName, valueForThisTime);
        }
    }

    private static String excludeLastTwo(String lastPartName) {
        return lastPartName.substring(0, lastPartName.length() - 2);
    }

    private static Params findOrCreateParamsInListByNameOccurenceTimes(int times, List list) {
        Params nextLevelParams;
        Params params;
        if (times >= list.size()) {
            nextLevelParams = new Params();
            list.add(nextLevelParams);
        } else {
            nextLevelParams = (Params) list.get(times);
        }
        params = nextLevelParams;
        return params;
    }

    private static Params getOrCreateMap(Params params, String name) {
        Params nextLevelParams = (Params) params.get(name);
        if (nextLevelParams == null) {
            nextLevelParams = new Params();
            params.put(name, nextLevelParams);
        }
        return nextLevelParams;
    }

    private static List getOrCreateList(Params params, String arrayName) {
        List list = (List) params.get(arrayName);
        if (list == null) {
            list = new ArrayList();
            params.put(arrayName, list);
        }
        return list;
    }
}
