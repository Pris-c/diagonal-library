package prisc.diagonallibrary.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

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
        log.error("InvalidIsbnException: " + message);
    }
}
