package org.alienideology.jcord.internal.exception;

import org.alienideology.jcord.internal.gateway.ErrorCode;

/**
 * @author AlienIdeology
 */
public class ErrorCodeException extends RuntimeException {

    private int key;
    private ErrorCode code;

    public ErrorCodeException(ErrorCode code) {
        super("[Error Code "+code.key+"] "+code.meaning);
        this.code = code;
    }

    /**
     * Constructor for custom message.
     */
    public ErrorCodeException(int key, ErrorCode code, String message) {
        super("[Custom] [Error Code "+key+"] "+message);
        this.key = key;
        this.code = code;
    }

    public int getKey() {
        return key;
    }

    public ErrorCode getCode() {
        return code;
    }
}
