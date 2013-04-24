package com.thoughtworks.mvc.core.resource;

import com.thoughtworks.mvc.core.ActionDescriptor;
import com.thoughtworks.mvc.core.route.Route;
import com.thoughtworks.mvc.core.urlAndVerb.UrlAndVerb;
import com.thoughtworks.mvc.core.urlAndVerb.UrlAndVerbFactory;
import com.thoughtworks.mvc.util.StringUtil;
import com.thoughtworks.mvc.verb.HttpMethod;

import java.lang.reflect.Method;

public enum Convention {

    Index(HttpMethod.GET, "/{resource}"),
    Add(HttpMethod.GET, "/{resource}/new"),
    Create(HttpMethod.POST, "/{resource}"),
    Show(HttpMethod.GET, "/{resource}/:id"),
    Edit(HttpMethod.GET, "/{resource}/:id/edit"),
    Update(HttpMethod.PUT, "/{resource}/:id"),
    Destroy(HttpMethod.DELETE, "/{resource}/:id");

    private HttpMethod httpMethod;
    private String template;

    private Convention(HttpMethod httpMethod, String template) {
        this.httpMethod = httpMethod;
        this.template = template;
    }

    public Route createRoute(Class clazz) {
        String name = StringUtil.extractControllerName(clazz.getName());
        UrlAndVerb urlAndVerb = UrlAndVerbFactory.create("", this.httpMethod, template.replace("{resource}", name));

        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getName().equalsIgnoreCase(this.name())) {
                ActionDescriptor descriptor = new ActionDescriptor(clazz, method);
                return new Route(urlAndVerb, descriptor);
            }
        }
        return null;
    }
}
