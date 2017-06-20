package org.alienideology.jcord.event.handler;

import org.alienideology.jcord.event.guild.emoji.GuildEmojiNameUpdateEvent;
import org.alienideology.jcord.event.guild.emoji.GuildEmojiRolesUpdateEvent;
import org.alienideology.jcord.event.guild.emoji.GuildEmojiUploadEvent;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.alienideology.jcord.internal.object.guild.GuildEmoji;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Objects;

/**
 * @author AlienIdeology
 */
public class GuildEmojisUpdateEventHandler extends EventHandler {

    public GuildEmojisUpdateEventHandler(IdentityImpl identity) {
        super(identity);
    }

    @Override
    public void dispatchEvent(JSONObject json, int sequence) {
        Guild guild = (Guild) identity.getGuild(json.getString("guild_id"));

        if (guild == null) {
            identity.LOG.fatal("Detected new guild that is not cached in a Guild Emojis Update Event!");
            return;
        }

        JSONArray emojis = json.getJSONArray("emojis");
        for (int i = 0; i < emojis.length(); i++) {
            JSONObject emojiJson = emojis.getJSONObject(i);

            GuildEmoji emoji = (GuildEmoji) guild.getGuildEmoji(emojiJson.getString("id"));
            GuildEmoji newEmoji = builder.buildEmoji(emojiJson, guild);

            if (emoji == null) { // New emoji
                fireEvent(new GuildEmojiUploadEvent(identity, guild, sequence, newEmoji)); // Emoji get added to the guild automatically
            } else {
                guild.removeGuildEmoji(emoji.getId());
                guild.addGuildEmoji(newEmoji); // Builder ignores the new emoji because the emoji list already contains the old one

                if (!Objects.equals(emoji.getName(), newEmoji.getName())) {
                    fireEvent(new GuildEmojiNameUpdateEvent(identity, guild, sequence, newEmoji, emoji));
                }

                if (!Objects.equals(emoji.getUsableRoles(), emoji.getUsableRoles())) {
                    fireEvent(new GuildEmojiRolesUpdateEvent(identity, guild, sequence, newEmoji, emoji));
                }
            }
        }

    }

}
