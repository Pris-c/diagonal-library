package prisc.library;



import java.util.ArrayList;
import java.util.List;

public class BookRepository {

    private static int countId;
    private static List<Book> books;
    private static boolean hasBookList;

    public BookRepository() {
        if (!hasBookList) {
            books = new ArrayList<>();
            hasBookList = true;
        }
    }

    /* **
           returns a list with all books saved in the database
   */
    public List<Book> getAll() {
        return books;
    }


    /* **
           insert a new book in the database and returns it
   */
    public Book save(BookDTO bookToBeSaved){
        Book book = new Book(++countId, bookToBeSaved.getTitle(), bookToBeSaved.getAuthor(), bookToBeSaved.getYear());
        if(books.add(book)){
            return book;
        } else {
            return null;
        }
    }


    /* **
         receive a String and returns a list of books containing it
         in the title.
    */
    public List<Book> findByTitle(String title) {

        return books.stream()
                .filter(b -> (b.getTitle().toLowerCase())
                        .contains(title.toLowerCase()))
                .toList();
    }



    /* **
       receive a String and returns a list of books containing it
       in the author name.
    */
    public List<Book> findByAuthor(String author) {
        // TODO: Create a Predicate function
        return books.stream()
                .filter(b -> (b.getAuthor().toLowerCase())
                        .contains(author.toLowerCase()))
                .toList();

    }

    /* **
      receive an int and returns a list of books containing it
      as its year
   */
    public List<Book> findByYear(int year) {
        return books.stream()
                .filter(b -> (b.getYear() == year))
                .toList();
    }

    /* **
     receive an int and returns the book that has this id
    */
    public Book findById(int id) {

        return books.stream()
                .filter(b-> (b.getBookId() == id))
                .findFirst()
                .orElse(null);


    }



    public Book update(Book bookToBeUpdated) {
        Book book = findById(bookToBeUpdated.getBookId());

        book.setTitle(bookToBeUpdated.getTitle());
        book.setAuthor(bookToBeUpdated.getAuthor());
        book.setYear(bookToBeUpdated.getYear());

        return book;

    }


    /* **
     receive a Book and removes it from database
     return true in success case or false if fail
    */
    public boolean delete(Book book) {
        return books.remove(book);
    }

    public boolean libraryIsEmpty(){
        return books.isEmpty();
    }



}
