package prisc.diagonallibrary.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

/**
 * Represents the response data for a book in the diagonal library.
 * This class is used to format and send information about a book as a response.
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
public class BookResponse {

    /**
     * Book attributes
     */
    private UUID bookId;
    private String title;
    private String author;
    private int year;


    /**
     * Overrides the equals method to compare two bookResponses.
     *
     * @param o Object to be compared.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookResponse that = (BookResponse) o;
        return year == that.year && Objects.equals(bookId, that.bookId) && Objects.equals(title, that.title) && Objects.equals(author, that.author);
    }

    /**
     * Overrides the hashCode method to generate a hash code for the bookResponse.
     *
     * @return Hash code of the book.
     */
    @Override
    public int hashCode() {
        return Objects.hash(bookId, title, author, year);
    }
}
