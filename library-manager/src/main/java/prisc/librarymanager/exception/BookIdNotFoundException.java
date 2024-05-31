package prisc.librarymanager.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class representing the scenario where a book with a specific ID is not found in the diagonal library.
 * This exception is thrown when attempting to retrieve a book by a non-existent ID.
 */
@Log4j2
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BookIdNotFoundException extends RuntimeException {

    /**
     * Constructs a new BookIdNotFoundException with the specified message.
     *
     * @param message The detail message.
     */
    public BookIdNotFoundException(String message) {
        super(message);
        log.error("BookIdNotFoundException: " + message);
    }
}
