package com.thoughtworks.mvc.core;

import core.IocContainer;
import core.IocContainerBuilder;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.context.Context;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

public class Dispatcher extends HttpServlet {
    IocContainer container;
    Map<String,ActionDescriptor> mapping;

    @Override
    public void init() throws ServletException {
        try {
            container = new IocContainerBuilder().withPackageName("app.controllers").build();
        } catch (Exception e) {
            throw new ServletException("init ioc container fail", e);
        }

        String absoluteRootPath = this.getServletContext().getRealPath("/");

        Properties properties = new Properties();
        properties.setProperty("file.resource.loader.path", absoluteRootPath + "/WEB-INF/views/");
        try {
            Velocity.init(properties);
        } catch (Exception e) {
            throw new ServletException("init template engine config fail", e);
        }

        try {
            mapping = new RouterScanner().scan("");
        } catch (Exception e) {
            throw new ServletException("init router fail", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = getNormalizedUrlWithoutContextPath(req);
        ActionDescriptor actionDescriptor = mapping.get(url);

        if(actionDescriptor == null) {
            resp.sendError(404);
            return;
        }

        try {
            actionDescriptor.exec(req, resp, container);
        } catch (Exception e) {
            throw new ServletException("error in handling url \"" + url + "\"", e);
        }
    }

    private String getNormalizedUrlWithoutContextPath(HttpServletRequest req) {
        return req.getRequestURI().substring(req.getContextPath().length());
    }
}
