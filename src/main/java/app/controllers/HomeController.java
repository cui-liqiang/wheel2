package app.controllers;

import com.thoughtworks.mvc.annotation.Path;
import core.annotation.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Path("/home")
@Component
public class HomeController {
    @Path("/")
    public void index(HttpServletRequest req, HttpServletResponse resp){
        req.setAttribute("name", "cui li qiang");
    }
}
