package prisc.diagonallibrary.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import prisc.diagonallibrary.annotation.ValidYear;

import java.util.Objects;
import java.util.UUID;


@Entity
@Getter
@Setter
@ToString
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID bookId;


    @Size(min=1, max=50)
    @NotEmpty
    @NotBlank
    private String title;

    @Size(min=1, max=50)
    @NotEmpty
    @NotBlank
    private String author;

    @Column(name = "publication_year")
    @ValidYear
    private int year;

    public Book(UUID bookId, String title, String author, int year) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return year == book.year && Objects.equals(title, book.title) && Objects.equals(author, book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, year);
    }

}

