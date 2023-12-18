package prisc.diagonallibrary.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import prisc.diagonallibrary.controller.request.BookPostRequestBody;
import prisc.diagonallibrary.controller.request.BookPutRequestBody;
import prisc.diagonallibrary.controller.response.BookResponse;
import prisc.diagonallibrary.entity.Book;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class BookMapper {

    public static final BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    //@Mapping(target = "bookId", ignore = true)
    public abstract Book toBook(BookPostRequestBody bookPostRequestBody);

    public abstract Book toBook(BookPutRequestBody bookPutRequestBody);
    public abstract Book toBook(BookResponse bookResponse);

    public abstract BookResponse toBookResponse(Book book);

    public abstract List<BookResponse> toBookResponseList(List<Book> bookList);


}
