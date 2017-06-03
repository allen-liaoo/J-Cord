package org.alienideology.jcord;

import com.neovisionaries.ws.client.WebSocketFactory;
import org.alienideology.jcord.event.DispatcherAdaptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The Identity Builder for constructing an Identity.
 * @author AlienIdeology
 */
public class IdentityBuilder {

    private IdentityType type;
    private String token;

    private List<DispatcherAdaptor> dispatchers = new ArrayList<>();

    public IdentityBuilder () {
    }

    public Identity build () throws Exception {
        Identity id =  new Identity(type, new WebSocketFactory());
        id.login(token);
        dispatchers.forEach(id::addListener);
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

    public IdentityBuilder addDispatchers (DispatcherAdaptor... listeners) {
        this.dispatchers.addAll(Arrays.asList(listeners));
        return this;
    }
}
