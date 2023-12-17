package prisc.diagonallibrary.exception;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import prisc.diagonallibrary.service.BookService;


@Log4j2
@ResponseStatus(HttpStatus.CONFLICT)
public class BookAlreadyExistsException extends RuntimeException{

    public BookAlreadyExistsException(String message) {
        super(message);
        log.error("BookAlreadyExistsException: " + message);

    }
}
