package org.alienideology.jcord.event.handler;

import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.event.gateway.ReadyEvent;
import org.alienideology.jcord.event.gateway.ResumedEvent;
import org.alienideology.jcord.internal.gateway.GatewayAdaptor;
import org.alienideology.jcord.internal.gateway.HttpPath;
import org.alienideology.jcord.internal.gateway.Requester;
import org.apache.commons.logging.impl.SimpleLog;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author AlienIdeology
 */
public class GatewayEventHandler extends EventHandler {

    private final GatewayAdaptor gateway;
    private final SimpleLog LOG;

    public GatewayEventHandler(IdentityImpl identity, GatewayAdaptor gateway) {
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
                    JSONObject get = new Requester(identity, HttpPath.Guild.GET_GUILD).request(guild.getString("id")).getAsJSONObject();
                    builder.buildGuild(get); // Guild added to identity automatically
                } catch (RuntimeException e) {
                    LOG.error("Initializing Guilds", e);
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

            identity.CONNECTION = IdentityImpl.Connection.READY;

        /* Resume Event */
        } else {

            identity.CONNECTION = IdentityImpl.Connection.RESUMING;

            fireEvent(new ResumedEvent(identity, gateway, sequence));

            identity.CONNECTION = IdentityImpl.Connection.READY;

        }
    }
}
