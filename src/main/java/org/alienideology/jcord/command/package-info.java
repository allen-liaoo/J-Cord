/**
 * Native Command Framework for JCord
 * <p>
 *     This is the native command framework support.
 *     The framework consist of Objects that implements {@link org.alienideology.jcord.command.CommandResponder}
 *     and register them to the {@link org.alienideology.jcord.command.CommandFramework}. Methods inside the
 *     {@link org.alienideology.jcord.command.CommandResponder} must add the {@link org.alienideology.jcord.command.Command}
 *     annotation in order for the {@link org.alienideology.jcord.command.CommandFramework} to detect the method and invoke it.
 * </p>
 * @since 0.0.2
 * @author AlienIdeology
 */
package org.alienideology.jcord.command;