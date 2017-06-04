package org.alienideology.jcord.exception;

import org.alienideology.jcord.gateway.ErrorResponse;

/**
 * An exception for Json ErrorResponse
 * @author AlienIdeology
 */
public class ErrorResponseException extends Exception {

    private int key;
    private ErrorResponse response;

    public ErrorResponseException(ErrorResponse error) {
        super(error.message);
        this.key = error.key;
    }

    public int getKey() {
        return key;
    }

    public ErrorResponse getResponse() {
        return response;
    }

}
