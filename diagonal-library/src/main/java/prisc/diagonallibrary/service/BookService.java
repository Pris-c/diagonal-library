package prisc.diagonallibrary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prisc.diagonallibrary.controller.request.BookRequest;
import prisc.diagonallibrary.controller.response.BookResponse;
import prisc.diagonallibrary.entity.Book;
import prisc.diagonallibrary.exception.BookAlreadyExistsException;
import prisc.diagonallibrary.exception.BookIdNotFoundException;
import prisc.diagonallibrary.mapper.BookMapper;
import prisc.diagonallibrary.repository.BookRepository;

import java.util.List;
import java.util.UUID;


/**
 * Service class for managing book-related operations in the diagonal library.
 */
@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;


    /**
     * Retrieves a list of all books.
     *
     * @return List of book responses representing all books.
     */
    public List<BookResponse> getAll() {
        return BookMapper.INSTANCE.toBookResponseList(bookRepository.findAll());
    }

    /**
     * Saves a new book in the database.
     *
     * @param bookRequest Request body containing book information.
     * @return Book response representing the saved book.
     * @throws BookAlreadyExistsException If a book with the same attributes already exists in the database.
     */
    @Transactional
    public BookResponse save(BookRequest bookRequest) throws BookAlreadyExistsException {
        Book bookToSave = BookMapper.INSTANCE.toBook(bookRequest);
        if (bookRepository.existsByAttributesIgnoreCase(
                bookToSave.getTitle(),
                bookToSave.getAuthor(),
                bookToSave.getYear())) {
            throw new BookAlreadyExistsException("The book: "
                    + bookRequest.getTitle() + ", "
                    + bookRequest.getAuthor() + ", "
                    + bookRequest.getYear() +
                    " is already in database");
        }

        return BookMapper.INSTANCE.toBookResponse(bookRepository.save(bookToSave));
    }

    /**
     * Retrieves a book by its unique identifier or throws an exception if not found.
     *
     * @param id Unique identifier of the book.
     * @return Book response representing the found book.
     * @throws BookIdNotFoundException If a book with the specified identifier is not found.
     */
    public BookResponse findById_OrThrowBookIdNotFoundException(UUID id) {
        return BookMapper.INSTANCE.toBookResponse(
                bookRepository.findById(id)
                        .orElseThrow(() -> new BookIdNotFoundException("Id " + id + " not found."))
        );
    }

    /**
     * Retrieves a list of books whose titles contain the specified substring, ignoring case.
     *
     * @param title Substring to search for in book titles.
     * @return List of book responses matching the criteria.
     */
    public List<BookResponse> findByTitle(String title) {
        return BookMapper.INSTANCE.toBookResponseList(bookRepository.findByTitleIgnoreCaseContaining(title));
    }

    /**
     * Retrieves a list of books whose authors' names contain the specified substring, ignoring case.
     *
     * @param author Substring to search for in book authors' names.
     * @return List of book responses matching the criteria.
     */
    public List<BookResponse> findByAuthor(String author) {
        return BookMapper.INSTANCE.toBookResponseList(bookRepository.findByAuthorIgnoreCaseContaining(author));
    }

    /**
     * Retrieves a list of books published in the specified year.
     *
     * @param year Year of publication.
     * @return List of book responses matching the criteria.
     */
    public List<BookResponse> findByYear(int year) {
        return BookMapper.INSTANCE.toBookResponseList(bookRepository.findByYear(year));
    }

    /**
     * Updates an existing book in the database.
     *
     * @param bookRequest Request body containing updated book information.
     * @return BookResponse representing the updated book.
     * @throws BookAlreadyExistsException If a book with the same attributes already exists in the database.
     */
    @Transactional
    public BookResponse update(BookRequest bookRequest) {
        //Verify if the id exists or throw exception in method findById
        Book savedBook = BookMapper.INSTANCE
                .toBook(findById_OrThrowBookIdNotFoundException(bookRequest.getBookId()));

        //Check if there is an identical book in the database
        if (bookRepository.existsByAttributesIgnoreCase(
                bookRequest.getTitle(),
                bookRequest.getAuthor(),
                bookRequest.getYear())) {
            throw new BookAlreadyExistsException("The book: "
                    + bookRequest.getTitle() + ", "
                    + bookRequest.getAuthor() + ", "
                    + bookRequest.getYear() +
                    " is already in database");
        }

        savedBook.setYear(bookRequest.getYear());
        savedBook.setAuthor(bookRequest.getAuthor());
        savedBook.setTitle(bookRequest.getTitle());
        Book updatedBook = bookRepository.save(savedBook);

        return BookMapper.INSTANCE.toBookResponse(updatedBook);
    }

    /**
     * Deletes a book from the database by its unique identifier.
     *
     * @param id Unique identifier of the book to be deleted.
     */
    @Transactional
    public void deleteById(UUID id) {
        BookMapper.INSTANCE.toBook(this.findById_OrThrowBookIdNotFoundException(id));
        bookRepository.deleteById(id);
    }

}
