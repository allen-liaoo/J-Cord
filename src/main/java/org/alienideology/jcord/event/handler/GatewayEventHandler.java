package org.alienideology.jcord.event.handler;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.alienideology.jcord.Identity;
import org.alienideology.jcord.event.gateway.ReadyEvent;
import org.alienideology.jcord.gateway.GatewayAdaptor;
import org.alienideology.jcord.gateway.HttpPath;
import org.alienideology.jcord.object.ObjectBuilder;
import org.alienideology.jcord.object.guild.Guild;
import org.apache.commons.logging.impl.SimpleLog;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author AlienIdeology
 */
public class GatewayEventHandler extends EventHandler {

    private final GatewayAdaptor gateway;
    private final SimpleLog LOG;

    public GatewayEventHandler(Identity identity, GatewayAdaptor gateway) {
        super(identity);
        this.gateway = gateway;
        this.LOG = identity.LOG;
    }

    @Override
    public void dispatchEvent(JSONObject json, int sequence) {

        /* Ready Event */
        if (json.has("session_id")) {
            String session_id = json.getString("session_id");

            /* Create Guilds */
            // We do not use GuildCreateEvent to initialize guilds at the time.
            JSONArray guilds = json.getJSONArray("guilds");
            for (int i = 0; i < guilds.length(); i++) {
                JSONObject guild = guilds.getJSONObject(i);

                try {
                    JSONObject get = HttpPath.Guild.GET_GUILD.request(identity, guild.getString("id")).asJson().getBody().getObject();

                    //System.out.println(get.toString(4));

                    identity.addGuild(builder.buildGuild(get));

                } catch (UnirestException ne) {
                    LOG.debug("Getting guilds", ne);
                }
            }

            LOG.info("[READY] Guilds: "+guilds.length());

            /* Create PrivateChannels */
            JSONArray pms = json.getJSONArray("private_channels");
            for (int i = 0; i < pms.length(); i++) {
                JSONObject pm = pms.getJSONObject(i);

                builder.buildPrivateChannel(pm);
            }
            LOG.info("[READY] Private Channels: "+pms.length());

            /* Initialize Self User */
            identity.setSelf(builder.buildUser(json.getJSONObject("user")));
            LOG.info("[READY] Self");

            fireEvent(new ReadyEvent(identity, gateway, sequence, session_id));

            identity.CONNECTION = Identity.Connection.READY;

        /* Resume Event */
        } else {

        }
    }
}