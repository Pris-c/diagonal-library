package prisc.diagonallibrary.util;

import jakarta.validation.Valid;
import prisc.diagonallibrary.controller.request.BookPostRequest;
import prisc.diagonallibrary.controller.request.BookPutRequest;
import prisc.diagonallibrary.controller.response.BookResponse;
import prisc.diagonallibrary.model.Book;

import java.util.UUID;

/**
 * Utility class for creating instances of Book, BookRequest, and BookResponse for testing purposes.
 */
public class BookCreator {

    public static Book createBookToBeSaved() {
        return Book.builder()
                .title("Book")
                .author("Author")
                .year(2021)
                .build();
    }

    public static BookResponse createBookResponse() {
        return BookResponse.builder()
                .bookId(UUID.fromString("a7669e4c-4420-43c8-9b90-81e149d37d95"))
                .title("Book")
                .author("Author")
                .year(2021)
                .build();
    }

    public static BookPostRequest createBookRequestToSave() {
        return BookPostRequest.builder()
                .title("Book")
                .author("Author")
                .year(2021)
                .build();
    }

    public static BookPutRequest createBookRequestToUpdate() {
        return BookPutRequest.builder()
                .bookId(UUID.fromString("a7669e4c-4420-43c8-9b90-81e149d37d95"))
                .title("Book")
                .author("Author")
                .year(2021)
                .build();
    }

    public static Book createValidBook() {
        return Book.builder()
                .bookId(UUID.fromString("a7669e4c-4420-43c8-9b90-81e149d37d95"))
                .title("Book")
                .author("Author")
                .year(2021)
                .build();
    }
}