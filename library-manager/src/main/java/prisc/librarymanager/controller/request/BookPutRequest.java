package prisc.librarymanager.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import prisc.librarymanager.annotation.ValidYear;

import java.util.UUID;

/**
 * Represents the request body for updating an existing book in the diagonal library.
 * This class is used to handle incoming requests to update an existing book.
 */
@Getter
@Setter
@ToString
@Builder
public class BookPutRequest {

    /**
     * Unique identifier of the book to be updated.
     */
    @NotNull(message = "The id cannot be null.")
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
