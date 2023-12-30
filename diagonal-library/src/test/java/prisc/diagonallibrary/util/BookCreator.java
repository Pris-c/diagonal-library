package prisc.diagonallibrary.util;

import prisc.diagonallibrary.controller.request.BookPostRequestBody;
import prisc.diagonallibrary.controller.request.BookPutRequestBody;
import prisc.diagonallibrary.controller.response.BookResponse;
import prisc.diagonallibrary.entity.Book;
import java.util.List;



public class BookCreator {

    public static Book createBookToBeSaved(){
        return Book.builder()
                .title("Book")
                .author("Author")
                .year(2021)
                .build();
    }

    public static BookResponse createBookResponse(){
        return BookResponse.builder()
                .bookId(1L)
                .title("Book")
                .author("Author")
                .year(2021)
                .build();
    }
    public static BookPostRequestBody createBookPostRequesBody(){
        return BookPostRequestBody.builder()
                .title("Book")
                .author("Author")
                .year(2021)
                .build();
    }

    public static BookPutRequestBody createBookPutRequestBody(){
        return BookPutRequestBody.builder()
                .bookId(1L)
                .title("Book")
                .author("Author")
                .year(2021)
                .build();
    }


    public static Book createValidBook(){
        return Book.builder()
                .bookId(1L)
                .title("Book")
                .author("Author")
                .year(2021)
                .build();
    }

}
