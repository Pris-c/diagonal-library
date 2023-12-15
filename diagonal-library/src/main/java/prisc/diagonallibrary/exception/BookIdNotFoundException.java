package prisc.diagonallibrary.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BookIdNotFoundException extends RuntimeException{

    private static final Logger logger = LogManager.getLogger(BookIdNotFoundException.class);

    public BookIdNotFoundException(String message) {
        super(message);
        logger.error("BookIdNotFoundException: " + message);
    }
}
