package com.thoughtworks.mvc.core.route;

import com.thoughtworks.mvc.core.ActionDescriptor;
import com.thoughtworks.mvc.core.param.FormParamsCreator;
import com.thoughtworks.mvc.core.param.Param;
import com.thoughtworks.mvc.core.param.Params;
import com.thoughtworks.mvc.core.param.ParamsCreator;
import com.thoughtworks.mvc.core.urlAndVerb.SimpleUrlAndVerb;
import com.thoughtworks.mvc.core.urlAndVerb.UrlAndVerb;
import core.IocContainer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Route {
    private UrlAndVerb urlAndVerb;
    private ActionDescriptor descriptor;

    public Route(UrlAndVerb urlAndVerb, ActionDescriptor descriptor) {
        this.urlAndVerb = urlAndVerb;
        this.descriptor = descriptor;
    }

    public ActionDescriptor getActionDescriptor() {
        return descriptor;
    }

    public boolean match(SimpleUrlAndVerb simpleUrlAndVerb) {
        return urlAndVerb.match(simpleUrlAndVerb);
    }

    public void exec(HttpServletRequest req, HttpServletResponse resp, IocContainer container) throws ServletException {
        Params params = ParamsCreator.create(req, this);

        try {
            descriptor.exec(req, resp, container, params);
        } catch (Exception e) {
            throw new ServletException("error in handling url \"" + req.getRequestURI() + "\"", e);
        }
    }

    public Param extract(String requestUrl) {
        return urlAndVerb.extract(requestUrl);
    }
}
