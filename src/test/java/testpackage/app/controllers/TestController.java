package testpackage.app.controllers;

import com.thoughtworks.mvc.annotation.Path;
import com.thoughtworks.mvc.verb.HttpMethod;
import core.annotation.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Path("/test")
public class TestController {

    String name;

    @Path("/action1")
    public void action1(HttpServletRequest req, HttpServletResponse resp) {
        name = "liqiang";
    }

    @Path("/action2/other/url")
    public void action2(HttpServletRequest req, HttpServletResponse resp) {
    }

    @Path(value = "/action1", httpMethod = HttpMethod.POST)
    public void action3(HttpServletRequest req, HttpServletResponse resp) {
    }
}
