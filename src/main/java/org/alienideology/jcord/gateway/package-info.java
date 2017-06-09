/**
 * Internal Gateway Connections
 * <p>
 *     {@link org.alienideology.jcord.gateway.GatewayAdaptor} is the listener to the Gateway connection with Discord.
 *     {@link org.alienideology.jcord.gateway.OPCode}, {@link org.alienideology.jcord.gateway.DisconnectionCode},
 *     {@link org.alienideology.jcord.gateway.ErrorCode}, and {@link org.alienideology.jcord.gateway.ErrorResponse}
 *     are enumerations representing codes and responses of Discord Gateway.
 *     {@link org.alienideology.jcord.gateway.HttpPath} is where all the Http Requests' path go. It is set up for requesting
 *     Get, Post, Patch, Delete, etc methods.
 * </p>
 * @since 0.0.1
 * @author AlienIdeology
 */
package org.alienideology.jcord.gateway;