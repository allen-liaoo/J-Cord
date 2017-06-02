package org.alienideology.jcord;

import com.neovisionaries.ws.client.WebSocketFactory;

/**
 * The Identity Builder for constructing an Identity.
 * @author AlienIdeology
 */
public class IdentityBuilder {

    private IdentityType type;
    private String token;

    public IdentityBuilder () {
    }

    public Identity build () throws Exception {
        Identity id =  new Identity(type, new WebSocketFactory());
        id.login(token);
        return id;
    }

    public IdentityBuilder setIdentityType (IdentityType type) {
        this.type = type;
        return this;
    }

    public IdentityBuilder useToken (String token) {
        this.token = token;
        return this;
    }
}
