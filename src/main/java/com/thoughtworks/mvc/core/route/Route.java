package com.thoughtworks.mvc.core.route;

import com.thoughtworks.mvc.core.ActionDescriptor;
import com.thoughtworks.mvc.core.urlAndVerb.SimpleUrlAndVerb;
import com.thoughtworks.mvc.core.urlAndVerb.UrlAndVerb;

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
}
