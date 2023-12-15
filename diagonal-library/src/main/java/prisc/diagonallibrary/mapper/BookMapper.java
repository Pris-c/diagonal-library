package prisc.diagonallibrary.mapper;

import prisc.diagonallibrary.controller.request.BookPostRequestBody;
import prisc.diagonallibrary.controller.response.BookResponse;
import prisc.diagonallibrary.entity.Book;

public class BookMapper {

    public static Book toBook(BookPostRequestBody bookPostRequestBody){
        return new Book(bookPostRequestBody.getTitle(), bookPostRequestBody.getAuthor(), bookPostRequestBody.getYear());
    }


    public static BookResponse toBookResponse(Book book){
        return new BookResponse(book.getBookId(), book.getTitle(), book.getAuthor(), book.getYear());
    }


}
