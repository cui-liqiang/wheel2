package app.controllers;

import app.domains.Book;
import app.services.BookService;
import com.thoughtworks.mvc.annotation.Param;
import com.thoughtworks.mvc.annotation.Resource;
import com.thoughtworks.mvc.annotation.Respond;
import com.thoughtworks.mvc.core.BaseController;
import com.thoughtworks.mvc.mime.MimeType;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;


@Resource
@Respond({MimeType.HTML})
public class BooksController extends BaseController {

    @Inject
    private BookService service;

    public void setService(BookService service) {
        this.service = service;
    }

    public List<Book> index() {
        return service.all();
    }

    public void add() {
    }

    public void create(@Param("book") Book book) {
        service.save(book);
        redirect(pathTo(book));
    }

    @Respond({MimeType.JSON})
    public Book show(@Param("id") int id) throws IOException {
        Book book = service.findBy(id);
        if (book == null) {
            render(NOT_FOUND);
        }
        return book;
    }

    public Book edit(@Param("id") int id) {
        return service.findBy(id);
    }

    public void update(@Param("book") Book book) {
        service.save(book);
        redirect(pathTo(book));
    }

    public void destroy(@Param("id") int id) {
        service.deleteBy(id);
        string("book is deleted!");
    }

    private String pathTo(Book book) {
        return String.format("/books/%s", book.getId());
    }

}
