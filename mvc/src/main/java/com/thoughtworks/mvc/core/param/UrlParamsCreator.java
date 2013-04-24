package com.thoughtworks.mvc.core.param;

import com.thoughtworks.mvc.core.route.Route;

import javax.servlet.http.HttpServletRequest;

public class UrlParamsCreator {

    public static Params create(HttpServletRequest req, Route route) {
        Param param = route.extract(req.getRequestURI().substring(req.getContextPath().length()));
        return new Params(param);
    }

}
