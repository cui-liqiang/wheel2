package app.controllers;

import app.domains.Book;
import app.services.BookService;
import com.thoughtworks.mvc.annotation.Param;
import com.thoughtworks.mvc.annotation.Resource;
import com.thoughtworks.mvc.annotation.Respond;
import com.thoughtworks.mvc.core.ControllerContext;
import com.thoughtworks.mvc.mime.MimeType;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

@Resource
@Respond({MimeType.HTML})
public class BooksController {

    @Inject
    private BookService service;

    private ControllerContext context;

    public void setService(BookService service) {
        this.service = service;
    }

    public void setContext(ControllerContext context) {
        this.context = context;
    }

    public List<Book> index() {
        return service.all();
    }

    public void add() {
    }

    public void create(@Param("book") Book book) {
        service.save(book);
        context.redirect(pathTo(book));
    }

    @Respond({MimeType.JSON})
    public Book show(@Param("id") int id) throws IOException {
        Book book = service.findBy(id);
        if (book == null) {
            context.render(404);
        }
        return book;
    }

    public Book edit(@Param("id") int id) {
        return service.findBy(id);
    }

    public void update(@Param("book") Book book) {
        service.save(book);
        context.redirect(pathTo(book));
    }

    public void destroy(@Param("id") int id) {
        service.deleteBy(id);
        context.string("book is deleted!");
    }

    private String pathTo(Book book) {
        return String.format("/books/%s", book.getId());
    }

}
