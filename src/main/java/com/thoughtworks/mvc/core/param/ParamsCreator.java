package com.thoughtworks.mvc.core.param;

import com.thoughtworks.mvc.core.route.Route;

import javax.servlet.http.HttpServletRequest;

public class ParamsCreator {

    public static Params create(HttpServletRequest req, Route route) {

        Params params = FormParamsCreator.create(req);
        return params.merge(UrlParamsCreator.create(req, route));
    }

}
