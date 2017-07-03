package org.alienideology.jcord;

import com.neovisionaries.ws.client.WebSocketFactory;
import org.alienideology.jcord.event.EventManager;
import org.alienideology.jcord.internal.exception.ErrorResponseException;
import org.alienideology.jcord.internal.gateway.ErrorResponse;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.util.log.LogLevel;
import org.alienideology.jcord.util.log.LogMode;
import sun.rmi.runtime.Log;

import java.io.IOException;
import java.util.regex.Pattern;

/**
 * The IdentityImpl Builder for constructing an IdentityImpl.
 * @author AlienIdeology
 */
public final class IdentityBuilder {

    public final static Pattern TOKEN_PATTERN = Pattern.compile("(\\w{24})([.])(\\w{6})([.])(\\w{27})");

    private IdentityType type;
    private String token;

    private EventManager manager;
    private LogMode mode;

    /**
     * Default Constructor
     */
    public IdentityBuilder () {
    }

    /**
     * Build the IdentityImpl
     * This is deprecated, see build(boolean useBlocking)
     * @return The IdentityImpl
     * @throws IllegalArgumentException If the provided token is not valid.
     * @throws IOException If there is any connection issue.
     */
    @Deprecated
    public org.alienideology.jcord.Identity build () throws IllegalArgumentException, ErrorResponseException, IOException {
        IdentityImpl id =  new IdentityImpl(type, new WebSocketFactory());
        if (mode != null) id.LOG.setMode(mode);
        id.login(token).setEventManager(manager);
        return id;
    }

    /**
     * Build the IdentityImpl (Can choose blocking or not)
     * @param useBlocking To block the thread or not
     * @return The IdentityImpl
     * @throws IllegalArgumentException If the provided token is not valid.
     * @throws IOException If there is any connection issue.
     */
    public org.alienideology.jcord.Identity build (boolean useBlocking) throws IllegalArgumentException, ErrorResponseException, IOException {
        org.alienideology.jcord.Identity id = build();
        if (useBlocking) {
            while (!((IdentityImpl)id).CONNECTION.isReady()) {
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
     * @param type Bot or Human
     * @return IdentityBuilder for chaining.
     */
    public IdentityBuilder setIdentityType (IdentityType type) {
        this.type = type;
        return this;
    }

    /**
     * Define the token of this identity.
     * This event must be used before building the identity.
     * @param token The token from Discord Bot application page or user token.
     * @return IdentityBuilder for chaining.
     */
    public IdentityBuilder useToken (String token) {
        this.token = token;
        return this;
    }

    /**
     * Set the event managers of this identity
     * @param manager The event managers
     * @return IdentityBuilder for chaining.
     */
    public IdentityBuilder setEventManager(EventManager manager) {
        this.manager = manager;
        return this;
    }

    /**
     * Set the logger mode of this identity.
     *
     * @param mode The LogMode.
     * @return IdentityBuilder for chaining.
     */
    public IdentityBuilder setLogMode(LogMode mode) {
        this.mode = mode;
        return this;
    }

    /**
     * Validate the given token.
     *
     * @return True if the token is valid.
     */
    public static boolean isTokenValid(String token, IdentityType type) {
        if (token == null || !token.matches(TOKEN_PATTERN.pattern())) return false;
        try {
            IdentityImpl test = (IdentityImpl) new IdentityBuilder()
                    .setIdentityType(type)
                    .useToken(token)
                    .build();

            test.logout();
        } catch (ErrorResponseException ere) {
            if (ere.getResponse().equals(ErrorResponse.INVALID_AUTHENTICATION_TOKEN))
                return false;
        } catch (IOException ioe) {
            return false;
        }
        return true;
    }

}
