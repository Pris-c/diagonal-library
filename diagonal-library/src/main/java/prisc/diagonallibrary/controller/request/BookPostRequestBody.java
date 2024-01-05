package prisc.diagonallibrary.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import prisc.diagonallibrary.annotation.ValidYear;

/**
 * Represents the request body for creating a new book in the diagonal library.
 * This class is used to handle incoming requests to create a new book.
 * As the bookId is created dynamically, this class doesn't need this attribute
 */
@Getter
@Setter
@ToString
@Builder
public class BookPostRequestBody {

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
     * Year of publication of the book, validated with the {@link ValidYear} annotation.
     */
    @ValidYear
    private int year;

}
