package io.hotdogger.login.exceptions;

public class ServiceUnavailable extends RuntimeException {

    /**
     * Used for when a repository (or any other part of the server) is unresponsive, or not behaving
     * properly. Throws a 500 status code
     */
    public ServiceUnavailable() {
    }

    /**
     * Used for when a repository (or any other part of the server) is unresponsive, or not behaving
     * properly. Throws a 500 status code
     */
    public ServiceUnavailable(String message) {
        super(message);
    }

    /**
     * Used for when a repository (or any other part of the server) is unresponsive, or not behaving
     * properly. Throws a 500 status code
     */
    public ServiceUnavailable(Exception e) {
        super(e.getCause());
    }
}

