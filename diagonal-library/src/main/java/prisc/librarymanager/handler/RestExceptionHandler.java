package prisc.librarymanager.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import prisc.librarymanager.exception.*;

import java.time.LocalDateTime;

/**
 * Global exception handler for REST controllers.
 * Provides custom handling for specific exceptions and validation errors.
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles EmptyApiResponseException.
     *
     * @param emptyApiReponse the exception to handle
     * @return a ResponseEntity with EmptyApiResponseExceptionDetails and HTTP status NOT_FOUND
     */
    @ExceptionHandler(EmptyApiResponseException.class)
    public ResponseEntity<EmptyApiResponseExceptionDetails>
    handlerEmptyApiResponseException(EmptyApiResponseException emptyApiReponse){
        return new ResponseEntity<>(
                EmptyApiResponseExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.NOT_FOUND.value())
                        .title("EmptyApiResponseException: Check the documentation")
                        .details(emptyApiReponse.getMessage())
                        .developerMessage(emptyApiReponse.getClass().getName())
                        .build(),
                HttpStatus.NOT_FOUND
        );
    }

    /**
     * Handles VolumeIsAlreadyRegisteredException.
     *
     * @param volumeIsAlreadyRegisteredExc the exception to handle
     * @return a ResponseEntity with VolumeIsAlreadyRegisteredExceptionDetails and HTTP status CONFLICT
     */
    @ExceptionHandler(VolumeIsAlreadyRegisteredException.class)
    public ResponseEntity<VolumeIsAlreadyRegisteredExceptionDetails>
    handlerVolumeIsAlreadyRegisteredException(VolumeIsAlreadyRegisteredException volumeIsAlreadyRegisteredExc){
        return new ResponseEntity<>(
                VolumeIsAlreadyRegisteredExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.CONFLICT.value())
                        .title("Volume Is Already Registeded Exception: Check the documentation")
                        .details(volumeIsAlreadyRegisteredExc.getMessage())
                        .developerMessage(volumeIsAlreadyRegisteredExc.getClass().getName())
                        .build(),
                HttpStatus.CONFLICT
        );
    }

    /**
     * Handles InvalidIsbnException.
     *
     * @param invalidIsbnException the exception to handle
     * @return a ResponseEntity with VolumeIsAlreadyRegisteredExceptionDetails and HTTP status BAD_REQUEST
     */
    @ExceptionHandler(InvalidIsbnException.class)
    public ResponseEntity<VolumeIsAlreadyRegisteredExceptionDetails>
    handlerInvalidIsbnException(InvalidIsbnException invalidIsbnException){
        return new ResponseEntity<>(
                VolumeIsAlreadyRegisteredExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .title("Invalid Isbn Exception: Check the documentation")
                        .details(invalidIsbnException.getMessage())
                        .developerMessage(invalidIsbnException.getClass().getName())
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }

    /**
     * Handles EbookTypeException.
     *
     * @param ebookTypeException the exception to handle
     * @return a ResponseEntity with EbookTypeExceptionDetails and HTTP status BAD_REQUEST
     */
    @ExceptionHandler(EbookTypeException.class)
    public ResponseEntity<EbookTypeExceptionDetails>
    handlerEbookTypeException(EbookTypeException ebookTypeException){
        return new ResponseEntity<>(
                EbookTypeExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .title("Ebook Type Exception: Check the documentation")
                        .details(ebookTypeException.getMessage())
                        .developerMessage(ebookTypeException.getClass().getName())
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }

    /**
     * Handles VolumeNotFoundException.
     *
     * @param volumeNotFoundException the exception to handle
     * @return a ResponseEntity with VolumeNotFoundExceptionDetails and HTTP status BAD_REQUEST
     */
    @ExceptionHandler(VolumeNotFoundException.class)
    public ResponseEntity<VolumeNotFoundExceptionDetails>
    handlerVolumeNotFoundException(VolumeNotFoundException volumeNotFoundException){
        return new ResponseEntity<>(
                VolumeNotFoundExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .title("Volume Not Found Exception: Check the documentation")
                        .details(volumeNotFoundException.getMessage())
                        .developerMessage(volumeNotFoundException.getClass().getName())
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }

    /**
     * Handles InvalidCredentialsException.
     *
     * @param invalidCredentialsException the exception to handle
     * @return a ResponseEntity with InvalidCredentialsExceptionDetails and HTTP status BAD_REQUEST
     */
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<InvalidCredentialsExceptionDetails>
    handlerInvalidCredentialsException(InvalidCredentialsException invalidCredentialsException){
        return new ResponseEntity<>(
                InvalidCredentialsExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .title("InvalidCredentialsException: Check the documentation")
                        .details(invalidCredentialsException.getMessage())
                        .developerMessage(invalidCredentialsException.getClass().getName())
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }

    /**
     * Handles validation errors for method arguments.
     *
     * @param ex the MethodArgumentNotValidException exception
     * @param headers the HTTP headers
     * @param status the HTTP status code
     * @param request the WebRequest
     * @return a ResponseEntity with ValidationExceptionDetails and HTTP status BAD_REQUEST
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return new ResponseEntity<>(
                ValidationExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .title("Bad Request Exception: Invalid ISBN")
                        .details("Validation failed for isbn value.")
                        .developerMessage(ex.getClass().getName())
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }
}