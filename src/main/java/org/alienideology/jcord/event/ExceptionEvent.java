package org.alienideology.jcord.event;

import org.alienideology.jcord.internal.object.IdentityImpl;

/**
 * @author AlienIdeology
 */
public class ExceptionEvent extends Event {

    private Exception exception;

    public ExceptionEvent(IdentityImpl identity, Exception exception) {
        super(identity, -1);
        this.exception = exception;
    }

    public Exception getException() {
        return exception;
    }
}
