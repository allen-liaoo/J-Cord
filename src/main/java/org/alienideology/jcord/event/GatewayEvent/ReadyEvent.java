package org.alienideology.jcord.event.GatewayEvent;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.alienideology.jcord.Identity;
import org.alienideology.jcord.gateway.GatewayAdaptor;
import org.alienideology.jcord.gateway.HttpPath;
import org.alienideology.jcord.object.Guild;
import org.alienideology.jcord.object.User;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author AlienIdeology
 */
public class ReadyEvent extends GatewayEvent {

    public ReadyEvent(Identity identity, GatewayAdaptor gateway) {
        super(identity, gateway);
    }

    @Override
    public void handleEvent(JSONObject raw) {
        System.out.println("Ready:\n"+raw.toString(4));

        /* Create Guilds */
        // We use GuildCreateEvent to initialize guilds at the time.
        /*JSONArray guilds = raw.getJSONArray("guilds");
        System.out.println(guilds.length());
        for (int i = 0; i < guilds.length(); i++) {
            JSONObject guild = guilds.getJSONObject(i);

            try {
                JSONObject get = HttpPath.Guild.GET_GUILD.request(identity, guild.getString("id")).asJson().getBody().getObject();

                System.out.println(get.toString(4).split("\n")[0]);

                identity.addGuild(new Guild(identity, get));

            } catch (UnirestException ne) {
                identity.LOG.debug("Getting guilds", ne);
            }
        }*/

        /* Initialize Self User */
        identity.setSelf(new User(identity, raw.getJSONObject("user")));
    }
}
