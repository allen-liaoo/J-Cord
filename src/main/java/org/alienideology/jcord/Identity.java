package org.alienideology.jcord;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.neovisionaries.ws.client.*;
import org.alienideology.jcord.event.DispatcherAdaptor;
import org.alienideology.jcord.exception.ErrorResponseException;
import org.alienideology.jcord.gateway.ErrorResponse;
import org.alienideology.jcord.gateway.GatewayAdaptor;
import org.alienideology.jcord.gateway.HttpPath;
import org.alienideology.jcord.object.guild.Guild;
import org.alienideology.jcord.object.User;
import org.apache.commons.logging.impl.SimpleLog;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The Identity of a bot (without shards), a shard, or a human (client)
 * @author AlienIdeology
 */
public class Identity {

    public SimpleLog LOG = new SimpleLog("Identity");

    private IdentityType type;
    private String token;

    private User self;

    private WebSocketFactory wsFactory;
    private List<DispatcherAdaptor> listeners = new ArrayList<>();
    private List<Guild> guilds = new ArrayList<>();

    public Identity (IdentityType type,  WebSocketFactory wsFactory) {
        this.type = type;
        this.wsFactory = wsFactory;
    }

    Identity login (String token) throws ErrorResponseException, IllegalArgumentException, IOException {
        if (type == IdentityType.BOT) {
            this.token = "Bot " + token;
        } else {
            this.token = token;
        }

        try {
            URI url = new URI(Unirest.get(HttpPath.Gateway.GET_BOT.getPath()).header("Authorization", this.token)
                    .asJson().getBody().getObject().getString("url") + "?encoding=json&v=" + GatewayAdaptor.GATEWAY_VERSION);

            WebSocket socket = wsFactory.createSocket(url);
            socket.addListener(new GatewayAdaptor(this, socket)).connect();
        } catch (UnirestException ne) {
            throw new ErrorResponseException(ErrorResponse.INVALID_AUTHENTICATION_TOKEN);
        } catch (URISyntaxException urise) {
            throw new ConnectException("Discord fail to provide a valid URI!");
        } catch (IOException iow) {
            throw new IOException("Fail to create WebSocket!");
        } catch (WebSocketException wse) {
            throw new ConnectException("Fail to connect to the Discord server!");
        }

        return this;
    }

    Identity addListener (DispatcherAdaptor... listeners) {
        this.listeners.addAll(Arrays.asList(listeners));
        return this;
    }

    public List<DispatcherAdaptor> getDispatchers () {
        return listeners;
    }

    public String getToken () {
        return token;
    }

    public User getSelf() {
        return self;
    }

    public List<Guild> getGuilds() {
        return Collections.unmodifiableList(guilds);
    }

    // TODO: Make this somehow private, unavailable for outside access
    public void addGuild (Guild... guilds) {
        this.guilds.addAll(Arrays.asList(guilds));
    }

    public void setSelf (User selfUser) {
        this.self = selfUser;
    }

}
