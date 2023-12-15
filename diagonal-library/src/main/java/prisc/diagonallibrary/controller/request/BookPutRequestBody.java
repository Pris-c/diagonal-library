package prisc.diagonallibrary.controller.request;

import jakarta.validation.constraints.*;
import prisc.diagonallibrary.annotation.ValidYear;

import java.time.Year;

public class BookPutRequestBody {

    private int currentYear = Year.now().getValue();


    @NotNull
    @Min(0)
    private Long bookId;

    @Size(min=1, max=50)
    @NotEmpty
    @NotBlank
    private String title;

    @Size(min=1, max=50)
    @NotEmpty
    @NotBlank
    private String author;

    @ValidYear
    private int year;


    public int getCurrentYear() {
        return currentYear;
    }

    public void setCurrentYear(int currentYear) {
        this.currentYear = currentYear;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "BookPutRequestBody{" +
                "currentYear=" + currentYear +
                ", bookId=" + bookId +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year +
                '}';
    }
}
