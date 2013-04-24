package app.controllers;

import com.thoughtworks.mvc.annotation.Path;
import com.thoughtworks.mvc.core.BaseController;

@Path("/users")
public class UsersController extends BaseController {

    @Path("/:id")
    // TODO: support parameters in request url.
    public void show(int id) {

    }
}
