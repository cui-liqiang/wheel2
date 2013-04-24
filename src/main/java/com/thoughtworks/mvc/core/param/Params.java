package com.thoughtworks.mvc.core.param;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Params {

    List<Param> params = new ArrayList<Param>();

    public Params(Param... params) {
        Collections.addAll(this.params, params);
    }

    public Object get(String key) {
        for (Param param : params) {
            if (param.getKey().equals(key)) {
                return param.getValue();
            }
        }
        return null;
    }

    public void put(String key, Object value) {
        params.add(new Param(key, value));
    }

    public Params merge(Params params) {
        for (Param param : params.toArray()) {
            this.params.add(param);
        }
        return this;
    }

    private ArrayList<Param> toArray() {
        return (ArrayList<Param>) params;
    }
}
