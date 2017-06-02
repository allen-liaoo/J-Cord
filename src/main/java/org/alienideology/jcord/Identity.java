package org.alienideology.jcord;

import com.mashape.unirest.http.Unirest;
import com.neovisionaries.ws.client.*;
import org.alienideology.jcord.gateway.GatewayAdaptor;
import org.apache.commons.logging.impl.SimpleLog;

import java.net.URI;

/**
 * @author AlienIdeology
 */
public class Identity {

    private IdentityType type;
    private String token;
    private WebSocketFactory wsFactory;
    private String baseURL = "https://discordapp.com/api/";

    private SimpleLog LOG = new SimpleLog("Identity");

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

        URI url = new URI(Unirest.get(baseURL+"gateway/bot").header("Authorization", this.token)
                .asJson().getBody().getObject().getString("url")+"?v=5&encoding=json");

        WebSocket socket = wsFactory.createSocket(url);
        socket.addListener(new GatewayAdaptor(this, socket)).connect();

        return this;
    }

    public String getToken () {
        return token;
    }

}
