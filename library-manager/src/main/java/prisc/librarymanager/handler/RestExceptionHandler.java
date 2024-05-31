package prisc.librarymanager.handler;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import prisc.librarymanager.exception.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BookAlreadyExistsException.class)
    public ResponseEntity<BookAlreadyExistsExceptionDetails>
    handleBookAlreadyExistsDetails(BookAlreadyExistsException bookAlrExExc) {
        return new ResponseEntity<>(
                BookAlreadyExistsExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.CONFLICT.value())
                        .title("Book Alredy Exists Exception: Check the documentation")
                        .details(bookAlrExExc.getMessage())
                        .developerMessage(bookAlrExExc.getClass().getName())
                        .build(),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(BookIdNotFoundException.class)
    public ResponseEntity<BookIdNotFoundExceptionDetails>
    handleBookIdNotFoundException(BookIdNotFoundException bookIdNoFound) {
        return new ResponseEntity<>(
                BookIdNotFoundExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .title("Book Id Not Found Exception: Check the documentation")
                        .details(bookIdNoFound.getMessage())
                        .developerMessage(bookIdNoFound.getClass().getName())
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ValidationExceptionDetails> handleConstraintViolationException(
            ConstraintViolationException constrValidEx) {

        List<ConstraintViolation<?>> constraintViolationsList = new ArrayList<>(constrValidEx.getConstraintViolations());

        String fields = String.join(", ",
                constraintViolationsList
                        .stream()
                        .map(
                                cViolation ->
                                        cViolation
                                                .getPropertyPath()
                                                .toString()
                        )
                        .collect(Collectors.toSet()));

        String errorMessages = constraintViolationsList
                .stream()
                .map(cViolation -> String.format("%s: %s",
                        cViolation.getPropertyPath().toString(), cViolation.getMessage()))
                .collect(Collectors.joining(", "));

        return new ResponseEntity<>(
                ValidationExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .title("Bad Request Exception: Invalid Fields")
                        .details("Validation failed for one or more fields.")
                        .developerMessage(constrValidEx.getClass().getName())
                        .fields(fields)
                        .fieldMessage(errorMessages)
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {

        ExceptionDetails exceptionDetails = ExceptionDetails.builder()
                .timestamp(LocalDateTime.now())
                .status(statusCode.value())
                .title(ex.getCause().getMessage())
                .details(ex.getMessage())
                .developerMessage(ex.getClass().getName())
                .build();
        return createResponseEntity(exceptionDetails, headers, statusCode, request);
    }

}
