package prisc.librarymanager.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class representing the scenario where a volume already exists in the library database.
 * This exception is thrown when attempting to save a book with isbn that match an existing book.
 */
@Log4j2
@ResponseStatus(HttpStatus.CONFLICT)
public class VolumeIsAlreadyRegisteredException extends RuntimeException {

    /**
     * Constructs a new VolumeIsAlreadyRegisteredException with the specified message.
     *
     * @param message The detail message.
     */
    public VolumeIsAlreadyRegisteredException(String message) {
        super(message);
    }
}
