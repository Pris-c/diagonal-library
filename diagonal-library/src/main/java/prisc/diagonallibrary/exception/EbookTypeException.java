package prisc.diagonallibrary.exception;


import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class representing the scenario where the ISBN informed by the user refers to an ebook.
 *
 */
@Log4j2
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EbookTypeException extends RuntimeException{


    /**
     * Constructs a new EbookTypeException with the specified message.
     *
     * @param message The detail message.
     */
    public EbookTypeException(String message) {
        super(message);
    }
}
