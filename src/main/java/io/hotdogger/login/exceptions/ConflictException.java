package io.hotdogger.login.exceptions;

/**
 * A custom exception class for Conflict
 */
public class ConflictException extends RuntimeException {

    /**
     * The basic constructor for ConflictException
     */
    public ConflictException() {
    }

    /**
     * A custom constructor for ConflictException that takes in a message when the exception is
     * thrown
     *
     * @param message A custom error message for ConflictException
     */
    public ConflictException(String message) {
        super(message);
    }
}