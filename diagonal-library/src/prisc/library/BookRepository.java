package prisc.library;



import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookRepository {

    private static int countId;
    private static List<Book> books;
    private static boolean hasBookList;


    /** Constructor:
        Ensures there is only one Book List (database)
    */
    public BookRepository() {
        if (!hasBookList) {
            books = new ArrayList<>();
            hasBookList = true;
        }
    }

    /** returns a list with all books saved in the database */
    public List<Book> getAll() {
        return books;
    }


    /** inserts a new book in the database and returns it */
    public Book save(BookDTO bookToBeSaved){
        Book book = new Book(++countId, bookToBeSaved.getTitle(), bookToBeSaved.getAuthor(), bookToBeSaved.getYear());
        if(books.add(book)){
            return book;
        } else {
            return null;
        }
    }


    /** receives a String and returns a list of books containing it
        in the title.
    */
    public List<Book> findByTitle(String title) {
        return filterBooksByTitle(books, title);
    }

    /** receives a String and returns a list of books containing it
        in the author name.
    */
    public List<Book> findByAuthor(String author) {
        return filterBooksByAuthor(books, author);
    }


    /** receives an int and returns a list of books containing it
        as its year
   */
    public List<Book> findByYear(int year) {
        return filterBooksByYear(books, year);
    }


    /**  receives an int and returns the book that has this id  */
    public Book findById(int id) {
        return getBookById(books, id);
    }


    /**  receives a Book with an existent id, and replaces the information in database  */
    public Book update(Book bookToBeUpdated) {
        // TODO: receives an id
        Book book = findById(bookToBeUpdated.getBookId());

        book.setTitle(bookToBeUpdated.getTitle());
        book.setAuthor(bookToBeUpdated.getAuthor());
        book.setYear(bookToBeUpdated.getYear());

        return book;

    }


    /** receives a Book and removes it from database
        return true in success case or false if fails
    */
    public boolean delete(Book book) {
        return books.remove(book);
    }

    /** returns true if the database is empty or false if it is not  */
    public boolean libraryIsEmpty(){
        return books.isEmpty();
    }


    /** filters books by title content  */
    private List<Book> filterBooksByTitle(List<Book> books, String title) {
        return books.stream()
                .filter(b -> containsIgnoreCase(b.getTitle(), title))
                .collect(Collectors.toList());
    }

    /** filters books by author content  */
    private List<Book> filterBooksByAuthor(List<Book> books, String author) {
        return books.stream()
                .filter(b -> containsIgnoreCase(b.getAuthor(), author))
                .collect(Collectors.toList());
    }


    /** checks if a String contains another one ignoring case  */
    private boolean containsIgnoreCase(String text, String target) {
        return text.toLowerCase().contains(target.toLowerCase());
    }

    /** filters books by year  */
    private List<Book> filterBooksByYear(List<Book> books, int year) {
        return books.stream()
                .filter(b -> (b.getYear() == year))
                .collect(Collectors.toList());

    }

    /** get book by id  */
    private Book getBookById(List<Book> books, int id) {
        return books.stream()
                .filter(b -> (b.getBookId() == id))
                .findFirst()
                .orElse(null);
    }


}
