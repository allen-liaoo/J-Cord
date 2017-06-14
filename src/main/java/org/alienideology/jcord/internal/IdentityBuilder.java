package org.alienideology.jcord.internal;

import com.neovisionaries.ws.client.WebSocketFactory;
import org.alienideology.jcord.internal.event.EventManager;
import org.alienideology.jcord.internal.exception.ErrorResponseException;
import org.alienideology.jcord.internal.gateway.ErrorResponse;

import java.io.IOException;
import java.util.regex.Pattern;

/**
 * The Identity Builder for constructing an Identity.
 * @author AlienIdeology
 */
public class IdentityBuilder {

    public final static Pattern TOKEN_PATTERN = Pattern.compile("(\\w{24})([.])(\\w{6})([.])(\\w{27})");

    private IdentityType type;
    private String token;

    private EventManager manager;

    /**
     * Default Constructor
     */
    public IdentityBuilder () {
    }

    /**
     * Build the Identity
     * This is deprecated, see build(boolean useBlocking)
     * @return The Identity
     * @throws IllegalArgumentException If the provided token is not valid.
     * @throws IOException If there is any connection issue.
     */
    @Deprecated
    public Identity build () throws IllegalArgumentException, ErrorResponseException, IOException {
        Identity id =  new Identity(type, new WebSocketFactory());
        id.login(token).setEventManager(manager);
        return id;
    }

    /**
     * Build the Identity (Can choose blocking or not)
     * @param useBlocking To block the thread or not
     * @return The Identity
     * @throws IllegalArgumentException If the provided token is not valid.
     * @throws IOException If there is any connection issue.
     */
    public Identity build (boolean useBlocking) throws IllegalArgumentException, ErrorResponseException, IOException {
        Identity id = build();
        if (useBlocking) {
            while (!id.CONNECTION.isReady()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {}
            }
        }

        return id;
    }

    /**
     * Define the identity type to be build.
     * This method must be used before building the identity.
     * @param type Bot or Human
     * @return IdentityBuilder for chaining.
     */
    public IdentityBuilder setIdentityType (IdentityType type) {
        this.type = type;
        return this;
    }

    /**
     * Define the token of this identity.
     * This method must be used before building the identity.
     * @param token The token from Discord Bot application page or user token.
     * @return IdentityBuilder for chaining.
     */
    public IdentityBuilder useToken (String token) {
        this.token = token;
        return this;
    }

    /**
     * Validate the current token.
     * Note that if the {@link IdentityType} is not set, then this will always returns false.
     * @return True if the token is valid.
     */
    public boolean isTokenValid() {
        if (token == null || !token.matches(TOKEN_PATTERN.pattern())) return false;
        try {
            Identity test = build();
            test.logout();
        } catch (ErrorResponseException ere) {
            if (ere.getResponse().equals(ErrorResponse.INVALID_AUTHENTICATION_TOKEN))
                return false;
        } catch (IOException ioe) {
            return false;
        }
        return true;
    }

    /**
     * Set the event manager of this identity
     * @param manager The event manager
     * @return IdentityBuilder for chaining.
     */
    public IdentityBuilder setEventManager(EventManager manager) {
        this.manager = manager;
        return this;
    }
}