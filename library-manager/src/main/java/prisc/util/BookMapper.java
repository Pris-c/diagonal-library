package prisc.util;

import prisc.book.Book;
import prisc.book.BookDTO;

import java.util.List;

/**
 * BookMapper Class
 *
 * The BookMapper class provides static methods for mapping between Book and BookDTO objects.
 * It encapsulates the conversion logic to create BookDTO objects from Book objects and vice versa.
 * Additionally, it supports the conversion of lists of Book objects to lists of BookDTO objects.
 *
 * Usage:
 * The BookMapper class is used to facilitate the transformation of Book and BookDTO objects within the application.
 * It is employed in scenarios where data needs to be transferred between different layers of the application,
 * such as converting database entities to DTOs for presentation or vice versa.
 */
public class BookMapper {

    /**
     * Creates a BookDTO from a Book.
     *
     * @param book The Book object to be converted.
     * @return A new BookDTO object representing the information of the provided Book.
     */
    public static BookDTO bookToDTO(Book book) {
        return new BookDTO(book.getBookId(), book.getTitle(), book.getAuthor(), book.getYear());
    }


    /**
     * Creates a Book from a BookDTO.
     *
     * @param bookDTO The BookDTO object to be converted.
     * @return A new Book object representing the information of the provided BookDTO.
     */
    public static Book dtoToBook(BookDTO bookDTO) {
        return new Book(bookDTO.getId(), bookDTO.getTitle(), bookDTO.getAuthor(), bookDTO.getYear());
    }


    /**
     * Creates a List of BookDTO from a List of Book.
     *
     * @param books The List of Book objects to be converted.
     * @return A new List containing BookDTO objects representing the information of the provided Book objects.
     */
    public static List<BookDTO> bookListToDTOList(List<Book> books) {
        return books.stream().map(BookMapper::bookToDTO).toList();
    }

}
