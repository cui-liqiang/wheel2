package com.thoughtworks.mvc.core;

import com.google.common.base.Predicate;
import com.thoughtworks.mvc.annotation.Path;
import core.IocContainer;
import util.ClassPathUtil;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Map<UrlAndVerb, ActionDescriptor> scan(String basePackage) throws Exception {
        List<String> classNames = ClassPathUtil.getClassNamesInPackage(controllersPackage(basePackage));
        Collection<String> controllers = filter(classNames, new Predicate<String>() {
            @Override
            public boolean apply(String className) {
                return className.endsWith("Controller");
            }
        });
        Map<UrlAndVerb, ActionDescriptor> mapping = new HashMap<UrlAndVerb, ActionDescriptor>();
        for (String controller : controllers) {
            addMappingsFromControllerTo(controller, mapping);
        }
        return mapping;
    }

    private String controllersPackage(String basePackage) {
        return basePackage.equals("") ? "app.controllers" : basePackage + ".app.controllers";
    }

    private void addMappingsFromControllerTo(String controller, Map<UrlAndVerb, ActionDescriptor> mapping) throws Exception {
        Class controllerClass = Class.forName(controller);
        Assert(controllerClass.isAnnotationPresent(Path.class), "Controller class " + controllerClass + " doesn't have a @Path annotation");

        container.register(controllerClass, true);
        addMappingForEachAction(mapping, controllerClass, ((Path) controllerClass.getAnnotation(Path.class)).value());
    }

    private void addMappingForEachAction(Map<UrlAndVerb, ActionDescriptor> mapping, Class controllerClass, String baseUrl) {
        for (Method method : filterWithPathAnnotation(controllerClass.getMethods())) {
            Path actionPathAnnotation = method.getAnnotation(Path.class);
            ActionDescriptor descriptor = new ActionDescriptor(controllerClass, method);

            mapping.put(new UrlAndVerb(actionPathAnnotation.httpMethod(), baseUrl + actionPathAnnotation.value()), descriptor);
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
