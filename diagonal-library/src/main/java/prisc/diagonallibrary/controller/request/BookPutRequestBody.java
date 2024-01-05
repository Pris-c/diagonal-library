package prisc.diagonallibrary.controller.request;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import prisc.diagonallibrary.annotation.ValidYear;

import java.util.UUID;

/**
 * Represents the request body for updating an existing book in the diagonal library.
 * This class is used to handle incoming requests to update an existing book.
 */
@Getter
@Setter
@ToString
@Builder
public class BookPutRequestBody {

    /**
     * Unique identifier of the book to be updated.
     */
    private UUID bookId;

    /**
     * Title of the book. Should have between 1 and 50 characters.
     */
    @Size(min=1, max=50)
    @NotEmpty
    @NotBlank
    private String title;

    /**
     * Author of the book. Should have between 1 and 50 characters.
     */
    @Size(min=1, max=50)
    @NotEmpty
    @NotBlank
    private String author;

    /**
     * Year of publication of the book, validated with the {@link ValidYear} annotation.
     */
    @ValidYear
    private int year;

}
