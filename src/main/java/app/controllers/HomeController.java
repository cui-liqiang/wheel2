package app.controllers;

import com.thoughtworks.mvc.annotation.Path;
import com.thoughtworks.mvc.core.BaseController;
import com.thoughtworks.mvc.verb.HttpMethod;
import core.annotation.Component;
import core.scopes.Prototype;

import java.io.IOException;

@Path("/home")
@Component
@Prototype
public class HomeController extends BaseController{
    private String name;

    @Path("/")
    public void index(){
    }

    @Path("/success")
    public void success(){
    }

    @Path(value = "/", httpMethod = HttpMethod.POST)
    public void handleSubmit() throws IOException {
        redirect("/home/success");
    }
}
