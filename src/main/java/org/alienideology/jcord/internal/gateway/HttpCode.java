package org.alienideology.jcord.internal.gateway;

/**
 * HttpCode - HTTP Response/Error Codes
 * @author AlienIdeology
 */
public enum HttpCode {

    /* Success */
    OK (200, "The request completed successfully"),
    CREATED (201, "The entity was created successfully"),

    /* OK */
    NO_CONTENT (204, "The request completed successfully but returned no content"),
    NOT_MODIFIED (304, "The entity was not modified (no action was taken)"),

    /* Failure */
    BAD_REQUEST (400, "The request was improperly formatted, or the server couldn't understand it"),
    UNAUTHORIZED (401, "The Authorization header was missing or invalid"),
    FORBIDDEN (403, "The Authorization token you passed did not have permission to the resource"),
    NOT_FOUND (404, "The resource at the location specified doesn't exist"),
    METHOD_NOT_ALLOWED (405, "The HTTP event used is not valid for the location specified"),
    TOO_MANY_REQUESTS (429, "Too many requests, rate limited"),

    /* Server Side */
    GATEWAY_UNAVAILABLE (502, "There was not a gateway available to process your request. Wait a bit and retry"),
    SERVER_ERROR (510, "The server had an error processing your request (these are rare)"),

    UNKNOWN (-1, "Unknown HTTP error.");

    public int key;
    public String meaning;

    HttpCode(int key, String meaning) {
        this.key = key;
        this.meaning = meaning;
    }

    public static HttpCode getByKey(int key) {
        for (HttpCode code : values()) {
            if (code.key == key) return code;
            if (key > 502) return SERVER_ERROR;
        }
        return UNKNOWN;
    }

    public boolean isSuccess() {
        return this.key <= 201 && this.key > 0;
    }

    public boolean isOK() {
        return this.key > 201 && this.key <= 399;
    }

    public boolean isFailure() {
        return this.key >= 400 && this.key <= 499;
    }

    public boolean isServerError() {
        return this.key >= 500;
    }

}
