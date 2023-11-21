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
    protected List<BookDTO> getAll() {
        List<Book> books = bookRepository.getAll();

        return books.stream().map(this::bookToDTO).toList();

    }




    /* **
          receives a BookDTO, check if it already exists in database,
          and returns null if it does, and call BookRepository to save, if not,
          and returns the book that was saved.

    */
    protected BookDTO save(BookDTO bookToBeSaved){

        Book book;
        BookDTO savedBook;

        if (bookAlreadyExists(bookToBeSaved)){
            savedBook = new BookDTO(-1, "", "", 0);

        } else {

           book = bookRepository.save(bookToBeSaved);
           savedBook = bookToDTO(book);

        }

        return savedBook;

    }


    /* **
         receive a String and returns a list of books containing it
         in the title. Uses BookRepository to find the books.
    */
    protected List<BookDTO> findByTitle(String title){
        List<Book> books = bookRepository.findByTitle(title);
        return books.stream().map(this::bookToDTO).toList();
    }

    /* **
         receive a String and returns a list of books containing it
         in the author name. Uses BookRepository to find the books.
    */
    protected List<BookDTO> findByAuthor(String author){
        List<Book> books = bookRepository.findByAuthor(author);
        return books.stream().map(this::bookToDTO).toList();
    }

    /* **
      receive an int and returns a list of books containing it
      as its year. Uses BookRepository to find the books.
   */
    protected List<BookDTO> findByYear(int year) {
        List<Book> books = bookRepository.findByYear(year);
        return books.stream().map(this::bookToDTO).toList();
    }

    /* **
     receive an int and returns the book that has this id.
     Uses BookRepository to find the book.
    */
    protected BookDTO findById(int id) {
        Book book = bookRepository.findById(id);
        BookDTO bookDTO;

        if (book == null){
            bookDTO = new BookDTO(-1, "", "", 0);
        } else {
            bookDTO = bookToDTO(book);
        }

        return bookDTO;

    }


    protected UpdateStatus update(BookDTO bookToBeUpdated) {

        UpdateStatus updateStatus;
        Book book = bookRepository.findById(bookToBeUpdated.getId());

        boolean bookIsTheSame = compareBooks(bookToBeUpdated, book);

        if (bookIsTheSame){

            updateStatus = UpdateStatus.SAME_BOOK;
            
        } else if (bookAlreadyExists(bookToBeUpdated)) {

            updateStatus = UpdateStatus.BOOK_ALREADY_EXISTS;

        } else {
            book.setTitle(bookToBeUpdated.getTitle());
            book.setAuthor(bookToBeUpdated.getAuthor());
            book.setYear(bookToBeUpdated.getYear());

            bookRepository.update(book);
            updateStatus = UpdateStatus.SUCCESS;
        }
        return updateStatus;
    }



    protected boolean delete(int id) {

        return bookRepository.delete(bookRepository.findById(id));

    }

    private static boolean compareBooks(BookDTO bookToBeUpdated, Book book) {
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

        List<Book> booksByTitle = bookRepository.findByTitle(bookDTO.getTitle());

        String author = bookDTO.getAuthor().toLowerCase();

        // TODO: Is it possible to improve this check?
        Book checkBook = booksByTitle.stream().filter(
                        b -> (b.getAuthor().toLowerCase()).equals(author)
                    )
                    .findFirst()
                    .orElse(null);

        System.err.println(checkBook);

        if (checkBook != null){
            bookAlreadyExists = true;
        }
        return bookAlreadyExists;
    }

    public BookDTO bookToDTO(Book book){
        return new BookDTO(book.getBookId(), book.getTitle(), book.getAuthor(), book.getYear());
    }


}
