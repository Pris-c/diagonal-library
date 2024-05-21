package prisc.librarymanager.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class representing the scenario where the user provides invalid input.
 * This exception is typically thrown when the provided input does not meet certain criteria or is not within expected bounds.
 */
@Log4j2
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidUserInputException extends RuntimeException{

    /**
     * Constructs a new InvalidIsbnException with the specified message.
     *
     * @param message The detail message.
     */
    public InvalidUserInputException(String message) {
        super(message);
    }
}
