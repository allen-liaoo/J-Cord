package org.alienideology.jcord.command;

import java.lang.annotation.*;

/**
 * Command - A command annotation used on methods for the native command framework.
 * @author AlienIdeology
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Command {

    String[] aliases();

    boolean guildOnly() default false;

    boolean privateOnly() default false;

}
