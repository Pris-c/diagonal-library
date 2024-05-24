package prisc.book;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import prisc.exceptions.BookAlreadyExistsException;
import prisc.exceptions.DatabaseOperationException;
import prisc.exceptions.InvalidIdException;
import prisc.exceptions.NoUpdatedInfoException;
import prisc.util.BookMapper;

import java.util.List;
import java.util.NoSuchElementException;


/**
 * BookService Class
 *
 * The BookService class acts as a service layer for managing Book entities in the application.
 * It orchestrates the business logic related to books and interacts with the BookRepository
 * to perform CRUD (Create, Read, Update, Delete) operations. This class is responsible for handling
 * operations such as retrieving all books, saving a new book, deleting a book by ID, and checking
 * if the library is empty.
 *
 * Usage:
 * The BookService class is utilized by other components of the application, such as controllers or
 * other services, to perform high-level operations on Book entities. It abstracts away the details of
 * database interactions, encapsulates business logic, and provides a clean and organized interface for
 * managing book-related functionality.
 */
public class BookService {

    private static final Logger logger = LogManager.getLogger(BookService.class);
    private final BookRepository bookRepository = new BookRepository();

    /**
     * Retrieves and returns a list of all books saved in the database.
     * This method calls the BookRepository to fetch the books and maps them to BookDTO objects.
     *
     * @return A list of BookDTO objects representing all books in the database.
     * @throws DatabaseOperationException If an error occurs while retrieving Book objects from the database.
     */
    public List<BookDTO> getAll() throws DatabaseOperationException {
        List<Book> books;

        try {
            books = bookRepository.getAll();
            return BookMapper.bookListToDTOList(books);

        } catch (Exception e){
            logger.error("Error while retrieving Book from database: " + e.getMessage(), e);
            throw new DatabaseOperationException("Error while retrieving Book objects from database" + e);
        }
    }


    /**
     * Saves a Book object to the database.
     * This operation involves converting a BookDTO to a Book object,
     * checking if a book with the same title, author, and year already exists,
     * and saving the Book to the database if it does not.
     *
     * @param dtoToBeSaved The BookDTO object to be saved.
     *                     It must contain necessary information such as title, author, and year.
     * @throws BookAlreadyExistsException If a book with the same title, author, and year already exists in the database.
     * @throws DatabaseOperationException If an error occurs while interacting with the database.
     */
    public void save(BookDTO dtoToBeSaved) throws DatabaseOperationException, BookAlreadyExistsException {
        Book bookToBeSaved = BookMapper.dtoToBook(dtoToBeSaved);
        Book existingBook;

        try {
             existingBook = bookRepository.findExistingBook(bookToBeSaved);

            if (existingBook == null) {
                bookRepository.save(bookToBeSaved);

            } else {
                throw new BookAlreadyExistsException(BookMapper.bookToDTO(existingBook));
            }

        } catch (BookAlreadyExistsException ex){
            logger.error("Error while saving Book: " + ex.getMessage(), ex);
            throw ex;

        } catch (Exception e){
            logger.error("Error while saving Book in database: " + e.getMessage(), e);
            throw new DatabaseOperationException("Error while saving Book in database" + e);
        }
    }


    /**
     * Retrieves and returns a list of books from the database containing the specified title.
     * This method uses BookRepository to find books by their titles and maps the results to BookDTO objects.
     *
     * @param title The title to search for in the books.
     * @return A list of BookDTO objects containing the specified title.
     * @throws DatabaseOperationException If an error occurs while searching for books in the database.
     */
    public List<BookDTO> findByTitle(String title) throws DatabaseOperationException {
        List<Book> books;

        try{
            books = bookRepository.findByTitle(title);

        } catch (Exception e){
            logger.error("Error while searching books in database: " + e.getMessage(), e);
            throw new DatabaseOperationException("Error while searching books in database:" + e);
        }
        return BookMapper.bookListToDTOList(books);
    }


    /**
     * Retrieves and returns a list of books from the database containing the specified author string.
     * This method uses BookRepository to find books by their authors and maps the results to BookDTO objects.
     *
     * @param author The author to search for in the books.
     * @return A list of BookDTO objects containing the specified author.
     * @throws DatabaseOperationException If an error occurs while searching for books in the database.
     */
    public List<BookDTO> findByAuthor(String author) throws DatabaseOperationException {
        List<Book> books;

        try{
            books = bookRepository.findByAuthor(author);

        } catch (Exception e){
            throw new DatabaseOperationException("Error while searching books in database");
        }
        return BookMapper.bookListToDTOList(books);
    }


    /**
     * Retrieves and returns a list of books from the database with the specified year.
     * This method uses BookRepository to find books by their publication year and maps the results to BookDTO objects.
     *
     * @param year The publication year to search for in the books.
     * @return A list of BookDTO objects containing the specified publication year.
     * @throws DatabaseOperationException If an error occurs while searching for books in the database.
     */
    public List<BookDTO> findByYear(int year) {
        List<Book> books;

        try{
            books = bookRepository.findByYear(year);

        } catch (Exception e){
            logger.error("Error while searching books in database: " + e.getMessage(), e);
            throw new DatabaseOperationException("Error while searching books in database:" + e);
        }
        return BookMapper.bookListToDTOList(books);
    }


