package com.thoughtworks.mvc.core;

import com.google.common.base.Predicate;
import com.thoughtworks.mvc.annotation.Path;
import com.thoughtworks.mvc.core.route.Routes;
import com.thoughtworks.mvc.core.urlAndVerb.UrlAndVerbFactory;
import core.IocContainer;
import util.ClassPathUtil;

import java.lang.reflect.Method;
import java.util.*;

import static com.google.common.collect.Collections2.filter;
import static util.AssertUtil.Assert;


public class RouterScanner {
    TemplateRepository templateRepo = new TemplateRepository();
    private IocContainer container;

    public RouterScanner(TemplateRepository templateRepo, IocContainer container) {
        this.templateRepo = templateRepo;
        this.container = container;
    }

    public RouterScanner(IocContainer container) {
        this.container = container;
    }

    public Routes scan(String basePackage) throws Exception {
        List<String> classNames = ClassPathUtil.getClassNamesInPackage(controllersPackage(basePackage));
        Collection<String> controllers = filter(classNames, new Predicate<String>() {
            @Override
            public boolean apply(String className) {
                return className.endsWith("Controller");
            }
        });
        Routes routes = new Routes();

        for (String controller : controllers) {
            addMappingsFromControllerTo(controller, routes);
        }
        return routes;
    }

    private String controllersPackage(String basePackage) {
        return basePackage.equals("") ? "app.controllers" : basePackage + ".app.controllers";
    }

    private void addMappingsFromControllerTo(String controller, Routes routes) throws Exception {
        Class controllerClass = Class.forName(controller);
        Assert(controllerClass.isAnnotationPresent(Path.class), "Controller class " + controllerClass + " doesn't have a @Path annotation");

        container.register(controllerClass, true);
        addMappingForEachAction(routes, controllerClass, ((Path) controllerClass.getAnnotation(Path.class)).value());
    }

    private void addMappingForEachAction(Routes routes, Class controllerClass, String baseUrl) {
        for (Method method : filterWithPathAnnotation(controllerClass.getMethods())) {
            ActionDescriptor descriptor = new ActionDescriptor(controllerClass, method);
            routes.put(UrlAndVerbFactory.create(baseUrl, method.getAnnotation(Path.class).httpMethod(), method.getAnnotation(Path.class).value()), descriptor);
        }
    }

    private List<Method> filterWithPathAnnotation(Method[] allMethods) {
        List<Method> forReturn = new ArrayList<Method>();
        for (Method method : allMethods) {
            if (method.isAnnotationPresent(Path.class)) {
                forReturn.add(method);
            }
        }
        return forReturn;
    }
}
