package app.controllers;

import app.domains.Book;
import com.thoughtworks.mvc.annotation.Param;
import com.thoughtworks.mvc.annotation.Resource;
import com.thoughtworks.mvc.core.BaseController;

@Resource
public class BooksController extends BaseController {

    public void index() {
    }

    public void add() {
    }

    public void create() {
    }

    public Book show(@Param("id") int id) {
        return Book.findBy(id);
    }

    public void edit() {
    }

    public void update() {
    }

    public void destroy() {
    }

}
