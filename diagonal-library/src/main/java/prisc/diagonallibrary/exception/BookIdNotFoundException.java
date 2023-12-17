package prisc.diagonallibrary.exception;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Log4j2
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BookIdNotFoundException extends RuntimeException{

    public BookIdNotFoundException(String message) {
        super(message);
        log.error("BookIdNotFoundException: " + message);
    }
}
