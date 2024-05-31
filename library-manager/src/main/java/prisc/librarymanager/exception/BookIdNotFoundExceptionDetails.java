package prisc.librarymanager.exception;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
public class BookIdNotFoundExceptionDetails {

    private LocalDateTime timestamp;
    private int status;
    private String title;
    private String details;
    private String developerMessage;

}
