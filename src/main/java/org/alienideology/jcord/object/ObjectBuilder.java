package org.alienideology.jcord.object;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.object.channel.GuildChannel;
import org.alienideology.jcord.object.channel.PrivateChannel;
import org.alienideology.jcord.object.channel.TextChannel;
import org.alienideology.jcord.object.channel.VoiceChannel;
import org.alienideology.jcord.object.guild.Guild;
import org.json.JSONObject;

/**
 * A builder for building DiscordObjects
 * @author AlienIdeology
 */
public class ObjectBuilder {

    private Identity identity;

    /**
     * Default Constructor
     * @param identity The identity this built DiscordObject belongs to.
     */
    public ObjectBuilder(Identity identity) {
        this.identity = identity;
    }

    /**
     * Build a guild object base on provided json.
     * @param json The guild JSONObject
     * @return The Guild object
     */
    public Guild buildGuild (JSONObject json) {
            String id = json.getString("id");

        if (json.has("unavailable") && json.getBoolean("unavailable")) {
            return new Guild(identity, id);
        } else {

            String name = json.getString("name");
            String icon = json.getString("icon");
            String splash = json.isNull("splash") ? null : json.getString("splash");
//            String owner = identity.getMember(json.getString("owner_id"));
            String region = json.getString("region");
            int afk_timeout = json.getInt("afk_timeout");
//            VoiceChannel afk_channel = json.isNull("afk_channel_id") ? null : identity.getVoiceChannel(json.getString("afk_channel_id"));
            boolean embed_enabled = json.has("embed_enabled") && json.getBoolean("embed_enabled");
//            GuildChannel embed_channel = json.has("embed_channel_id") ? : identity.getChannel(json.getString("embed_channel_id")) : null;
            int verification_level = json.getInt("verification_level");
            int notifications_level = json.getInt("default_message_notifications");
            int mfa_level = json.getInt("mfa_level");
//            roles = new ArrayList<Role>();
//            json.getJSONArray("roles").forEach(role -> roles.add(new Role(identity, role.toString())));
//            emojis = new ArrayList<Emote>();
//            json.getJSONArray("emojis").forEach(emoji -> emoji.add(new Role(identity, emoji.toString())));

            return new Guild(identity, id, name, icon, splash, region, afk_timeout, embed_enabled, verification_level, notifications_level, mfa_level);
//                .setAFKChannel(afk_channel).setEmbedChannel(embed_channel).addRoles(roles).addEmojis(emojis);
        }
    }

    /**
     * Build a guild channel object base on provided json.
     * @param json The GuildChannel JSONObject
     * @return TextChannel or VoiceChannel, wrapped as a GuildChannel
     */
    public GuildChannel buildGuildChannel (JSONObject json) {
        String guild_id = json.getString("guild_id");
        String id = json.getString("id");
        String name = json.getString("name");
        int position = json.getInt("position");
        String type = json.getString("type");

        switch (type) {
            case "text": {
                String topic = json.getString("topic");
                String last_msg = json.getString("last_message_id");
                return new TextChannel(identity, guild_id, id, name, position, topic, last_msg);
            }
            case "voice": {
                int bitrate = json.getInt("bitrate");
                int user_limit = json.getInt("user_limit");
                return new VoiceChannel(identity, guild_id, id, name, position, bitrate, user_limit);
            }
            default: {
                return new GuildChannel(identity, guild_id, id, Channel.Type.UNKNOWN, name, position);
            }
        }
    }

    /**
     * Build a private channel object base on provided json.
     * @param json The PrivateChannel JSONObject
     * @return The PrivateChannel object
     */
    public PrivateChannel buildPrivateChannel (JSONObject json) {
        String id = json.getString("id");
        User recipient = new ObjectBuilder(identity).buildUser(json.getJSONObject("recipient"));
        String last_msg = json.getString("last_message_id");
        return new PrivateChannel(identity, id, recipient, last_msg);
    }

    /**
     * Build a user object base on provided json.
     * @param json The user JSONObject
     * @return The User object
     */
    public User buildUser (JSONObject json) {
        String id = json.getString("id");
        String name = json.getString("username");
        String discriminator = json.getString("discriminator");
        String avatar = json.getString("avatar");
        String email = json.isNull("email") ? null : json.getString("email");
        boolean isBot = json.getBoolean("bot");
        boolean isVerified = json.getBoolean("verified");
        boolean MFAEnabled = json.getBoolean("mfa_enabled");
        return new User(identity, id, name, discriminator, avatar, email, isBot, isVerified, MFAEnabled);
    }
}
