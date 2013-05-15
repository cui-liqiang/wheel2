package app.controllers;

import app.domains.Person;
import com.thoughtworks.mvc.annotation.Param;
import com.thoughtworks.mvc.annotation.Resource;
import com.thoughtworks.mvc.annotation.Respond;
import com.thoughtworks.mvc.core.ControllerContext;
import com.thoughtworks.mvc.mime.MimeType;
import com.thoughtworks.orm.core.IDB;

import javax.inject.Inject;

@Resource
@Respond({MimeType.HTML})
public class PeopleController {
    private ControllerContext context;

    @Inject
    IDB db;

    public void create(@Param("person") Person person) throws Exception {
        db.save(person);
        context.redirect(pathTo(person));
    }

    public void add() {
    }

    @Respond({MimeType.JSON})
    public Person show(@Param("id") int id) throws Exception {
        Person person = db.find(Person.class, id);
        if (person == null) {
            context.render(404);
        }
        return person;
    }

    public void update(@Param("person") Person person) throws Exception {
        db.save(person);
        context.redirect(pathTo(person));
    }

    public void destroy(@Param("id") int id) throws Exception {
        db.delete(db.find(Person.class, id));
        context.string("book is deleted!");
    }

    private String pathTo(Person person) {
        return String.format("/persons/%s", person.getId());
    }
}
