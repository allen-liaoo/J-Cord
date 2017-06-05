package org.alienideology.jcord.object;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.alienideology.jcord.Identity;
import org.alienideology.jcord.gateway.HttpPath;
import org.alienideology.jcord.object.channel.*;
import org.alienideology.jcord.object.guild.Guild;
import org.alienideology.jcord.object.message.EmbedMessage;
import org.alienideology.jcord.object.message.Message;
import org.alienideology.jcord.object.message.StringMessage;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * A builder for building DiscordObjects
 * @author AlienIdeology
 */
public final class ObjectBuilder {

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
            /* Basic Information */
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

            Guild guild = new Guild(identity, id, name, icon, splash, region, afk_timeout, embed_enabled, verification_level, notifications_level, mfa_level);
//                .setAFKChannel(afk_channel).setEmbedChannel(embed_channel).addRoles(roles).addEmojis(emojis);

            /* Build Channels */
            try {
                JSONArray guildChannels = HttpPath.Guild.GET_GUILD_CHANNELS.request(identity, id).asJson().getBody().getArray();

                for (int i = 0; i < guildChannels.length(); i++) {

                    JSONObject newChannel = guildChannels.getJSONObject(i);
                    GuildChannel channel = buildGuildChannel(newChannel);

                    if (!channel.getChannelType().equals(Channel.Type.UNKNOWN)) {
                        guild.addGuildChannel(channel);
                    } else {
                        throw new RuntimeException("Unknown channel type!!");
                    }
                }

            } catch (UnirestException e) {
                identity.LOG.error("Building guild channels. (Guild: "+guild.toString()+")", e);
            }

