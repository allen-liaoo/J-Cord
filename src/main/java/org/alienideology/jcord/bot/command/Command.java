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
public @interface Command {

    /**
     * The name of a command.
     * This can be accessed when building a help command.
     *
     * @return The name.
     */
    String name() default "";

    /**
     * The description of a command.
     * This can be accessed when building a help command.
     *
     * @return The description
     */
    String description() default "";

    /**
     * An array of aliases for this command.
     * Different event can have different aliases.
     *
     * @return An array of aliases that would trigger the command.
     */
    String[] aliases();

    /**
     * Permissions required for the command to be triggered by a certain user.
     *
     * @return The permissions.
     */
    Permission[] permissions() default {};

    /**
     * @return True if this event can only be triggered by IGuildMessageCreateEvent.
     * Default is false: Can be trigger by all events.
     */
    boolean guildOnly() default false;

    /**
     * @return True if this event can only be triggered by PrivateMessageCreateEvent.
     * Default is false: Can be trigger by all events.
     */
    boolean privateOnly() default false;

}
