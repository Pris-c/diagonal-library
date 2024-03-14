package prisc.diagonallibrary.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class representing the scenario where the volume_id informed by the user is not found in database.
 *
 */
@Log4j2
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class VolumeNotFoundException extends RuntimeException{

    /**
     * Constructs a new VolumeNotFoundException with the specified message.
     *
     * @param message The detail message.
     */
    public VolumeNotFoundException(String message) {
        super(message);
    }
}
