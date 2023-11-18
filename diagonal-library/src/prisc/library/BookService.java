package prisc.library;


import prisc.utils.enums.UpdateStatus;

import java.util.List;

public class BookService {

    private final BookRepository bookRepository = new BookRepository();

    public BookService() {
    }


    /* **
          call BookRepository and returns a list with all books saved in the database
    */
    private List<Book> getAll() {
        return bookRepository.getAll();
    }




    /* **
          receives a BookDTO, check if it already exists in database,
          and returns null if it does, and call BookRepository to save, if not,
          and returns the book that was saved.

    */
    private Book save(BookDTO bookToBeSaved){

        if (bookAlreadyExists(bookToBeSaved)){
        // TODO: Personalize exception
            return null;
        } else {
           return   bookRepository.save(bookToBeSaved);
        }

    }


    /* **
         receive a String and returns a list of books containing it
         in the title. Uses BookRepository to find the books.
    */
    public List<prisc.library.Book> findByTitle(String title){
        return bookRepository.findByTitle(title);
    }

    /* **
         receive a String and returns a list of books containing it
         in the author name. Uses BookRepository to find the books.
    */
    public List<prisc.library.Book> findByAuthor(String author){
        return bookRepository.findByAuthor(author);
    }

    /* **
      receive an int and returns a list of books containing it
      as its year. Uses BookRepository to find the books.
   */
    public List<prisc.library.Book> findByYear(int year) {
        return bookRepository.findByYear(year);
    }

    /* **
     receive an int and returns the book that has this id.
     Uses BookRepository to find the book.
    */
    public prisc.library.Book findById(int id) {
        return bookRepository.findById(id);
    }


    public UpdateStatus update(int id, BookDTO bookToBeUpdated) {

        UpdateStatus updateStatus;
        Book book = bookRepository.findById(id);

        boolean bookIsTheSame = isBookIsTheSame(bookToBeUpdated, book);

        if (bookIsTheSame){
            updateStatus = UpdateStatus.SAME_BOOK;
            
        } else if (bookAlreadyExists(bookToBeUpdated)) {

            updateStatus = UpdateStatus.BOOK_ALREADY_EXISTS;
        } else {

            bookRepository.update(id, bookToBeUpdated);
            updateStatus = UpdateStatus.SUCCESS;
        }
            return updateStatus;
        }



    private void deleteBook(int id) {

        bookRepository.delete(findById(id));

    }




    private static boolean isBookIsTheSame(BookDTO bookToBeUpdated, Book book) {
        String title = bookToBeUpdated.getTitle().toLowerCase();
        String author = bookToBeUpdated.getAuthor().toLowerCase();
        int year = bookToBeUpdated.getYear();

        // TODO: Is it possible to improve this check?
        //Checking if the book is the same to the saved
        return  (book.getTitle().toLowerCase()).equals(title) &&
                (book.getAuthor().toLowerCase()).equals(author) &&
                book.getYear() == year;
    }

    private boolean bookAlreadyExists(BookDTO bookDTO){

        boolean bookAlreadyExists = false;

        List<Book> books = bookRepository.getAll();

        String title = bookDTO.getTitle().toLowerCase();
        String author = bookDTO.getAuthor().toLowerCase();
        int year = bookDTO.getYear();

        // TODO: Is it possible to improve this check?
        Book checkBook = books.stream().filter(
                        b ->
                        (b.getTitle().toLowerCase()).equals(title) &&
                        (b.getAuthor().toLowerCase()).equals(author) &&
                        b.getYear() == year
                )
                .findFirst()
                .orElse(null);

        if (checkBook != null){
            bookAlreadyExists = true;
        }
        return bookAlreadyExists;
    }



}
