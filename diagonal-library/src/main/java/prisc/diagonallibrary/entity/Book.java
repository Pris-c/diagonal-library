package prisc.diagonallibrary.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import prisc.diagonallibrary.annotation.ValidYear;

@Getter
@Setter
@Entity
@ToString
public class Book {

    @Min(0)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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


    public Book(Long bookId, String title, String author, int year) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public Book() {
    }

}

