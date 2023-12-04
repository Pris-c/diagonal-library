package prisc.exceptions;

import prisc.book.BookDTO;

public class BookAlreadyExistsException extends RuntimeException{
    private BookDTO book;

    public BookAlreadyExistsException(BookDTO book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "BookAlreadyExistsException";
    }
    @Override
    public String getMessage() {
        return " The book " + book.getTitle() + ", " + book.getAuthor() + " from " + book.getYear()
                + " already exists in database whit id = " + book.getId();
    }





}
