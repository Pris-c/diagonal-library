package prisc.diagonallibrary.controller.request;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;
import prisc.diagonallibrary.annotation.ValidYear;
import prisc.diagonallibrary.validations.groups.PostValidation;
import prisc.diagonallibrary.validations.groups.PutValidation;

import java.util.UUID;

/**
 * Represents the request body for updating an existing book in the diagonal library.
 * This class is used to handle incoming requests to update an existing book.
 */
@Getter
@Setter
@ToString
@Builder
public class BookRequest {

    /**
     * Unique identifier of the book to be updated.
     */
    @NotNull(groups = PutValidation.class, message = "The id cannot be null.")
    @Null(groups = PostValidation.class, message = "You cannot choose the id value.")
    private UUID bookId;

    /**
     * Title of the book. Should have between 1 and 50 characters.
     */
    @Size(min=1, max=50, message = "The title must have between 1 and 50 characters.")
    @NotEmpty(message = "Title cannot be empty.")
    @NotBlank(message = "Title cannot be blank or null.")
    private String title;

    /**
     * Author of the book. Should have between 1 and 50 characters.
     */
    @Size(min=1, max=50, message = "The author's name must have between 1 and 50 characters.")
    @NotEmpty(message = "Author's name cannot be empty.")
    @NotBlank(message = "Author's name cannot be blank or null.")
    private String author;

    /**
     * Year of publication of the book, validated with the {@link ValidYear} annotation.
     */
    @ValidYear
    private int year;

}
