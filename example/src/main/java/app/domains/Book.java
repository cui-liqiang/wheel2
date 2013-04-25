package app.domains;

public class Book {

    private int id;
    private String title;

    public Book() {

    }

    public Book(int id, String title) {

        this.id = id;
        this.title = title;
    }

    public static Book findBy(int id) {
        return new Book(id, "From Russia With Love");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
