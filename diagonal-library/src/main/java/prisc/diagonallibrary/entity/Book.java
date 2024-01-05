package prisc.diagonallibrary.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import prisc.diagonallibrary.annotation.ValidYear;

import java.util.Objects;
import java.util.UUID;

/**
 * Book Class
 * <p>
 * This class represents a Book entity in the application. It is annotated with JPA annotations
 * to indicate its mapping to a database table.
 */
@Entity
@Getter
@Setter
@ToString
@Builder
public class Book {

    /**
     * Automatically generated unique identifier for the book.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID bookId;

    /**
     * Title of the book. Should have between 1 and 50 characters.
     */
    @Size(min = 1, max = 50)
    @NotEmpty
    @NotBlank
    private String title;

    /**
     * Author of the book. Should have between 1 and 50 characters.
     */
    @Size(min = 1, max = 50)
    @NotEmpty
    @NotBlank
    private String author;

    /**
     * Publication year of the book, validated with the {@link ValidYear} annotation.
     */
    @Column(name = "publication_year")
    @ValidYear
    private int year;

    /**
     * Constructor that takes all parameters.
     *
     * @param bookId Unique identifier of the book.
     * @param title  Title of the book.
     * @param author Author of the book.
     * @param year   Publication year of the book.
     */
    public Book(UUID bookId, String title, String author, int year) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.year = year;
    }

    /**
     * Constructor that takes only the title, author, and publication year of the book.
     *
     * @param title  Title of the book.
     * @param author Author of the book.
     * @param year   Publication year of the book.
     */
    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    /**
     * Default constructor with no parameters.
     */
    public Book() {
    }

    /**
     * Overrides the equals method to compare two books.
     *
     * @param o Object to be compared.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return year == book.year && Objects.equals(title, book.title) && Objects.equals(author, book.author);
    }

    /**
     * Overrides the hashCode method to generate a hash code for the book.
     *
     * @return Hash code of the book.
     */
    @Override
    public int hashCode() {
        return Objects.hash(title, author, year);
    }

}

