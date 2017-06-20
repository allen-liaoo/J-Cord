package org.alienideology.jcord.bot.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Command - A command annotation used on methods for the native command framework.
 * @author AlienIdeology
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
// TODO: Permissions field, description, and AUTO-GENERATED HELP COMMAND
public @interface Command {

    /**
     * An array of aliases for this command.
     * Different method can have different aliases.
     */
    String[] aliases();

    /**
     * @return True if this method can only be triggered by IGuildMessageCreateEvent.
     * Default is false: Can be trigger by all events.
     */
    boolean guildOnly() default false;

    /**
     * @return True if this method can only be triggered by PrivateMessageCreateEvent.
     * Default is false: Can be trigger by all events.
     */
    boolean privateOnly() default false;

}
