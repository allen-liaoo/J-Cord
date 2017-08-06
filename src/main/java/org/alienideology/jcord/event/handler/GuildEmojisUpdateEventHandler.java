package org.alienideology.jcord.event.handler;

import org.alienideology.jcord.event.guild.emoji.GuildEmojiDeleteEvent;
import org.alienideology.jcord.event.guild.emoji.GuildEmojiUploadEvent;
import org.alienideology.jcord.event.guild.emoji.update.GuildEmojiNameUpdateEvent;
import org.alienideology.jcord.event.guild.emoji.update.GuildEmojiRequireColonUpdateEvent;
import org.alienideology.jcord.event.guild.emoji.update.GuildEmojiRolesUpdateEvent;
import org.alienideology.jcord.handle.guild.IGuildEmoji;
import org.alienideology.jcord.handle.guild.IRole;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.alienideology.jcord.internal.object.guild.GuildEmoji;
import org.alienideology.jcord.internal.object.guild.Role;
import org.alienideology.jcord.util.log.LogLevel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
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
            logger.log(LogLevel.FETAL, "[UNKNOWN GUILD] [GUILD_EMOJIS_UPDATE_EVENT]");
            return;
        }

        JSONArray emojis = json.getJSONArray("emojis");

        // Emoji Deleted
        // The Json will be updated, but the deleted guild emoji is still in the cache.
        for (IGuildEmoji emoji : guild.getGuildEmojis()) {
            boolean isDeleted = true;
            for (int i = 0; i < emojis.length(); i++) { // Iterates through the JSONArray, and check if the emoji is deleted
                if (emoji.getId().equals(emojis.getJSONObject(i).getString("id"))) { // Find a match, not deleted
                    isDeleted = false;
                    break;
                }
            }

            if (isDeleted) {
                dispatchEvent(new GuildEmojiDeleteEvent(identity, guild, sequence, (GuildEmoji) emoji, OffsetDateTime.now()));
                guild.removeGuildEmoji(emoji.getId());
            }
        }

        for (int i = 0; i < emojis.length(); i++) {
            JSONObject emojiJson = emojis.getJSONObject(i);

            GuildEmoji emoji = (GuildEmoji) guild.getGuildEmoji(emojiJson.getString("id"));

            String name = emojiJson.getString("name");
            boolean requireColon = json.has("require_colons") & json.getBoolean("require_colons");

            List<Role> roles = new ArrayList<>();
            JSONArray rolesJson = json.getJSONArray("roles");
            for (int j = 0; j < rolesJson.length(); j++) {
                Role role = (Role) guild.getRole(rolesJson.getString(j));
                if (role != null) roles.add(role);
            }

            if (emoji == null) { // New emoji, not cached yet
                dispatchEvent(new GuildEmojiUploadEvent(identity, guild, sequence, builder.buildEmoji(emojiJson, guild))); // Emoji get added to the guild automatically
            } else {
                if (!Objects.equals(emoji.getName(), name)) {
                    String oldName = emoji.getName();
                    emoji.setName(name);
                    dispatchEvent(new GuildEmojiNameUpdateEvent(identity, guild, sequence, emoji, oldName));
                }

                if (!Objects.equals(emoji.getUsableRoles(), emoji.getUsableRoles())) {
                    List<IRole> oldRoles = emoji.getUsableRoles();
                    emoji.setRoles(roles);
                    dispatchEvent(new GuildEmojiRolesUpdateEvent(identity, guild, sequence, emoji, oldRoles));
                }

                if (requireColon != emoji.isRequireColon()) {
                    emoji.setRequireColon(requireColon);
                    dispatchEvent(new GuildEmojiRequireColonUpdateEvent(identity, guild, sequence, emoji));
                }

            }
        }

    }

}
