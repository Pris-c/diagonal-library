package prisc.diagonallibrary.mapper;

import prisc.diagonallibrary.controller.request.BookPostRequestBody;
import prisc.diagonallibrary.controller.request.BookPutRequestBody;
import prisc.diagonallibrary.controller.response.BookResponse;
import prisc.diagonallibrary.entity.Book;

import java.util.List;

public class BookMapper {

    public static Book toBook(BookPostRequestBody bookPostRequestBody){
        return new Book(bookPostRequestBody.getTitle(), bookPostRequestBody.getAuthor(), bookPostRequestBody.getYear());
    }

    public static Book toBook(BookPutRequestBody bookPutRequestBody) {
        return new Book(bookPutRequestBody.getBookId(), bookPutRequestBody.getTitle(), bookPutRequestBody.getAuthor(), bookPutRequestBody.getYear());
    }
    public static Book toBook(BookResponse bookResponse){
        return new Book(bookResponse.getBookId(), bookResponse.getTitle(), bookResponse.getAuthor(), bookResponse.getYear());
    }


    public static BookResponse toBookResponse(Book book){
        return new BookResponse(book.getBookId(), book.getTitle(), book.getAuthor(), book.getYear());
    }

    public static List<BookResponse> toBookResponseList(List<Book> bookList){
        return bookList.stream().map(BookMapper::toBookResponse).toList();
    }


}