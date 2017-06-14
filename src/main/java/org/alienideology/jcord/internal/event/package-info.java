/**
 * Where all the events and their handlers locate.
 * <p>
 *     {@link org.alienideology.jcord.internal.event.handler.EventHandler} are handlers that handle events fired by
 *     {@link org.alienideology.jcord.internal.gateway.GatewayAdaptor}, which are general events such as GuildUpdateEvent. The
 *     {@link org.alienideology.jcord.internal.event.handler.EventHandler} breaks down the gateway event and fire normal
 *     {@link org.alienideology.jcord.internal.event.Event} properly.
 *     {@link org.alienideology.jcord.internal.event.Event} are events that are used firing in {@link org.alienideology.jcord.internal.event.DispatcherAdaptor}.
 *
 * </p>
 * @since 0.0.1
 * @author AlienIdeology
 */
package org.alienideology.jcord.internal.event;