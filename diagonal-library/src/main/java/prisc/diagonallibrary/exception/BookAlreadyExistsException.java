package prisc.diagonallibrary.exception;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import prisc.diagonallibrary.service.BookService;

/**
 * Exception class representing the scenario where a book already exists in the diagonal library.
 * This exception is thrown when attempting to save or update a book with attributes that match an existing book.
 */
@Log4j2
@ResponseStatus(HttpStatus.CONFLICT)
public class BookAlreadyExistsException extends RuntimeException{

    /**
     * Constructs a new BookAlreadyExistsException with the specified message.
     *
     * @param message The detail message.
     */
    public BookAlreadyExistsException(String message) {
        super(message);
        log.error("BookAlreadyExistsException: " + message);

    }
}
