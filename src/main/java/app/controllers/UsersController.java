package app.controllers;

import com.thoughtworks.mvc.annotation.Path;
import com.thoughtworks.mvc.core.BaseController;

@Path("/users")
public class UsersController extends BaseController {

    @Path("/:id")
    public void show() {

    }
}
