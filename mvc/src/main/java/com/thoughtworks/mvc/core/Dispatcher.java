package com.thoughtworks.mvc.core;

import com.thoughtworks.mvc.core.route.Route;
import com.thoughtworks.mvc.core.route.Routes;
import com.thoughtworks.mvc.core.urlAndVerb.SimpleUrlAndVerb;
import com.thoughtworks.mvc.verb.HttpMethod;
import core.IocContainer;
import core.IocContainerBuilder;
import org.apache.velocity.app.Velocity;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

public class Dispatcher extends HttpServlet {
    IocContainer container;
    Routes routes;

    @Override
    public void init() throws ServletException {
        initContainer();
        initVelocity();
        scanRouter();
    }

    private void initContainer() throws ServletException {
        try {
            container = new IocContainerBuilder().withPackageName("app.controllers").withPackageName("app.services").build();
        } catch (Exception e) {
            throw new ServletException("init ioc container fail", e);
        }
    }

    private void initVelocity() throws ServletException {
        String absoluteRootPath = this.getServletContext().getRealPath("/");

        Properties properties = new Properties();
        properties.setProperty("file.resource.loader.path", absoluteRootPath + "/WEB-INF/views/");
        try {
            Velocity.init(properties);
        } catch (Exception e) {
            throw new ServletException("init template engine config fail", e);
        }
    }

    private void scanRouter() throws ServletException {
        try {
            routes = new RouterScanner(container).scan("");
        } catch (Exception e) {
            throw new ServletException("init router fail", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dispatch(req, resp, HttpMethod.GET);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dispatch(req, resp, HttpMethod.POST);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dispatch(req, resp, HttpMethod.PUT);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dispatch(req, resp, HttpMethod.DELETE);
    }

    private void dispatch(HttpServletRequest req, HttpServletResponse resp, HttpMethod method) throws IOException, ServletException {
        String url = req.getRequestURI().substring(req.getContextPath().length());
        Route route = routes.get(new SimpleUrlAndVerb(method, url));

        resp.setContentType("text/html");

        if (route == null || route.getActionDescriptor() == null) {
            resp.sendError(404);
            return;
        }

        route.exec(req, resp, container);
    }

}
