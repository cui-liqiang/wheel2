package app.controllers;

import com.thoughtworks.mvc.annotation.Param;
import com.thoughtworks.mvc.annotation.Path;
import com.thoughtworks.mvc.core.BaseController;

@Path("/users")
public class UsersController extends BaseController {

    public String id;

    @Path("/:id")
    public void show(@Param("id") String id) {
        this.id = id;
    }

}
