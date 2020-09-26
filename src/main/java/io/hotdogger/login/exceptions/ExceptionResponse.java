package io.hotdogger.login.exceptions;

import java.util.Date;

/**
 * A response object that is used or will be returned for any errors/exceptions that occur. It is
 * for exception handlers.
 */
public class ExceptionResponse {

    private Date timestamp;
    private String status;
    private String error;
    private String errorMessage;

    public ExceptionResponse() {
    }

    public ExceptionResponse(Date timestamp, String status, String error, String errorMessage) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.errorMessage = errorMessage;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}