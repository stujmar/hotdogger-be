package io.hotdogger.login.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BadInputException extends RuntimeException {

    /**
     * Used for when a REST request is not formatted properly, and a more specific exception does not
     * exist. Throws a 400 status code
     */
    public BadInputException() {
    }

    /**
     * Used for when a REST request is not formatted properly, and a more specific exception does not
     * exist. Throws a 400 status code
     */
    public BadInputException(String message) {
        super(message);
    }
}