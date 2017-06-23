package org.alienideology.jcord.bot.command;

import org.alienideology.jcord.handle.permission.Permission;

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
// TODO: Auto-generated help command, description.
public @interface Command {

    /**
     * An array of aliases for this command.
     * Different method can have different aliases.
     */
    String[] aliases();

    /**
     * Permissions required for the command to be triggered by a certain user.
     *
     * @return The permissions.
     */
    Permission[] permissions() default {};

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
