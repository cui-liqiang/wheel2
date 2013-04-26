package app.controllers;

import app.domains.Person;
import com.thoughtworks.mvc.annotation.Param;
import com.thoughtworks.mvc.annotation.Path;
import com.thoughtworks.mvc.core.BaseController;
import com.thoughtworks.mvc.verb.HttpMethod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/home")
public class HomeController extends BaseController {
    private String name;
    private Person person;

    @Path("/")
    public void index() {
    }

    @Path("/success")
    public void success() {
    }

    @Path(value = "/", httpMethod = HttpMethod.POST)
    public void handleSubmit(@Param("people") List<Person> people) throws Exception {
        Map locals = new HashMap();
        locals.put("people", people);

        render("success", locals);
    }

    @Path(value = "/handle-and-redirect", httpMethod = HttpMethod.POST)
    public void handleAndRedirect() throws Exception {
        redirect("/home/");
    }
}
