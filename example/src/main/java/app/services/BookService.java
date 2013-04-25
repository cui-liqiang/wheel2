package app.services;

import app.domains.Book;
import core.annotation.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class BookService {

    private static HashMap<Integer, Book> books = new HashMap<Integer, Book>();

    static {
        books.put(1, new Book(1, "From Russia With Love"));
        books.put(2, new Book(2, "Casino Royale"));
        books.put(3, new Book(3, "You Only Live Twice"));
        books.put(4, new Book(4, "Licence to kill"));
        books.put(5, new Book(5, "Goldfinger"));
    }

    public Book findBy(int id) {
        return books.get(id);
    }

    public List<Book> all() {
        return new ArrayList(books.values());
    }

    public void save(Book book) {
        int newId = books.size() + 1;
        book.setId(newId);
        books.put(newId, book);
    }
}
