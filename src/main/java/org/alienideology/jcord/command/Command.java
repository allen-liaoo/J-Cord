package org.alienideology.jcord.command;

import java.lang.annotation.*;

/**
 * Command - A command annotation used on methods for the native command framework.
 * @author AlienIdeology
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Command {

    /**
     * An array of aliases for this command.
     * Different method can have different aliases.
     */
    String[] aliases();

    /**
     * @return True if this method can only be triggered by GuildMessageCreateEvent.
     * Default is false: Can be trigger by all events.
     */
    boolean guildOnly() default false;

    /**
     * @return True if this method can only be triggered by PrivateMessageCreateEvent.
     * Default is false: Can be trigger by all events.
     */
    boolean privateOnly() default false;

}
