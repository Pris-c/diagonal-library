package prisc.diagonallibrary.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import prisc.diagonallibrary.controller.request.BookPostRequestBody;
import prisc.diagonallibrary.controller.request.BookPutRequestBody;
import prisc.diagonallibrary.controller.response.BookResponse;
import prisc.diagonallibrary.entity.Book;

import java.util.List;

/**
 * Mapper interface for converting between different representations of book entities.
 */
@Mapper(componentModel = "spring")
public abstract class BookMapper {

    /**
     * Singleton instance of the BookMapper.
     */
    public static final BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    /**
     * Converts a BookPostRequestBody to a Book entity.
     *
     * @param bookPostRequestBody Request body containing book information.
     * @return Book entity.
     */
    public abstract Book toBook(BookPostRequestBody bookPostRequestBody);

    /**
     * Converts a BookPutRequestBody to a Book entity.
     *
     * @param bookPutRequestBody Request body containing updated book information.
     * @return Book entity.
     */
    public abstract Book toBook(BookPutRequestBody bookPutRequestBody);

    /**
     * Converts a BookResponse to a Book entity.
     *
     * @param bookResponse Response containing book information.
     * @return Book entity.
     */
    public abstract Book toBook(BookResponse bookResponse);

    /**
     * Converts a Book entity to a BookResponse.
     *
     * @param book Book entity.
     * @return Response containing book information.
     */
    public abstract BookResponse toBookResponse(Book book);

    /**
     * Converts a list of Book entities to a list of BookResponses.
     *
     * @param bookList List of Book entities.
     * @return List of responses containing book information.
     */
    public abstract List<BookResponse> toBookResponseList(List<Book> bookList);

}
