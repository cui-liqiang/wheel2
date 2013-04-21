package com.thoughtworks.mvc.core;

import core.IocContainer;
import core.IocContainerBuilder;
import org.apache.velocity.Template;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import testpackage.app.controllers.TestController;
import testutil.MockHttpServletRequest;
import testutil.MockHttpServletResponse;
import testutil.TemplateUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ActionDescriptorTest {
    ActionDescriptor actionDescriptor;
    IocContainer controllersContainer;
    TemplateRepository repo;

    @Before
    public void setup() throws Exception {
        repo = mock(TemplateRepository.class);
        TemplateRepository.setInstance(repo);

        actionDescriptor = new ActionDescriptor(TestController.class,
                                                TestController.class.getMethod("action1", HttpServletRequest.class, HttpServletResponse.class));
        controllersContainer = new IocContainerBuilder().withPackageName("testpackage.app.controllers").build();
    }

    @After
    public void TearDown() {
        TemplateRepository.clearInstance();
    }

    @Test
    public void should_exec_action_and_assign_fields_to_velocity_context() throws Exception {
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse resp = new MockHttpServletResponse();

        Template template = TemplateUtil.getTemplateFromString("Hello $name");
        when(repo.getTemplate("test", "action1")).thenReturn(template);

        actionDescriptor.exec(req, resp, controllersContainer);

        assertEquals("Hello liqiang", resp.getResponse());
    }
}
