package app.controllers;

import app.domains.Person;
import com.thoughtworks.mvc.annotation.Param;
import com.thoughtworks.mvc.annotation.Path;

@Path("/users")
public class UsersController {

    public int id;

    @Path("/:id")
    public Person show(@Param("id") int id) {
        this.id = id;
        Person person = new Person();
        person.setName("wheel");
        person.setAge(2);
        return person;
    }

}
