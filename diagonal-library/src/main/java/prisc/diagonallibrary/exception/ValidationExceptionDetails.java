package prisc.diagonallibrary.exception;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import javax.annotation.processing.Generated;

@Getter
@SuperBuilder
public class ValidationExceptionDetails extends ExceptionDetails{

    private final String fields;
    private final String fieldMessage;
}
