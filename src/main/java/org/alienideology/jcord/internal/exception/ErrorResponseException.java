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

    public ErrorResponseException(int key, String message) {
        super("[Response Code "+ key + "] " + message);
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    public ErrorResponse getResponse() {
        return response;
    }

}
