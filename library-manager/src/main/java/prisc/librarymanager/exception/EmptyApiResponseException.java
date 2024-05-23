package prisc.librarymanager.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class representing the scenario where there is no answer from the external API.
 * This exception is thrown when attempting to find a book with the isbn informed by the user.
 */
@Log4j2
@ResponseStatus(HttpStatus.NOT_FOUND)
public class EmptyApiResponseException extends RuntimeException{

    /**
     * Constructs a new EmptyApiResponseException with the specified message.
     *
     * @param message The detail message.
     */
    public EmptyApiResponseException(String message) {
        super(message);
    }
}
