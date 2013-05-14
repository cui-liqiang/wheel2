package com.thoughtworks.mvc.core.route;

import com.thoughtworks.mvc.core.ActionDescriptor;
import com.thoughtworks.mvc.core.param.Param;
import com.thoughtworks.mvc.core.param.Params;
import com.thoughtworks.mvc.core.param.ParamsCreator;
import com.thoughtworks.mvc.core.urlAndVerb.SimpleUrlAndVerb;
import com.thoughtworks.mvc.core.urlAndVerb.UrlAndVerb;
import com.thoughtworks.mvc.mime.MimeType;
import core.IocContainer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

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

    public void exec(HttpServletRequest req, HttpServletResponse resp, MimeType mimeType, IocContainer container) throws ServletException {
        Params params = ParamsCreator.create(req, this);

        try {
            descriptor.exec(req, resp, mimeType, container, params);
        } catch (Exception e) {
            throw new ServletException("error in handling url \"" + req.getRequestURI() + "\"", e);
        }
    }

    public Param extract(String requestUrl) {
        return urlAndVerb.extract(requestUrl);
    }

    public MimeType getFirstSupportMimeType(Set<MimeType> mimeTypes) {
        if (mimeTypes == null || mimeTypes.size() == 0) {
            return MimeType.HTML;
        }

        for (MimeType mimeType : mimeTypes) {
            if (descriptor.support(mimeType))
                return mimeType;
        }
        return null;
    }
}
