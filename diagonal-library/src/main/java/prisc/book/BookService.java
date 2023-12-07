package prisc.book;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import prisc.exceptions.BookAlreadyExistsException;
import prisc.exceptions.DatabaseOperationException;
import prisc.exceptions.InvalidIdException;
import prisc.exceptions.NoUpdatedInfoException;

import java.util.List;
import java.util.NoSuchElementException;

public class BookService {

    private static final Logger logger = LogManager.getLogger(BookService.class);
    private final BookRepository bookRepository = new BookRepository();

    public BookService() {
    }

    /**
     * Retrieves and returns a list of all books saved in the database.
     * This method calls the BookRepository to fetch the books and maps them to BookDTO objects.
     *
     * @return A list of BookDTO objects representing all books in the database.
     * @throws DatabaseOperationException If an error occurs while retrieving Book objects from the database.
     */
    public List<BookDTO> getAll() {
        List<Book> books;

        try {
            books = bookRepository.getAll();
            return getBooksAndMapToDTO(books);

        } catch (Exception e){
            throw new DatabaseOperationException("Error while retrieving Book objects from database");
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
    public void save(BookDTO dtoToBeSaved) {
        Book bookToBeSaved = dtoToBook(dtoToBeSaved);
        Book existingBook;

        try {
             existingBook = bookRepository.findExistingBook(bookToBeSaved);

            if (existingBook == null) {
                bookRepository.save(bookToBeSaved);

            } else {
                throw new BookAlreadyExistsException(bookToDTO(existingBook));
            }

        } catch (BookAlreadyExistsException ex){
            logger.error("Error while saving Book: " + ex + ex.getMessage());
            throw ex;

        } catch (Exception e){
            throw new DatabaseOperationException("Error while saving Book in database");
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
    public List<BookDTO> findByTitle(String title) {
        List<Book> books;

        try{
            books = bookRepository.findByTitle(title);

        } catch (Exception e){
            throw new DatabaseOperationException("Error while searching books in database");
        }
        return getBooksAndMapToDTO(books);
    }


    /**
     * Retrieves and returns a list of books from the database containing the specified author string.
     * This method uses BookRepository to find books by their authors and maps the results to BookDTO objects.
     *
     * @param author The author to search for in the books.
     * @return A list of BookDTO objects containing the specified author.
     * @throws DatabaseOperationException If an error occurs while searching for books in the database.
     */
    public List<BookDTO> findByAuthor(String author) {
        List<Book> books;

        try{
            books = bookRepository.findByAuthor(author);

        } catch (Exception e){
            throw new DatabaseOperationException("Error while searching books in database");
        }
        return getBooksAndMapToDTO(books);
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
            throw new DatabaseOperationException("Error while searching books in database");
        }
        return getBooksAndMapToDTO(books);
    }


    /**
     * Retrieves and returns a BookDTO representing the book with the specified ID.
     * This method uses BookRepository to find a book by its ID and maps the result to a BookDTO object.
     *
     * @param id The ID of the book to search for.
     * @return A BookDTO object representing the book with the specified ID.
     * @throws DatabaseOperationException If an error occurs while searching for the book in the database.
     */
    public BookDTO findById(int id) {
        Book book;

        try {
            book = bookRepository.findById(id);

            if (book == null){
                return null;
            } else {
                return bookToDTO(book);
            }

        } catch (Exception e){
            throw new DatabaseOperationException("Error while searching books in database");
        }
    }


    /**
     * Updates the information of a book in the database based on the provided BookDTO.
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
    public void update(BookDTO bookFromDatabase, BookDTO dtoToBeUpdated){

        try {
            int dtoID = dtoToBeUpdated.getId();
            int dbID = bookFromDatabase.getId();

            if (dtoID == 0 || dtoID != dbID){
                throw new InvalidIdException("There is some error in IDs information: Check if bookFromDatabase and dtoToBeUpdated have the same valid id");
            }

            if (compareBooks(bookFromDatabase, dtoToBeUpdated)){
                throw new NoUpdatedInfoException("There is no new information to be updated");
            }
            boolean bookAlredyExists = bookRepository.findExistingBook(dtoToBook(dtoToBeUpdated)) != null;
            if (bookAlredyExists){
                throw new BookAlreadyExistsException(dtoToBeUpdated);
            }
            bookRepository.update(dtoToBook(dtoToBeUpdated));

        } catch (NoUpdatedInfoException | BookAlreadyExistsException e){
            logger.info(e + e.getMessage());
            throw e;

        } catch (InvalidIdException e){
            logger.error(e);
            throw e;

        } catch (Exception e){
            throw e;
        }
    }



    /**
     * Deletes the book with the specified ID from the database.
     * This method calls the BookRepository to find and delete the book.
     *
     * @param id The ID of the book to be deleted.
     * @throws NoSuchElementException If no book with the specified ID is found in the database.
     * @throws DatabaseOperationException If an error occurs while deleting the book from the database.
     */
    public void delete(int id) {
        try {
            if (bookRepository.findById(id) != null) {
                bookRepository.delete(id);
            } else {
                throw new NoSuchElementException("There is no book with id = " + id + " in database.");
            }

        } catch (NoSuchElementException noSuchElement){
            logger.info(noSuchElement + noSuchElement.getMessage());
            throw noSuchElement;
        } catch (Exception e){
            throw new DatabaseOperationException("Error while deleting book from database");
        }
    }


    /**
     * Checks if the library is empty by verifying if there are no records in the Book table.
     *
     * @return True if the library is empty, false otherwise.
     */
    public boolean libraryIsEmpty(){
        return bookRepository.contRegisters() == 0;
    }









    // TODO: Refactoring to BookMapper

    /**
     * Creates a BookDTO from a Book
     */
    private BookDTO bookToDTO(Book book) {
        return new BookDTO(book.getBookId(), book.getTitle(), book.getAuthor(), book.getYear());
    }


    /**
     * Creates a Book from a BookDTO
     */
    private Book dtoToBook(BookDTO bookDTO) {
        return new Book(bookDTO.getId(), bookDTO.getTitle(), bookDTO.getAuthor(), bookDTO.getYear());
    }


    /**
     * Creates a List of BookDTO from a List of Book
     */
    private List<BookDTO> getBooksAndMapToDTO(List<Book> books) {
        return books.stream().map(this::bookToDTO).toList();
    }


    /**
     * Checks if the information of Book and BookDTO are equals
     */
    private static boolean compareBooks(BookDTO bookFromDatabase, BookDTO bookToBeUpdated) {

        return bookFromDatabase.getTitle().equalsIgnoreCase(bookToBeUpdated.getTitle()) &&
                bookFromDatabase.getAuthor().equalsIgnoreCase(bookToBeUpdated.getAuthor()) &&
                bookFromDatabase.getYear() == bookToBeUpdated.getYear();

    }


}
