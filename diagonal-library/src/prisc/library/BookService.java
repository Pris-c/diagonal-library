package prisc.library;


import prisc.utils.enums.UpdateStatus;

import java.util.List;

public class BookService {

    private final BookRepository bookRepository = new BookRepository();

    public BookService() {
    }

    /**
     * Checks if the information of Book and BookDTO are equals
     */
    private static boolean compareBooks(BookDTO bookToBeUpdated, Book book) {
        String titleToUpdate = bookToBeUpdated.getTitle().toLowerCase();
        String authorToUpdate = bookToBeUpdated.getAuthor().toLowerCase();
        int yearToUpdate = bookToBeUpdated.getYear();

        String titleInDatabase = book.getTitle().toLowerCase();
        String authorInDatabase = book.getAuthor().toLowerCase();
        int yearInDatabase = book.getYear();

        return titleToUpdate.equalsIgnoreCase(titleInDatabase) &&
                authorToUpdate.equalsIgnoreCase(authorInDatabase) &&
                yearToUpdate == yearInDatabase;
    }


    /**
     * Calls BookRepository and returns a list with all books saved in the database
     */
    protected List<BookDTO> getAll() {
        List<Book> books = bookRepository.getAll();
        return getBooksAndMapToDTO(books);
    }


    /**
     * Receives a BookDTO, check if it already exists in database,
     * and returns null if it does or call BookRepository to save, if not.
     * Returns the book that was saved.
     */
    protected BookDTO save(BookDTO bookToBeSaved) {
        Book book;
        BookDTO savedBook;

        if (bookAlreadyExists(bookToBeSaved)) {
            savedBook = new BookDTO(-1, "", "", 0);

        } else {

            book = bookRepository.save(bookToBeSaved);
            savedBook = bookToDTO(book);
        }
        return savedBook;
    }


    /**
     * Receives a String and returns a list of books containing it
     * in the title. Uses BookRepository to find the books.
     */
    protected List<BookDTO> findByTitle(String title) {
        List<Book> books = bookRepository.findByTitle(title);
        return getBooksAndMapToDTO(books);
    }


    /**
     * Receives a String and returns a list of books containing it
     * in the author name. Uses BookRepository to find the books.
     */
    protected List<BookDTO> findByAuthor(String author) {
        List<Book> books = bookRepository.findByAuthor(author);
        return getBooksAndMapToDTO(books);
    }


    /**
     * Receives an int and returns a list of books containing it
     * as its year. Uses BookRepository to find the books.
     */
    protected List<BookDTO> findByYear(int year) {
        List<Book> books = bookRepository.findByYear(year);
        return getBooksAndMapToDTO(books);
    }


    /**
     * Receives an int and returns the book that has this id.
     * Uses BookRepository to find the book.
     */
    protected BookDTO findById(int id) {
        Book book = bookRepository.findById(id);
        BookDTO bookDTO;

        if (book == null) {
            bookDTO = new BookDTO(-1, "", "", 0);
        } else {
            bookDTO = bookToDTO(book);
        }
        return bookDTO;
    }


    /**
     * Receives a BookDTO, check if any information is new, checks if the book already exists
     * and if there is no conflict, calls Repository to update information.
     * Returns the status of operation
     */
    protected UpdateStatus updateStringFields(BookDTO bookToBeUpdated) {

        UpdateStatus updateStatus = null;
        Book bookInDatabase = bookRepository.findById(bookToBeUpdated.getId());

        boolean bookIsTheSame = compareBooks(bookToBeUpdated, bookInDatabase);

        if (bookIsTheSame) {

            updateStatus = UpdateStatus.SAME_BOOK;

        } else if (bookAlreadyExists(bookToBeUpdated)) {

            updateStatus = UpdateStatus.BOOK_ALREADY_EXISTS;

        } else {
            Book bookUpdated = bookRepository.update(dtoToBook(bookToBeUpdated));
            if(compareBooks(bookToBeUpdated, bookUpdated)){
                updateStatus = UpdateStatus.SUCCESS;
            }
        }
        return updateStatus;
    }


    /**
     * Receives a BookDTO, checks if new year is different from the saved one,
     * if it is, calls Repository to update information.
     * Returns the status of operation
     */
    protected UpdateStatus updateYear(BookDTO bookToBeUpdated) {

        UpdateStatus updateStatus = null;
        Book bookInDatabase = bookRepository.findById(bookToBeUpdated.getId());

        boolean bookYearIsTheSame = bookToBeUpdated.getYear() == bookInDatabase.getYear();

        if (bookYearIsTheSame) {
            updateStatus = UpdateStatus.SAME_BOOK;
        } else {
            Book bookUpdated = bookRepository.update(dtoToBook(bookToBeUpdated));
            if(compareBooks(bookToBeUpdated, bookUpdated)){
                updateStatus = UpdateStatus.SUCCESS;
            }
        }
        return updateStatus;
    }


    /**
     * Calls Repository to delete the book with the received id
     */
    protected boolean delete(int id) {
        return bookRepository.delete(bookRepository.findById(id));
    }


    /**
     * Checks if there is some book saved in database
     */
    public boolean libraryIsEmpty() {
        return bookRepository.libraryIsEmpty();
    }


    /**
     * Checks if already exist a book with the same information in database
     */
    private boolean bookAlreadyExists(BookDTO bookDTO) {
        String title = bookDTO.getTitle();
        String author = bookDTO.getAuthor();

        List<Book> matchingBooks = bookRepository.findByTitle(title)
                .stream()
                .filter(b -> b.getAuthor().equalsIgnoreCase(author))
                .toList();

        return !matchingBooks.isEmpty();
    }


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

}
