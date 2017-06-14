package org.alienideology.jcord.internal.event;

import org.alienideology.jcord.internal.Identity;

/**
 * @author AlienIdeology
 */
public class ExceptionEvent extends Event {

    private Exception exception;

    public ExceptionEvent(Identity identity, Exception exception) {
        super(identity, -1);
        this.exception = exception;
    }

    public Exception getException() {
        return exception;
    }
}
