package com.thoughtworks.mvc.core;

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

    @Before
    public void setup() {
        repo = mock(TemplateRepository.class);
        scanner = new RouterScanner(repo);
    }

    @Test
    public void should_first_part_in_url_as_controller_and_second_as_action() throws Exception {
        Template template = TemplateUtil.getTemplateFromString("whatever");
        when(repo.getTemplate("test", "action1")).thenReturn(template);
        Map<String,ActionDescriptor> mapping = scanner.scan("testpackage");

        Method action = TestController.class.getMethod("action1", HttpServletRequest.class, HttpServletResponse.class);
        ActionDescriptor expectedDescriptor = new ActionDescriptor(TestController.class, action);
        assertEquals(expectedDescriptor, mapping.get("/test/action1"));
    }
}
