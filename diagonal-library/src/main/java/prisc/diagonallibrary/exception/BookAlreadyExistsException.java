package prisc.diagonallibrary.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import prisc.diagonallibrary.service.BookService;


@ResponseStatus(HttpStatus.CONFLICT)
public class BookAlreadyExistsException extends RuntimeException{

    private static final Logger logger = LogManager.getLogger(BookAlreadyExistsException.class);
    public BookAlreadyExistsException(String message) {
        super(message);
        logger.error("BookAlreadyExistsException: " + message);

    }
}
