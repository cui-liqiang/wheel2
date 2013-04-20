package com.thoughtworks.mvc.core;

import app.controllers.HomeController;
import core.IocContainer;
import core.IocContainerBuilder;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.context.Context;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Properties;

public class Dispatcher extends HttpServlet {
    IocContainer container;

    public Dispatcher() throws Exception {
        container = new IocContainerBuilder().withPackageName("app.controllers").build();
    }

    @Override
    public void init() throws ServletException {
        String absoluteRootPath = this.getServletContext().getRealPath("/");

        Properties properties = new Properties();
        properties.setProperty("file.resource.loader.path", absoluteRootPath + "/WEB-INF/views/");
        try {
            Velocity.init(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String controllerName = null;
        String methodName = null;
        Template template = null;

        if(req.getRequestURI().endsWith("/home")){
            String tplName;
            controllerName = "app.controllers." + StringUtils.capitalize("home") + "Controller";
            methodName = "index";
            tplName = "home" + "/" + methodName + ".vm";
            try {
                template = Velocity.getTemplate(tplName);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ServletException("error in looking for view template: " + tplName);
            }
        }

        try {
            Object controller = container.getBean(Class.forName(controllerName));
            Method method = Class.forName(controllerName).getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(controller, req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        try {
            Context context = new VelocityContext();
            Enumeration attributeNames = req.getAttributeNames();
            while(attributeNames.hasMoreElements()) {
                String name = (String)attributeNames.nextElement();
                context.put(name, req.getAttribute(name));
            }
            template.merge(context, resp.getWriter());
            resp.getWriter().flush();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
}
