package prisc.diagonallibrary.util;

import prisc.diagonallibrary.controller.request.BookPostRequestBody;
import prisc.diagonallibrary.controller.request.BookPutRequestBody;
import prisc.diagonallibrary.controller.response.BookResponse;
import prisc.diagonallibrary.entity.Book;

import java.util.UUID;


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

    public static BookPostRequestBody createBookPostRequesBody() {
        return BookPostRequestBody.builder()
                .title("Book")
                .author("Author")
                .year(2021)
                .build();
    }

    public static BookPutRequestBody createBookPutRequestBody() {
        return BookPutRequestBody.builder()
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
