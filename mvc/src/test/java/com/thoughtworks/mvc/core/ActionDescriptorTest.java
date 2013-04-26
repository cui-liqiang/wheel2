package com.thoughtworks.mvc.core;

import com.thoughtworks.mvc.core.param.FormParamsCreator;
import com.thoughtworks.mvc.mime.MimeType;
import core.IocContainer;
import core.IocContainerBuilder;
import org.apache.velocity.Template;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import testpackage.app.controllers.TestController;
import testutil.MockHttpServletResponse;
import testutil.TemplateUtil;

import javax.servlet.http.HttpServletRequest;

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
                TestController.class.getMethod("action1"));
        controllersContainer = new IocContainerBuilder().withPackageName("testpackage.app.controllers").build();
    }

    @After
    public void TearDown() {
        TemplateRepository.clearInstance();
    }

    @Test
    public void should_exec_action_and_assign_fields_to_velocity_context() throws Exception {
        HttpServletRequest req = mock(HttpServletRequest.class);
        MockHttpServletResponse resp = new MockHttpServletResponse();

        Template template = TemplateUtil.getTemplateFromString("Hello $name");
        when(repo.getTemplate("test", "action1", "html")).thenReturn(template);

        actionDescriptor.exec(req, resp, MimeType.HTML, controllersContainer, FormParamsCreator.create(req));

        assertEquals("Hello liqiang", resp.getResponse());
    }
}
