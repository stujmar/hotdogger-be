package io.hotdogger.login.exceptions;

import io.hotdogger.login.players.PlayerServiceImpl;
import java.util.Date;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.JDBCException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * It applies exception handling techniques across the whole application. Contains the various
 * custom exception handler methods.
 */
@ControllerAdvice
public class ExceptionController {

    private final Logger loggerPlayer = LogManager.getLogger(PlayerServiceImpl.class);

    /**
     * Triggers when the ResourceNotFound exception is thrown.
     *
     * @param error the ResourceNotFound exception containing the custom message.
     * @return the ResponseEntity containing the custom exception and the status code 404.
     */
    @ExceptionHandler(ResourceNotFound.class)
    protected ResponseEntity<ExceptionResponse> resourceNotFound(ResourceNotFound error) {
        ExceptionResponse response = new ExceptionResponse(new Date(), "404", "Not Found",
                error.getMessage());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Triggers when the ConflictException exception is thrown.
     *
     * @param error the ConflictException exception containing the custom message.
     * @return the ResponseEntity containing the custom exception and the status code 409.
     */
    @ExceptionHandler(ConflictException.class)
    protected ResponseEntity<ExceptionResponse> ConflictException(ConflictException error) {
        ExceptionResponse response = new ExceptionResponse(new Date(), "409", "Conflict",
                error.getMessage());

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    /**
     * Triggers when the ServiceUnavailable exception is thrown. The ServiceUnavailable error is any
     * errors that might occur, whether is database errors or unexpected errors.
     *
     * @param error the ServiceUnavailable exception containing the custom message.
     * @return the ResponseEntity containing the custom exception and the status code 503 or 500
     * depending on the cause of the exception.
     */
    @ExceptionHandler(ServiceUnavailable.class)
    protected ResponseEntity<ExceptionResponse> serverErrors(ServiceUnavailable error) {
        String exceptionMessage = error.getMessage();
        ExceptionResponse response;

        if (error.getCause() instanceof JDBCException) { //these are DB errors
            exceptionMessage = ((JDBCException) error.getCause()).getSQLException().getMessage();

            response = new ExceptionResponse(new Date(), "503", "Database Server Error",
                    exceptionMessage);
            loggerPlayer.error(exceptionMessage);
            return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);

        } else { //any other 500 errors that aren't DB errors, like unexpected error?
            exceptionMessage = "Error: " + exceptionMessage
                    + "   Class: " + error.getStackTrace()[0].getClassName()
                    + "   Method: " + error.getStackTrace()[0].getMethodName()
                    + "   Line: " + error.getStackTrace()[0].getLineNumber();

            response = new ExceptionResponse(new Date(), "500", "Unexpected Server Error",
                    exceptionMessage);
            loggerPlayer.error(exceptionMessage);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
