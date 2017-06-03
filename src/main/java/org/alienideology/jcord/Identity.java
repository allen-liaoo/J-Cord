package org.alienideology.jcord;

import com.mashape.unirest.http.Unirest;
import com.neovisionaries.ws.client.*;
import org.alienideology.jcord.gateway.GatewayAdaptor;
import org.alienideology.jcord.gateway.HttpPath;
import org.alienideology.jcord.object.Guild;
import org.apache.commons.logging.impl.SimpleLog;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The Identity of a bot (without shards), a shard, or a human (client)
 * @author AlienIdeology
 */
public class Identity {

    public SimpleLog LOG = new SimpleLog("Identity");

    private IdentityType type;
    private String token;

    private WebSocketFactory wsFactory;

    private List<Guild> guilds = new ArrayList<>();

    public Identity (IdentityType type,  WebSocketFactory wsFactory) {
        this.type = type;
        this.wsFactory = wsFactory;
    }

    Identity login (String token) throws Exception {
        if (type == IdentityType.BOT) {
            this.token = "Bot " + token;
        } else {
            this.token = token;
        }

        URI url = new URI(Unirest.get(HttpPath.Gateway.GET_BOT.getPath()).header("Authorization", this.token)
                .asJson().getBody().getObject().getString("url")+"?encoding=json&v="+GatewayAdaptor.GATEWAY_VERSION);

        WebSocket socket = wsFactory.createSocket(url);
        socket.addListener(new GatewayAdaptor(this, socket)).connect();

        return this;
    }

    public String getToken () {
        return token;
    }

    // TODO: Make this somehow private, unavailable for outside access
    public void addGuild(Guild... guilds) {
        this.guilds.addAll(Arrays.asList(guilds));
    }

}
