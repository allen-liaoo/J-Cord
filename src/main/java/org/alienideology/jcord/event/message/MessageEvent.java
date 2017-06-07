package org.alienideology.jcord.event.message;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.event.Event;

/**
 * @author AlienIdeology
 */
public class MessageEvent extends Event {

    public MessageEvent(Identity identity, int sequence) {
        super(identity, sequence);
    }


}
