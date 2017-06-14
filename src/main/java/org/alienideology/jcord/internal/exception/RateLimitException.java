package org.alienideology.jcord.internal.exception;

public class RateLimitException extends RuntimeException {

    public RateLimitException() {
        super("You are being rate limited!");
    }
}