            return guild;
        }
    }

    /**
     * Build a guild object just by an id.
     * @param id The id of the guild
     * @return The guild object
     */
    public Guild buildGuildById (String id) {
        JSONObject guild = null;
        try {
            guild = HttpPath.Guild.GET_GUILD.request(identity, id).asJson().getBody().getObject();
        } catch (UnirestException e) {
            identity.LOG.error("Building Guild By ID (ID: "+id+")", e);
            throw new IllegalArgumentException("Invalid ID!");
        }
        return buildGuild(guild);
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
                String topic = json.isNull("topic") ? null : json.getString("topic");
                String last_msg = json.isNull("last_message_id") ? null : json.getString("last_message_id");
                Message lastMessage = last_msg == null ? null : buildMessageById(id, last_msg);
                return new TextChannel(identity, guild_id, id, name, position, topic)
                        .setLastMessage(lastMessage);
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
     * Build a guild channel object just by an id.
     * @param id The id of the channel
     * @return The GuildChannel object
     */
    public GuildChannel buildGuildChannelById (String id) {
        JSONObject gChannel = null;
        try {
            gChannel = HttpPath.Channel.GET_CHANNEL.request(identity, id).asJson().getBody().getObject();
        } catch (UnirestException e) {
            identity.LOG.error("Building GuildChannel By ID (ID: "+id+")", e);
            throw new IllegalArgumentException("Invalid ID!");
        }
        return buildGuildChannel(gChannel);
    }

    /**
     * Build a private channel object base on provided json.
     * @param json The PrivateChannel JSONObject
     * @return The PrivateChannel object
     */
    public PrivateChannel buildPrivateChannel (JSONObject json) {
        String id = json.getString("id");
        User recipient = buildUser(json.getJSONObject("recipient"));
        String last_msg = json.isNull("last_message_id") ? null : json.getString("last_message_id");
        Message lastMessage = last_msg == null ? null : buildMessageById(id, last_msg);

        return new PrivateChannel(identity, id, recipient)
                .setLastMessage(lastMessage);
    }

    /**
     * Build a user object base on provided json.
     * @param json The user JSONObject
     * @return The User object
     */
    public User buildUser (JSONObject json) {

        /* Basic Information */
        String id = json.has("webhook_id") ? json.getString("webhook_id") : json.getString("id");
        String name = json.getString("username");
        String discriminator = json.getString("discriminator");

        /* Require Email OAuth2 */
        String avatar = json.has("avatar") && !json.isNull("avatar") ? json.getString("avatar") : null;
        String email = json.has("email") && !json.isNull("email") ? json.getString("email") : null;

        /* Boolean Information */
        boolean isBot = json.has("bot") && json.getBoolean("bot");
        boolean isWebHook = json.has("webhook_id");
        boolean isVerified = json.has("verified") && json.getBoolean("verified");
        boolean MFAEnabled = json.has("mfa_enabled") && json.getBoolean("mfa_enabled");

        return new User(identity, id, name, discriminator, avatar, email, isBot, isWebHook, isVerified, MFAEnabled);
    }

    /**
     * Build a user object (webhook) base on provided json.
     * @param json The json of this webhook user object (Does not contains webhook id)
     * @param webhookId The webhook id
     * @return The User(Webhook) object
     */
    public User buildWebHook (JSONObject json, String webhookId) {
        return buildUser(json.put("webhook_id", webhookId));
    }

    /**
     * Build a message object base on provided json.
     * @param json The message JSONObject
     * @return The Message object
     */
    // TODO: Add lists (See Message object)
    public Message buildMessage (JSONObject json) {
        String id = json.getString("id");

        /* Build User (Can be Webhook) */
        User author = json.has("webhook_id") && !json.isNull("webhook_id") ?
                buildWebHook(json.getJSONObject("author"), json.getString("webhook_id")) :
                buildUser(json.getJSONObject("author"));

        String timeStamp = json.getString("timestamp");
        boolean isTTS = json.getBoolean("tts");
        boolean mentionedEveryone = json.getBoolean("mention_everyone");
        boolean isPinned = json.has("pinned") && json.getBoolean("pinned");

        /* StringMessage */
        if (!json.getString("content").isEmpty()) {
            String content = json.getString("content");
            return new StringMessage(identity, id, author, timeStamp, isTTS, mentionedEveryone, isPinned, content);

        /* EmbedMessage */
        } else {

            JSONObject embed = json.getJSONArray("embeds").getJSONObject(0);

            String title = embed.has("title") ? embed.getString("title") : null;
            String description = embed.has("description") ? embed.getString("description") : null;
            String embed_url = embed.has("embed_url") ? embed.getString("embed_url") : null;
            String time_stamp = embed.has("timestamp") ? embed.getString("timestamp") : null;
            int color = embed.has("color") ? embed.getInt("color") : 0;

            EmbedMessage embedMessage =  new EmbedMessage(identity, id, author, timeStamp, isTTS, mentionedEveryone, isPinned)
                    .setEmbed(title, description, embed_url, time_stamp, color);

            if (embed.has("author")) {
                JSONObject emAuthor = embed.getJSONObject("author");

                String name = emAuthor.has("name") ? emAuthor.getString("name") : null;
                String url = emAuthor.has("embed_url") ? emAuthor.getString("embed_url") : null;
                String icon_url = emAuthor.has("icon_url") ? emAuthor.getString("icon_url") : null;
                String proxy_url = emAuthor.has("proxy_icon_url") ? emAuthor.getString("proxy_icon_url") : null;
                embedMessage.setAuthor(new EmbedMessage.Author(name, url, icon_url, proxy_url));
            }

            if (embed.has("fields")) {
                JSONArray fields = embed.getJSONArray("fields");

                for (int i = 0; i < fields.length(); i++) {
                    JSONObject field = fields.getJSONObject(i);

                    String name = field.getString("name");
                    String value = field.getString("value");
                    boolean inline = field.getBoolean("inline");
                    embedMessage.addFields(new EmbedMessage.Field(name, value, inline));
                }
            }

            if (embed.has("thumbnail")) {
                JSONObject thumbnail = embed.getJSONObject("thumbnail");
                String url = thumbnail.getString("url");
                String proxy_url = thumbnail.getString("proxy_url");
                int height = thumbnail.getInt("height");
                int width = thumbnail.getInt("width");

                embedMessage.setThumbnail(new EmbedMessage.Thumbnail(url, proxy_url, height, width));
            }

            if (embed.has("video")) {
                JSONObject video = embed.getJSONObject("video");
                String url = video.getString("url");
                int height = video.getInt("height");
                int width = video.getInt("width");
                embedMessage.setVideo(new EmbedMessage.Video(url, height, width));
            }

            if (embed.has("provider")) {
                JSONObject video = embed.getJSONObject("provider");
                String name = video.getString("name");
                String url = video.getString("url");
                embedMessage.setProvider(new EmbedMessage.Provider(name, url));
            }

            if (embed.has("image")) {
                JSONObject image = embed.getJSONObject("image");
                String url = image.getString("url");
                String proxy_url = image.getString("proxy_url");
                int height = image.getInt("height");
                int width = image.getInt("width");
                embedMessage.setImage(new EmbedMessage.Image(url, proxy_url , height, width));
            }

            if (embed.has("footer")) {
                JSONObject footer = embed.getJSONObject("footer");

                String name = footer.has("name") ? footer.getString("name") : null;
                String icon_url = footer.has("icon_url") ? footer.getString("icon_url") : null;
                String proxy_url = footer.has("proxy_icon_url") ? footer.getString("proxy_icon_url") : null;
                embedMessage.setFooter(new EmbedMessage.Footer(name, icon_url, proxy_url));
            }

            return embedMessage;
        }
    }

    /**
     * Build a message object just by an id.
     * @param channel_id The id of the channel
     * @param message_id The id of the message
     * @return The Message object
     */
    public Message buildMessageById (String channel_id, String message_id) {
        JSONObject message = null;
        try {
            message = HttpPath.Channel.GET_CHANNEL_MESSAGE.request(identity, channel_id, message_id).asJson().getBody().getObject();
        } catch (UnirestException e) {
            identity.LOG.error("Building Message By ID (ID: "+message_id+")", e);
            throw new IllegalArgumentException("Invalid ID!");
        }
        return buildMessage(message);
    }

}
