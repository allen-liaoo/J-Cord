package org.alienideology.jcord.event;

import org.alienideology.jcord.internal.object.Identity;

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
