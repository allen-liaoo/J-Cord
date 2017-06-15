package org.alienideology.jcord.internal.exception;

import org.alienideology.jcord.internal.gateway.ErrorResponse;

/**
 * ErrorResponseException - An exception for Json ErrorResponse
 * @author AlienIdeology
 */
public class ErrorResponseException extends RuntimeException {

    private int key;
    private ErrorResponse response;

    public ErrorResponseException(ErrorResponse error) {
        super("[Response Code "+ error.key + "] " + error.message);
        this.key = response.key;
        this.response = error;
    }

    /**
     * Constructor for custom message.
     */
    public ErrorResponseException(int key, ErrorResponse response, String message) {
        super("[Custom] [Response Code "+ key + "] " + message);
        this.key = key;
        this.response = response;
    }

    public int getKey() {
        return key;
    }

    public ErrorResponse getResponse() {
        return response;
    }

}
