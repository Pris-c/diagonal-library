package prisc.diagonallibrary.exception;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;


/**
 * ExceptionDetails is a base class for representing details of an exception.
 * It provides common fields such as timestamp, status, title, details, and developer message.
 * This class is intended to be extended by specific exception detail classes.
 */
@Getter
@SuperBuilder
public class ExceptionDetails {

    protected LocalDateTime timestamp;
    protected int status;
    protected String title;
    protected String details;
    protected String developerMessage;

}
