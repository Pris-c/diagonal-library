package prisc.diagonallibrary.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import prisc.diagonallibrary.exception.*;

import java.time.LocalDateTime;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(EmptyApiResponseException.class)
    public ResponseEntity<EmptyApiResponseExceptionDetails>
    handlerEmptyApiResponseExceptionDetails(EmptyApiResponseException emptyApiReponse){
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

    @ExceptionHandler(VolumeIsAlreadyRegisteredException.class)
    public ResponseEntity<VolumeIsAlreadyRegisteredExceptionDetails>
    handlerVolumeIsAlreadyRegisteredExceptionDetails(VolumeIsAlreadyRegisteredException volumeIsAlreadyRegisteredExc){
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

    @ExceptionHandler(InvalidIsbnException.class)
    public ResponseEntity<VolumeIsAlreadyRegisteredExceptionDetails>
    handlerInvalidIsbnExceptionDetails(InvalidIsbnException invalidIsbnException){
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