    /**
     * Retrieves and returns a BookDTO representing the book with the specified ID.
     * This method uses BookRepository to find a book by its ID and maps the result to a BookDTO object.
     *
     * @param id The ID of the book to search for.
     * @return A BookDTO object representing the book with the specified ID.
     * @throws DatabaseOperationException If an error occurs while searching for the book in the database.
     */
    public BookDTO findById(int id) throws DatabaseOperationException {
        Book book;

        try {
            book = bookRepository.findById(id);

            if (book == null){
                return null;
            } else {
                return BookMapper.bookToDTO(book);
            }

        } catch (Exception e){
            logger.error("Error while searching books in database: " + e.getMessage(), e);
            throw new DatabaseOperationException("Error while searching books in database:" + e);
        }
    }


    /**
     * Updates the information of a book in the database based on the provided BookDTO.
     *
     * This method verifies the integrity of ID information, checks for new information to update,
     * ensures that the updated book does not already exist in the database, and then calls
     * BookRepository to perform the update operation.
     *
     * @param bookFromDatabase The BookDTO representing the book currently in the database.
     * @param dtoToBeUpdated The BookDTO containing the updated information to be applied to the database.
     * @throws InvalidIdException If there is an issue with the ID information, such as mismatched or invalid IDs.
     * @throws NoUpdatedInfoException If there is no new information to be updated.
     * @throws BookAlreadyExistsException If a book with the updated information already exists in the database.
     * @throws DatabaseOperationException If an error occurs while updating the book in the database.
     */
    public void update(BookDTO bookFromDatabase, BookDTO dtoToBeUpdated) throws NoUpdatedInfoException,
            BookAlreadyExistsException, InvalidIdException, DatabaseOperationException {

        try {
            int dtoID = dtoToBeUpdated.getId();
            int dbID = bookFromDatabase.getId();

            if (dtoID == 0 || dtoID != dbID){
                throw new InvalidIdException("There is some error in IDs information: Check if bookFromDatabase and dtoToBeUpdated have the same valid id");
            }

            if (compareBooks(bookFromDatabase, dtoToBeUpdated)){
                throw new NoUpdatedInfoException("There is no new information to be updated");
            }
            boolean bookAlredyExists = bookRepository.findExistingBook(BookMapper.dtoToBook(dtoToBeUpdated)) != null;
            if (bookAlredyExists){
                throw new BookAlreadyExistsException(dtoToBeUpdated);
            }
            bookRepository.update(BookMapper.dtoToBook(dtoToBeUpdated));

        } catch (NoUpdatedInfoException | BookAlreadyExistsException | InvalidIdException e){
            logger.error("The book could not be updated: " + e.getMessage(), e);
            throw e;

        } catch (Exception e){
            logger.error("Error while updating books in database: " + e.getMessage(), e);
            throw new DatabaseOperationException(e + e.getMessage());
        }
    }


    /**
     * Deletes the book with the specified ID from the database.
     *
     * This method calls the BookRepository to find and delete the book.
     *
     * @param id The ID of the book to be deleted.
     * @throws NoSuchElementException If no book with the specified ID is found in the database.
     * @throws DatabaseOperationException If an error occurs while deleting the book from the database.
     */
    public void delete(int id) throws NoSuchElementException, DatabaseOperationException{
        try {
            if (bookRepository.findById(id) != null) {
                bookRepository.delete(id);
            } else {
                throw new NoSuchElementException("There is no book with id = " + id + " in database.");
            }

        } catch (NoSuchElementException e){
            logger.error("The book could not be deletd: " + e.getMessage(), e);
            throw e;
        } catch (Exception e){
            logger.error("The book could not be deletd: " + e.getMessage(), e);
            throw new DatabaseOperationException("Error while deleting book from database");
        }
    }


    /**
     * Checks if the library is empty by verifying if there are no records in the Book table.
     *
     * @return True if the library is empty, false otherwise.
     * @throws DatabaseOperationException If an error occurs while counting books in the database.
     */
    public boolean libraryIsEmpty() throws DatabaseOperationException{
        try {
            return bookRepository.contRegisters() == 0;
        } catch (Exception e){
            logger.error("Error while checking database content: " + e.getMessage(), e);
            throw new DatabaseOperationException("Error while counting books in database");
        }
    }


    /**
     * Checks if the information of BookDTO objects is equal.
     *
     * This method compares the title, author, and year attributes of two BookDTO objects
     * to determine if their information is equal, ignoring case for the title and author.
     *
     * @param bookFromDatabase The BookDTO object representing the book's information from the database.
     * @param bookToBeUpdated The BookDTO object representing the book's information to be updated.
     * @return True if the information is equal, false otherwise.
     */
    private static boolean compareBooks(BookDTO bookFromDatabase, BookDTO bookToBeUpdated) {

        return bookFromDatabase.getTitle().equalsIgnoreCase(bookToBeUpdated.getTitle()) &&
                bookFromDatabase.getAuthor().equalsIgnoreCase(bookToBeUpdated.getAuthor()) &&
                bookFromDatabase.getYear() == bookToBeUpdated.getYear();

    }


}
