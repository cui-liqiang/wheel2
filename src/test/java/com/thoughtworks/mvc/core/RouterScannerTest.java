package com.thoughtworks.mvc.core;

import com.thoughtworks.mvc.core.route.Route;
import com.thoughtworks.mvc.core.route.Routes;
import com.thoughtworks.mvc.core.urlAndVerb.SimpleUrlAndVerb;
import com.thoughtworks.mvc.verb.HttpMethod;
import core.IocContainer;
import core.IocContainerBuilder;
import org.apache.velocity.Template;
import org.junit.Before;
import org.junit.Test;
import testpackage.app.controllers.TestController;
import testutil.TemplateUtil;

import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RouterScannerTest {
    RouterScanner scanner;
    TemplateRepository repo;
    Template template;
    Routes routes;

    @Before
    public void setup() throws Exception {
        IocContainer iocContainer = new IocContainerBuilder().build();
        repo = mock(TemplateRepository.class);
        scanner = new RouterScanner(repo, iocContainer);
        template = TemplateUtil.getTemplateFromString("whatever");
        when(repo.getTemplate("test", "action1")).thenReturn(template);
        routes = scanner.scan("testpackage");
    }

    @Test
    public void should_join_url_in_class_and_url_in_action() throws Exception {
        Method action = TestController.class.getMethod("action1");
        ActionDescriptor expectedDescriptor = new ActionDescriptor(TestController.class, action);
        Route route = routes.get(new SimpleUrlAndVerb(HttpMethod.GET, "/test/action1"));
        assertEquals(expectedDescriptor, route.getActionDescriptor());
    }

    @Test
    public void should_mapping_action_with_according_to_http_method() throws NoSuchMethodException {
        Method action = TestController.class.getMethod("action3");
        ActionDescriptor expectedDescriptor = new ActionDescriptor(TestController.class, action);
        Route route = routes.get(new SimpleUrlAndVerb(HttpMethod.POST, "/test/action1"));
        assertEquals(expectedDescriptor, route.getActionDescriptor());
    }
}
