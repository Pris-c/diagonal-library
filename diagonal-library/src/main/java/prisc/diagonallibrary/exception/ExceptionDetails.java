package prisc.diagonallibrary.exception;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
public class ExceptionDetails {

    protected LocalDateTime timestamp;
    protected int status;
    protected String title;
    protected String details;
    protected String developerMessage;

}
