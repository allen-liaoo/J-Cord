package org.alienideology.jcord.event.handler;

import org.alienideology.jcord.event.user.WebhookUpdateEvent;
import org.alienideology.jcord.handle.channel.ITextChannel;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.json.JSONObject;

/**
 * @author AlienIdeology
 */
public class WebhookUpdateEventHandler extends EventHandler {

    public WebhookUpdateEventHandler(IdentityImpl identity) {
        super(identity);
    }

    @Override
    public void dispatchEvent(JSONObject json, int sequence) {
        IGuild guild = identity.getGuild(json.getString("guild_id"));
        ITextChannel channel = identity.getTextChannel(json.getString("channel_id"));
        dispatchEvent(new WebhookUpdateEvent(identity, sequence, guild, channel));
    }

}
