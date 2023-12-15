package prisc.diagonallibrary.controller.request;

import jakarta.validation.constraints.*;
import prisc.diagonallibrary.annotation.ValidYear;

import java.time.Year;

public class BookPostRequestBody {

    private int currentYear = Year.now().getValue();

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


    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setYear(int year) {
        this.year = year;
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


    @Override
    public String toString() {
        return "BookPostRequest{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year +
                '}';
    }
}
