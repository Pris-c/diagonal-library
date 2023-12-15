package prisc.diagonallibrary.controller.response;

public class BookResponse {

    private Long bookId;
    private String title;
    private String author;
    private int year;

    public BookResponse(Long bookId, String title, String author, int year) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public Long getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }
}
