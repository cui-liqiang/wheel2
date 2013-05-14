package app.controllers;

import app.domains.Person;
import com.thoughtworks.mvc.annotation.Param;
import com.thoughtworks.mvc.annotation.Path;
import com.thoughtworks.mvc.core.ControllerContext;
import com.thoughtworks.mvc.verb.HttpMethod;

import java.util.List;

@Path("/home")
public class HomeController {

    private String name;
    private Person person;

    private ControllerContext context;

    public void setContext(ControllerContext context) {
        this.context = context;
    }

    @Path("/")
    public void index() {
    }

    @Path("/success")
    public void success() {
    }

    @Path(value = "/", httpMethod = HttpMethod.POST)
    public void handleSubmit(@Param("people") List<Person> people) throws Exception {
//        Map locals = new HashMap();
//        locals.put("people", people);
//
//        render("success", locals);
    }

    @Path(value = "/handle-and-redirect", httpMethod = HttpMethod.POST)
    public void handleAndRedirect() throws Exception {
        context.redirect("/home/");
    }
}
