package org.alienideology.jcord.exception;

import org.alienideology.jcord.JCord;
import org.alienideology.jcord.gateway.ErrorResponse;

/**
 * ErrorResponseException - An exception for Json ErrorResponse
 * @author AlienIdeology
 */
public class ErrorResponseException extends RuntimeException {

    private int key;
    private ErrorResponse response;

    public ErrorResponseException(ErrorResponse error) {
        super("[Code "+ error.key + "] " + error.message);
        this.response = error;
        this.key = response.key;
    }

    public int getKey() {
        return key;
    }

    public ErrorResponse getResponse() {
        return response;
    }

}
