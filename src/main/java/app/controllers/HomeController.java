package app.controllers;

import com.thoughtworks.mvc.annotation.Path;
import core.annotation.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Path("/home")
@Component
public class HomeController {
    private String name;

    @Path("/")
    public void index(HttpServletRequest req, HttpServletResponse resp){
        name = "cui li qiang";
    }
}
