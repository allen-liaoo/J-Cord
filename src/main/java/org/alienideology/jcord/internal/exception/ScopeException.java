package org.alienideology.jcord.internal.exception;

import org.alienideology.jcord.oauth.Scope;

import java.util.Arrays;

/**
 * ScopeException - When the identity try to access a resource outside of its scopes.
 *
 * @author AlienIdeology
 */
public class ScopeException extends Exception {

    private Scope[] scopes;

    public ScopeException(Scope... scopes) {
        super("Missing scope(s): " + Arrays.toString(scopes));
        this.scopes = scopes;
    }

    public ScopeException(String cause) {
        super(cause);
        this.scopes = null;
    }

    public Scope[] getMissingScopes() {
        return scopes;
    }

}
