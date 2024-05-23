package prisc.librarymanager.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class representing the scenario where login credentials are invalid.
 */
@Log4j2
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidCredentialsException extends RuntimeException{

    /**
     * Constructs a new InvalidCredentialsException with the specified message.
     *
     * @param message The detail message explaining the ebook type issue.
     */
    public InvalidCredentialsException(String message) {
        super(message);
    }
}