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
    public HttpErrorException(HttpCode code, String message) {
        super("[Custom] [Http Code "+code.key+"] "+message);
        this.key = code.key;
        this.code = code;
    }

    /**
     * Constructor for unknown code.
     */
    public HttpErrorException(int key, String message) {
        super("[Unknown] [Http Code "+key+"] "+message);
        this.key = key;
        this.code = HttpCode.UNKNOWN;
    }

    public int getKey() {
        return key;
    }

    public HttpCode getCode() {
        return code;
    }

    public boolean isPermissionException() {
        return code.equals(HttpCode.FORBIDDEN);
    }

}
