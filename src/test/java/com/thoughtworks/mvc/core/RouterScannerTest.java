package com.thoughtworks.mvc.core;

import com.thoughtworks.mvc.verb.HttpMethod;
import core.IocContainer;
import core.IocContainerBuilder;
import org.apache.velocity.Template;
import org.junit.Before;
import org.junit.Test;
import testpackage.app.controllers.TestController;
import testutil.TemplateUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RouterScannerTest {
    RouterScanner scanner;
    TemplateRepository repo;
    Template template;
    Map<UrlAndVerb, ActionDescriptor> mapping;

    @Before
    public void setup() throws Exception {
        IocContainer iocContainer = new IocContainerBuilder().build();
        repo = mock(TemplateRepository.class);
        scanner = new RouterScanner(repo, iocContainer);
        template = TemplateUtil.getTemplateFromString("whatever");
        when(repo.getTemplate("test", "action1")).thenReturn(template);
        mapping = scanner.scan("testpackage");
    }

    @Test
    public void should_join_url_in_class_and_url_in_action() throws Exception {
        Method action = TestController.class.getMethod("action1");
        ActionDescriptor expectedDescriptor = new ActionDescriptor(TestController.class, action);
        assertEquals(expectedDescriptor, mapping.get(new UrlAndVerb(HttpMethod.GET, "/test/action1")));
    }

    @Test
    public void should_mapping_action_with_according_to_http_method() throws NoSuchMethodException {
        Method action = TestController.class.getMethod("action3");
        ActionDescriptor expectedDescriptor = new ActionDescriptor(TestController.class, action);
        assertEquals(expectedDescriptor, mapping.get(new UrlAndVerb(HttpMethod.POST, "/test/action1")));
    }
}
