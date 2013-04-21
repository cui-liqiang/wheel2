package com.thoughtworks.mvc.core;

import core.IocContainer;
import core.IocContainerBuilder;
import org.apache.velocity.Template;
import org.junit.Before;
import org.junit.Test;
import testpackage.app.controllers.TestController;
import testutil.MockHttpServletRequest;
import testutil.MockHttpServletResponse;
import testutil.TemplateUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.assertEquals;

public class ActionDescriptorTest {
    ActionDescriptor actionDescriptor;
    IocContainer controllersContainer;

    @Before
    public void setup() throws Exception {
        Template template = TemplateUtil.getTemplateFromString("Hello $name");

        actionDescriptor = new ActionDescriptor(TestController.class,
                                                TestController.class.getMethod("action1", HttpServletRequest.class, HttpServletResponse.class), template);
        controllersContainer = new IocContainerBuilder().withPackageName("testpackage.app.controllers").build();
    }

    @Test
    public void should_exec_action_and_assign_fields_to_velocity_context() throws Exception {
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse resp = new MockHttpServletResponse();

        actionDescriptor.exec(req, resp, controllersContainer);

        assertEquals("Hello liqiang", resp.getResponse());
    }
}
