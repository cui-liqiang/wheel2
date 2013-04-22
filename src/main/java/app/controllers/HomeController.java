package app.controllers;

import app.domains.Person;
import com.thoughtworks.mvc.annotation.Path;
import com.thoughtworks.mvc.core.BaseController;
import com.thoughtworks.mvc.verb.HttpMethod;
import core.annotation.Component;
import core.scopes.Prototype;

import java.util.List;
import java.util.Map;

@Path("/home")
@Component
@Prototype
public class HomeController extends BaseController{
    private String name;
    private Person person;

    @Path("/")
    public void index(){
    }

    @Path("/success")
    public void success(){
    }

    @Path(value = "/", httpMethod = HttpMethod.POST)
    public void handleSubmit() throws Exception {
        person = toObject(Person.class, (Map)params.get("person"));
        List<Person> people = toList(Person.class, (List)params.get("people"));

        render("success");
    }
}
