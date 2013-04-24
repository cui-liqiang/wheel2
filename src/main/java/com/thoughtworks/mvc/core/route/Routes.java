package com.thoughtworks.mvc.core.route;

import com.thoughtworks.mvc.core.ActionDescriptor;
import com.thoughtworks.mvc.core.urlAndVerb.SimpleUrlAndVerb;
import com.thoughtworks.mvc.core.urlAndVerb.UrlAndVerb;

import java.util.ArrayList;
import java.util.List;

public class Routes {

    List<Route> routes = new ArrayList<Route>();

    public void put(UrlAndVerb urlAndVerb, ActionDescriptor descriptor) {
        routes.add(new Route(urlAndVerb, descriptor));
    }

    public ActionDescriptor get(SimpleUrlAndVerb simpleUrlAndVerb) {
        for (Route route : routes) {
            if (route.match(simpleUrlAndVerb)) {
                return route.getActionDescriptor();
            }
        }
        return null;
    }
}
