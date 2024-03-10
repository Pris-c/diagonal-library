package prisc.diagonallibrary.exception.volumes;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import prisc.diagonallibrary.exception.ExceptionDetails;

/**
 * Exception class representing the scenario where the ISBN informed by the user is not valid.
 * This exception is thrown when ISBN has an invalid size or some non-numeric character.
 */
@Log4j2
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidIsbnException extends RuntimeException{

    /**
     * Constructs a new InvalidIsbnException with the specified message.
     *
     * @param message The detail message.
     */
    public InvalidIsbnException(String message) {
        super(message);
        log.error("InvalidIsbnException: " + message);
    }
}