package org.alienideology.jcord.internal.exception;

import org.alienideology.jcord.internal.gateway.HttpCode;

/**
 * @author AlienIdeology
 */
public class HttpErrorException extends RuntimeException {

    private int key;
    private HttpCode code;

    public HttpErrorException(HttpCode code) {
        super("[Http Code "+code.key+"] "+code.meaning);
        this.code = code;
    }

    /**
     * Constructor for custom message.
     */
    public HttpErrorException(int key, HttpCode code, String message) {
        super("[Custom] [Http Code "+key+"] "+message);
        this.key = key;
        this.code = code;
    }

    public int getKey() {
        return key;
    }

    public HttpCode getCode() {
        return code;
    }
}
