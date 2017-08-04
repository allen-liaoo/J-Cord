package org.alienideology.jcord;

import com.neovisionaries.ws.client.WebSocketFactory;
import org.alienideology.jcord.event.EventManager;
import org.alienideology.jcord.internal.exception.ErrorResponseException;
import org.alienideology.jcord.internal.gateway.ErrorResponse;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.util.log.Logger;

import java.net.ConnectException;
import java.net.URISyntaxException;
import java.util.function.Consumer;
import java.util.regex.Pattern;

/**
 * The IdentityImpl Builder for constructing an IdentityImpl.
 *
 * @author AlienIdeology
 */
public final class IdentityBuilder {

    public final static Pattern TOKEN_PATTERN = Pattern.compile("(\\w{24})([.])(\\w{6})([.])(\\w{27})");

    private IdentityType type;
    private String token;

    private EventManager manager;
    private Logger logger = new Logger("Identity");

    /**
     * Default Constructor
     */
    public IdentityBuilder () {
    }

    /**
     * Validate the given token.
     *
     * @return True if the token is valid.
     */
    public static boolean isValidToken(String token, IdentityType type) {
        if (token == null || !token.matches(TOKEN_PATTERN.pattern())) return false;
        try {
            IdentityImpl test = (IdentityImpl) new IdentityBuilder()
                    .setIdentityType(type)
                    .useToken(token)
                    .build(false);

            test.logout();
        } catch (ErrorResponseException ere) {
            if (ere.getResponse().equals(ErrorResponse.INVALID_AUTHENTICATION_TOKEN))
                return false;
        } catch (ConnectException | URISyntaxException e) {
            return false;
        }
        return true;
    }

    /**
     * Build the IdentityImpl (Can choose blocking or not)
     *
     * @param async To block the thread or not. Either synchronously ({@code false}) or asynchronously ({@code true}).
     * @return The IdentityImpl
     *
     * @throws ErrorResponseException
     *          If the provided token is not valid.
     *          If you get this exception, please check if the bot's token has been revoked or not.
     *          For clients, please copy the token again.
     * @see ErrorResponse#INVALID_AUTHENTICATION_TOKEN
     *
     * @throws URISyntaxException
     *          If Discord failed to provide a valid URI. Please contact a developer and provide the failed URI.
     *
     * @throws ConnectException
     *          Can be caused by:
     *          <ul>
     *              <li>Fail to create web socket (Establishing connection on the library side).</li>
     *              <li>Connecting the server failed.</li>
     *              <li>The opening handshake failed.</li>
     *          </ul>
     */
    public Identity build (boolean async) throws ErrorResponseException, URISyntaxException, ConnectException {
        IdentityImpl id = new IdentityImpl(type, token, new WebSocketFactory(), logger);
        id.setEventManager(manager == null ? new EventManager() : manager).login();
        if (!async) {
            while (!(id).CONNECTION.isReady()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {}
            }
        }

        return id;
    }

    /**
     * Define the identity type to be build.
     * This event must be used before building the identity.
     *
     * @param type Either {@link IdentityType#BOT} or {@link IdentityType#CLIENT}.
     * @return IdentityBuilder for chaining.
     */
    public IdentityBuilder setIdentityType (IdentityType type) {
        this.type = type;
        return this;
    }

    /**
     * Define the token of this identity.
     * This event must be used before building the identity.
     *
     * @param token The token from Discord IBot application page or user token.
     * @return IdentityBuilder for chaining.
     */
    public IdentityBuilder useToken (String token) {
        this.token = token;
        return this;
    }

    /**
     * Set the event managers of this identity
     *
     * @param manager The event managers
     * @return IdentityBuilder for chaining.
     */
    public IdentityBuilder setEventManager(EventManager manager) {
        this.manager = manager;
        return this;
    }

    /**
     * Set the logger of the identity.
     *
     * @param logger The Logger that will be used in {@link Identity}.
     * @return IdentityBuilder for chaining.
     */
    public IdentityBuilder setLogger(Consumer<Logger> logger) {
        logger.accept(this.logger);
        return this;
    }

}
