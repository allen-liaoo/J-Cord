package org.alienideology.jcord.handle.channel;

import org.alienideology.jcord.handle.IMention;

/**
 * TextChannel - An IGuildChannel for text messages.
 * @author AlienIdeology
 */
public interface ITextChannel extends IMessageChannel, IGuildChannel, IMention {

    String getTopic();

    boolean isDefaultChannel();

    boolean isNSFWChannel();

    @Override
    default String mention() {
        return "<#"+getId()+">";
    }

}
