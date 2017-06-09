package org.alienideology.jcord;

import com.neovisionaries.ws.client.WebSocketFactory;
import org.alienideology.jcord.command.CommandFramework;
import org.alienideology.jcord.event.DispatcherAdaptor;
import org.alienideology.jcord.exception.ErrorResponseException;
import org.alienideology.jcord.gateway.ErrorResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * The Identity Builder for constructing an Identity.
 * @author AlienIdeology
 */
public class IdentityBuilder {

    public final static Pattern TOKEN_PATTERN = Pattern.compile("(\\w{24})([.])(\\w{6})([.])(\\w{27})");

    private IdentityType type;
    private String token;

    private List<DispatcherAdaptor> dispatchers;
    private List<CommandFramework> frameworks;

    /**
     * Default Constructor
     */
    public IdentityBuilder () {
        dispatchers = new ArrayList<>();
        frameworks = new ArrayList<>();
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
        id.login(token);
        dispatchers.forEach(id::addDispatchers);
        frameworks.forEach(id::addCommandFrameworks);
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
     * Register objects that extend DispatcherAdaptor, used to perform actions when a event is fired.
     * @see {@link #registerCommandFramework(CommandFramework...)} for native command framework support.
     * @param adaptors The adaptors to register
     * @return IdentityBuilder for chaining.
     */
    public IdentityBuilder registerDispatchers(DispatcherAdaptor... adaptors) {
        this.dispatchers.addAll(Arrays.asList(adaptors));
        return this;
    }

    /**
     * Register Native CommandFramework.
     * @param frameworks The frameworks to register
     * @return IdentityBuilder for chaining.
     */
    public IdentityBuilder registerCommandFramework(CommandFramework... frameworks) {
        for (CommandFramework framework : frameworks) {
            this.dispatchers.add(framework.getDispatcher());
            this.frameworks.add(framework);
        }
        return this;
    }
}
