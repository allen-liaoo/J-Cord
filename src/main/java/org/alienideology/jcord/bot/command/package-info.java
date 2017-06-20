/**
 * Native Command Framework for JCord
 * <p>
 *     This is the native command framework support.
 *     The framework consist of Objects that implements {@link org.alienideology.jcord.bot.command.CommandResponder}
 *     and register them to the {@link org.alienideology.jcord.bot.command.CommandFramework}. Methods inside the
 *     {@link org.alienideology.jcord.bot.command.CommandResponder} must add the {@link org.alienideology.jcord.bot.command.Command}
 *     annotation in order for the {@link org.alienideology.jcord.bot.command.CommandFramework} to detect the method and alias it.
 * </p>
 * @since 0.0.2
 * @author AlienIdeology
 */
package org.alienideology.jcord.bot.command;