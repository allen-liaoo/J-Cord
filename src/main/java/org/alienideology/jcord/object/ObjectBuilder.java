package org.alienideology.jcord.object;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.alienideology.jcord.Identity;
import org.alienideology.jcord.event.DispatcherAdaptor;
import org.alienideology.jcord.exception.ErrorResponseException;
import org.alienideology.jcord.gateway.ErrorResponse;
import org.alienideology.jcord.gateway.HttpPath;
import org.alienideology.jcord.gateway.Requester;
import org.alienideology.jcord.object.channel.*;
import org.alienideology.jcord.object.guild.GuildEmoji;
import org.alienideology.jcord.object.guild.Member;
import org.alienideology.jcord.object.guild.Role;
import org.alienideology.jcord.object.message.EmbedMessage;
import org.alienideology.jcord.object.message.Reaction;
import org.alienideology.jcord.object.message.StringMessage;
import org.alienideology.jcord.object.user.User;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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
     * The guild will be added to the identity automatically.
     * @param json The guild JSONObject
     * @return The Guild object
     */
    public Guild buildGuild (JSONObject json) {
        handleBuildError(json);

        String id = json.getString("id");

        if (json.has("unavailable") && json.getBoolean("unavailable")) {
            return new Guild(identity, id);
        } else {
            /* Basic Information */
            String name = json.getString("name");
            String icon = json.getString("icon");
            String splash = json.isNull("splash") ? null : json.getString("splash");
            String owner = json.getString("owner_id");
            String region = json.getString("region");
            int afk_timeout = json.getInt("afk_timeout");
            String afk_channel = json.has("afk_channel_id") && !json.isNull("afk_channel_id") ? json.getString("afk_channel_id") : null;
            boolean embed_enabled = json.has("embed_enabled") && json.getBoolean("embed_enabled");
            String embed_channel = json.has("embed_channel_id") && !json.isNull("embed_channel_id") ? json.getString("embed_channel_id") : null;
            int verification_level = json.getInt("verification_level");
            int notifications_level = json.getInt("default_message_notifications");
            int mfa_level = json.getInt("mfa_level");
//            roles = new ArrayList<Role>();
//            json.getJSONArray("roles").forEach(role -> roles.add(new Role(identity, role.toString())));
//            emojis = new ArrayList<Emote>();
//            json.getJSONArray("emojis").forEach(emoji -> emoji.add(new Role(identity, emoji.toString())));

            Guild guild = new Guild(identity, id, name, icon, splash, region, afk_timeout, embed_enabled, verification_level, notifications_level, mfa_level);
//                .addRoles(roles).addEmojis(emojis);

            // Add guilds first because channels, roles, and members have a guild field
            identity.addGuild(guild);

            /* Build Roles */
            JSONArray roles = json.getJSONArray("roles");
            for (int i = 0; i < roles.length(); i++) {
                JSONObject roleJson = roles.getJSONObject(i);
                guild.addRole(buildRole(roleJson, guild));
            }

            /* Build GuildEmojis */
            // Build this after roles because emojis have roles field
            // Build this before channels because channel latest messages requires GuildEmoji
            JSONArray emojis = json.getJSONArray("emojis");
            for (int i = 0; i < emojis.length(); i++) {
                JSONObject emojiJson = emojis.getJSONObject(i);
                guild.addGuildEmoji(buildEmoji(emojiJson, guild));
            }

            /* Build Channels */
            // LastMessage requires role field
            try {
                JSONArray guildChannels = new Requester(identity, HttpPath.Guild.GET_GUILD_CHANNELS).request(id).getAsJSONArray();

                for (int i = 0; i < guildChannels.length(); i++) {

                    JSONObject newChannel = guildChannels.getJSONObject(i);
                    GuildChannel channel = buildGuildChannel(newChannel);

                    guild.addGuildChannel((GuildChannel) channel);
                }

            } catch (RuntimeException e) {
                identity.LOG.error("Building guild channels. (Guild: "+guild.toString()+")", e);
            }

            /* Build Members */
            // Build this after roles because members have roles field
            try {
                JSONArray members = new Requester(identity, HttpPath.Guild.LIST_GUILD_MEMBERS).request(id)
                        .updateGetRequest(r -> r.queryString("limit", "1000")).getAsJSONArray();
                for (int i = 0; i < members.length(); i++) {
                    JSONObject member = members.getJSONObject(i);
                    guild.addMember(buildMember(member, guild));
                }

            } catch (RuntimeException e) {
                identity.LOG.error("Building guild members. (Guild: "+guild.toString()+")", e);
            }

            guild.setOwner(owner).setChannels(afk_channel, embed_channel);

            return guild;
        }
    }

    /**
     * Build a guild object by an id.
     * @param id The id of the guild
     * @return The guild object
     */
    public Guild buildGuildById (String id) {
        JSONObject guild;
        try {
            guild = new Requester(identity, HttpPath.Guild.GET_GUILD).request(id).getAsJSONObject();
        } catch (RuntimeException e) {
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
        handleBuildError(json);

        String guild_id = json.getString("guild_id");
        String id = json.getString("id");
        String name = json.getString("name");
        int position = json.getInt("position");
        String type = json.getString("type");

        if (type.equals("text")) {
            String topic = json.isNull("topic") ? null : json.getString("topic");
            String last_msg = !json.has("last_message_id") || json.isNull("last_message_id") ? null : json.getString("last_message_id");
            Message lastMessage = last_msg == null ? null : buildMessageById(id, last_msg);
            TextChannel tc = new TextChannel(identity, guild_id, id, name, position, topic, lastMessage);
            identity.addTextChannel(tc);
            if (lastMessage != null) lastMessage.setChannel(id);
            return tc;
        } else {
            int bitrate = json.getInt("bitrate");
            int user_limit = json.getInt("user_limit");
            VoiceChannel vc = new VoiceChannel(identity, guild_id, id, name, position, bitrate, user_limit);
            identity.addVoiceChannel(vc);
            return vc;
        }
    }

    /**
     * Build a guild channel object by an id.
     * @param id The id of the channel
     * @return The GuildChannel object
     */
    public GuildChannel buildGuildChannelById (String id) {
        JSONObject gChannel;
        try {
            gChannel = new Requester(identity, HttpPath.Channel.GET_CHANNEL).request(id).getAsJSONObject();
        } catch (RuntimeException e) {
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
        handleBuildError(json);

        String id = json.getString("id");
        User recipient = buildUser(json.getJSONObject("recipient"));
        String last_msg = !json.has("last_message_id") || json.isNull("last_message_id") ? null : json.getString("last_message_id");
        Message lastMessage = last_msg == null ? null : buildMessageById(id, last_msg);

        PrivateChannel dm = new PrivateChannel(identity, id, recipient, lastMessage);
        identity.addPrivateChannel(dm);
        if (lastMessage != null) lastMessage.setChannel(id);
        return dm;
    }

    /**
     * Build a user object base on provided json.
     * @param json The user JSONObject
     * @return The User object
     */
    public User buildUser (JSONObject json) {
        handleBuildError(json);

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

        User user = new User(identity, id, name, discriminator, avatar, email, isBot, isWebHook, isVerified, MFAEnabled);
        identity.addUser(user);
        return user;
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
     * Build a member object base on provided json.
     * @param json The member JSONObject
     * @return The Member object
     */
    public Member buildMember (JSONObject json, Guild guild) {
        handleBuildError(json);
        String nick = !json.has("nick") || json.isNull("nick") ? null : json.getString("nick");
        String joined_at = json.getString("joined_at");
        boolean isDeaf = json.getBoolean("deaf");
        boolean isMute = json.getBoolean("mute");
        User user = buildUser(json.getJSONObject("user"));

        List<Role> memberRoles = new ArrayList<>();

        if (!json.isNull("roles")) {
            JSONArray roles = json.getJSONArray("roles");
            for (int i = 0; i < roles.length(); i++) {
                String roleId = roles.getString(i);
                Role newRole = guild.getRole(roleId);
                if (newRole != null) memberRoles.add(newRole);
            }
            memberRoles.add(guild.getEveryoneRole());
        }

        return new Member(identity, guild, user, nick, joined_at, memberRoles, isDeaf, isMute);
    }

    /**
     * Build a member object base on provided json.
     * @param json The member JSONObject
     * @return The Member object
     */
    public Member buildMemberById (JSONObject json, String guild_id) {
        Guild guild = identity.getGuild(guild_id);
        return buildMember(json, guild);
    }

    /**
     * Build a message object base on provided json.
     * @param json The message JSONObject
     * @return The Message object
     */
    // TODO: Add lists (See Message object)
    public Message buildMessage (JSONObject json) {
        handleBuildError(json);
        Message message;

        String id = json.getString("id");
        String channel_id = json.getString("channel_id");

        /* Build User (Can be Webhook) */
        User author = json.has("webhook_id") && !json.isNull("webhook_id") ?
                buildWebHook(json.getJSONObject("author"), json.getString("webhook_id")) :
                buildUser(json.getJSONObject("author"));

        String content = json.getString("content");
        String timeStamp = json.getString("timestamp");

        /* Mentioned User */
        List<User> mentions = new ArrayList<>();
        JSONArray mentionUser = json.getJSONArray("mentions");
        for (int i = 0; i < mentionUser.length(); i++) {
            mentions.add(identity.getUser(mentionUser.getJSONObject(i).getString("id")));
        }

        /* Mentioned Role (TextChannel only) */
        List<Role> mentionsRole = new ArrayList<>();
        JSONArray mentionRole = json.getJSONArray("mention_roles");
        for (int i = 0; i < mentionRole.length(); i++) {
            mentionsRole.add(identity.getRole(mentionRole.getString(i)));
        }

        /* Attachments */
        List<Message.Attachment> attachments = new ArrayList<>();
        JSONArray attachs = json.getJSONArray("attachments");
        for (int i = 0; i < attachs.length(); i++) {
            JSONObject attachment = attachs.getJSONObject(i);
            String attachmentId = attachment.getString("id");
            String filename = attachment.has("filename") && !attachment.isNull("filename") ?
                    attachment.getString("filename") : null;
            int size = attachment.has("size") ? attachment.getInt("size") : 0;
            String url = attachment.has("url") && !attachment.isNull("url") ?
                    attachment.getString("url") : null;
            attachments.add(new Message.Attachment(attachmentId, filename, size, url));
        }

        boolean isTTS = json.getBoolean("tts");
        boolean mentionedEveryone = json.getBoolean("mention_everyone");
        boolean isPinned = json.has("pinned") && json.getBoolean("pinned");

        /* StringMessage */
        if (!content.isEmpty() || json.getJSONArray("embeds").length() == 0) {
            message =  new StringMessage(identity, id, author, content, timeStamp, mentions, mentionsRole, attachments, isTTS, mentionedEveryone, isPinned);

        /* EmbedMessage */
        } else {

            JSONObject embed = json.getJSONArray("embeds").getJSONObject(0);

            String title = embed.has("title") ? embed.getString("title") : null;
            String description = embed.has("description") ? embed.getString("description") : null;
            String embed_url = embed.has("embed_url") ? embed.getString("embed_url") : null;
            String time_stamp = embed.has("timestamp") ? embed.getString("timestamp") : null;
            int color = embed.has("color") ? embed.getInt("color") : 0;

            EmbedMessage embedMessage =  new EmbedMessage(identity, id, author, content, timeStamp, mentions, mentionsRole, attachments, isTTS, mentionedEveryone, isPinned)
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
            message = embedMessage;
        }
        message.setChannel(channel_id);  // Set channel may be null for MessageChannel's LastMessage

        /* Reactions */
        // Build this at last because GuildEmoji requires Message#getGuild, which can only be called after setting channel
        List<Reaction> reactions = new ArrayList<>();
        if (json.has("reactions")) {
            boolean isFromGuild = message.fromType(Channel.Type.TEXT);
            EmojiList emojis = new EmojiList();
            JSONArray reacts = json.getJSONArray("reactions");

            for (int i = 0; i < reacts.length(); i++) {
                JSONObject react = reacts.getJSONObject(i);
                int reactedTimes = react.getInt("count");
                boolean selfReacted = react.has("me") && react.getBoolean("me");

                Reaction reaction;
                JSONObject emoji = react.getJSONObject("emoji");

                if (emoji.has("id") && !emoji.isNull("id")) {
                    if (isFromGuild && message.getGuild() != null) {  // From recognized guild
                        reaction = new Reaction(identity, reactedTimes, selfReacted, message.getGuild().getGuildEmoji(emoji.getString("id")));
                    } else {    // Global guild emoji
                        reaction = new Reaction(identity, reactedTimes, selfReacted, new GuildEmoji(identity, emoji.getString("id"), emoji.getString("name")));
                    }
                } else {
                    reaction = new Reaction(identity, reactedTimes, selfReacted, emojis.getByUnicode(emoji.getString("name")));
                }
                reactions.add(reaction);
            }
        }
        message.setReactions(reactions);

        return message;
    }

    /**
     * Build a message object by an id.
     * @param channel_id The id of the channel
     * @param message_id The id of the message
     * @return The Message object
     */
    public Message buildMessageById (String channel_id, String message_id) {
        JSONObject message;
        try {
            message = new Requester(identity, HttpPath.Channel.GET_CHANNEL_MESSAGE).request(channel_id, message_id).getAsJSONObject();
        } catch (RuntimeException e) {
            identity.LOG.error("Building Message By ID (ID: "+message_id+")", e);
            throw new IllegalArgumentException("Invalid ID!");
        }
        return buildMessage(message);
    }

    /**
     * Build a role object base on provided json.
     * @param json The role JSONObject
     * @param guild The guild this role is in
     * @return The Message object
     */
    public Role buildRole (JSONObject json, Guild guild) {
        handleBuildError(json);

        String id = json.getString("id");
        String name = json.getString("name");
        Color color = json.has("color") ? new Color(json.getInt("color")) : null;
        int position = json.getInt("position");
        long permissions = json.getLong("permissions");
        boolean isSeparateListed = json.has("hoist") && json.getBoolean("hoist");
        boolean canMention = json.has("mentionable")&& json.getBoolean("mentionable");

        return new Role(identity, guild, id, name, color, position, permissions, isSeparateListed, canMention);
    }

    /**
     * Build a GuildEmoji object base on provided json.
     * @param json The emoji JSONObject
     * @param guild The guild this emoji is in
     * @return The GuildEmoji object
     */
    public GuildEmoji buildEmoji (JSONObject json, Guild guild) {
        handleBuildError(json);
        String id = json.getString("id");
        String name = json.getString("name");
        boolean requireColon = json.has("require_colons") & json.getBoolean("require_colons");
        List<Role> roles = new ArrayList<>();

        JSONArray rolesJson = json.getJSONArray("roles");
        for (int i = 0; i < rolesJson.length(); i++) {
            Role role = guild.getRole(rolesJson.getString(i));
            if (role != null) roles.add(role);
        }
        return new GuildEmoji(identity, guild, id, name, roles, requireColon);
    }

    /**
     * Handle Error Responses or Error Code
     * @param json The json to be check
     */
    private void handleBuildError (JSONObject json) {
        if (json.has("code")) {
            identity.getDispatchers().forEach((DispatcherAdaptor dispatcher) ->
                    dispatcher.onException(new ErrorResponseException(ErrorResponse.getByKey(json.getInt("code")))));
        }
    }

}
