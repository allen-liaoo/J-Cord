package org.alienideology.jcord.event.handler;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.IdentityType;
import org.alienideology.jcord.event.gateway.ReadyEvent;
import org.alienideology.jcord.event.gateway.ResumedEvent;
import org.alienideology.jcord.internal.gateway.GatewayAdaptor;
import org.alienideology.jcord.internal.gateway.HttpPath;
import org.alienideology.jcord.internal.gateway.Requester;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.ObjectBuilder;
import org.alienideology.jcord.internal.object.client.Client;
import org.alienideology.jcord.internal.object.client.ClientSetting;
import org.alienideology.jcord.internal.object.client.Profile;
import org.alienideology.jcord.internal.object.user.User;
import org.alienideology.jcord.util.log.LogLevel;
import org.alienideology.jcord.util.log.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author AlienIdeology
 */
public class GatewayEventHandler extends EventHandler {

    private final GatewayAdaptor gateway;
    private final Logger LOG;

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

            try {
                /* Create Guilds */
                // We do not use GuildCreateEvent to initialize guilds
                // We use this to get the guilds' ID, then post http request to get guild information
                JSONArray guilds = json.getJSONArray("guilds");
                for (int i = 0; i < guilds.length(); i++) {
                    JSONObject guild = guilds.getJSONObject(i);
                    if (guild.has("unavailable") && guild.getBoolean("unavailable")) {
                        guild = new Requester(identity, HttpPath.Guild.GET_GUILD).request(guild.getString("id")).getAsJSONObject();
                    }
                    builder.buildGuild(guild); // Guild added to identity automatically
                }
                LOG.log(LogLevel.DEBUG, "[READY] Guilds: " + guilds.length());

                /* Create PrivateChannels */
                // This will not work FOR BOTS since Discord does not send private channels on ready event
                JSONArray pms = json.getJSONArray("private_channels");
                for (int i = 0; i < pms.length(); i++) {
                    JSONObject pm = pms.getJSONObject(i);

                    if (pm.getJSONArray("recipients").length() > 1) { // Group

                    } else {
                        builder.buildPrivateChannel(pm);
                    }
                }
                LOG.log(LogLevel.DEBUG, "[READY] Private Channels: " + pms.length());

                /* Create Self User */
                final User self = builder.buildUser(json.getJSONObject("user"));
                identity.setSelf(self);
                LOG.log(LogLevel.DEBUG, "[READY] Self");

                if (identity.getType().equals(IdentityType.CLIENT)) {

                    try {

                        Client client = identity.getClient();
                        ObjectBuilder cb = new ObjectBuilder(client);

                        /* Create Client Setting */
                        ClientSetting setting = cb.buildClientSetting(json.getJSONObject("user_settings"));
                        client.setSetting(setting);

                        /* Create Profile */
                        Profile profile = cb.buildProfile(json.getJSONObject("user"), self);
                        client.setProfile(profile);

                        /* Create Relationships */
                        JSONArray relations = json.getJSONArray("relationships");
                        for (int i = 0; i < relations.length(); i++) {
                            JSONObject rs = relations.getJSONObject(i);
                            cb.buildRelationship(rs); // Added to client automatically
                        }
                        LOG.log(LogLevel.DEBUG, "[READY] Client - Relationships: " + relations.length());

                        /* Create Notes */
                        JSONObject notes = json.getJSONObject("notes");
                        for (String key : notes.keySet()) {
                            cb.buildNote(key, notes.getString(key)); // Added to client automatically
                        }
                        LOG.log(LogLevel.DEBUG, "[READY] Client - Notes: " + notes.length());

                        /* Create Guild and TextChannel Settings */
                        JSONArray settings = json.getJSONArray("user_guild_settings");
                        for (int i = 0; i < settings.length(); i++) {
                            JSONObject gs = settings.getJSONObject(i);
                            cb.buildGuildSetting(gs); // Added to client automatically
                        }
                        LOG.log(LogLevel.DEBUG, "[READY] Client - Guild (& TextChannel) Settings: " + settings.length());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                dispatchEvent(new ReadyEvent(identity, gateway, sequence, session_id));

                identity.CONNECTION = Identity.Connection.READY;
            } catch (Exception e) {
                e.printStackTrace();
//                LOG.log(LogLevel.ERROR, e);
            }

        /* Resume Event */
        } else {

            identity.CONNECTION = Identity.Connection.RESUMING;

            dispatchEvent(new ResumedEvent(identity, gateway, sequence));

            identity.CONNECTION = Identity.Connection.READY;

        }
    }
}
