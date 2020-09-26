package io.hotdogger.login.exceptions;

/**
 * A custom exception class for Resource Not Found
 */
public class ResourceNotFound extends RuntimeException {

    /**
     * The basic constructor
     */
    public ResourceNotFound() {
    }

    /**
     * A custom constructor for ResourceNotFound that takes in an error message
     *
     * @param message The error message if resource not found exception is thrown
     */
    public ResourceNotFound(String message) {
        super(message);
    }
}