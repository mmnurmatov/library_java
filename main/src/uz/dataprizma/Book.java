package uz.dataprizma;
import java.util.UUID;

public class Book {

    private UUID id;
    private String title;
    private String year;
    private String author;
    private BookStatus bookStatus;

    public Book(){ }

    public Book(String title, String year, String author, BookStatus bookStatus) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.year = year;
        this.author = author;
        this.bookStatus = bookStatus;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public BookStatus getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(BookStatus bookStatus) {
        this.bookStatus = bookStatus;
    }
}
