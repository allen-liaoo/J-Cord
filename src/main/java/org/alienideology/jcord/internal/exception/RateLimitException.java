package org.alienideology.jcord.internal.exception;

/**
 * RateLimitException - An exception for gateway rate limits.
 */
public class RateLimitException extends RuntimeException {

    public RateLimitException(String message) {
        super(message);
    }
}
