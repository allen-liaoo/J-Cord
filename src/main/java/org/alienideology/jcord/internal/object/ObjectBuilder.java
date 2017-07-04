package org.alienideology.jcord.internal.object;

import org.alienideology.jcord.JCord;
import org.alienideology.jcord.event.ExceptionEvent;
import org.alienideology.jcord.handle.channel.IGuildChannel;
import org.alienideology.jcord.handle.guild.IGuildEmoji;
import org.alienideology.jcord.handle.guild.IRole;
import org.alienideology.jcord.handle.message.IReaction;
import org.alienideology.jcord.handle.permission.PermOverwrite;
import org.alienideology.jcord.handle.user.Game;
import org.alienideology.jcord.handle.user.OnlineStatus;
import org.alienideology.jcord.handle.user.Presence;
import org.alienideology.jcord.internal.exception.ErrorResponseException;
import org.alienideology.jcord.internal.exception.HttpErrorException;
import org.alienideology.jcord.internal.gateway.ErrorResponse;
import org.alienideology.jcord.internal.gateway.HttpPath;
import org.alienideology.jcord.internal.gateway.Requester;
import org.alienideology.jcord.internal.object.channel.MessageChannel;
import org.alienideology.jcord.internal.object.channel.PrivateChannel;
import org.alienideology.jcord.internal.object.channel.TextChannel;
import org.alienideology.jcord.internal.object.channel.VoiceChannel;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.alienideology.jcord.internal.object.guild.GuildEmoji;
import org.alienideology.jcord.internal.object.guild.Member;
import org.alienideology.jcord.internal.object.guild.Role;
import org.alienideology.jcord.internal.object.message.Embed;
import org.alienideology.jcord.internal.object.message.Message;
import org.alienideology.jcord.internal.object.message.Reaction;
import org.alienideology.jcord.internal.object.user.User;
import org.alienideology.jcord.internal.object.user.Webhook;
import org.alienideology.jcord.util.log.LogLevel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * A builder for building DiscordObjects
 *
 * @author AlienIdeology
 */
public final class ObjectBuilder {

    private IdentityImpl identity;

    /**
     * Default Constructor
     *
     * @param identity The identity this built DiscordObject belongs to.
     */
    public ObjectBuilder(IdentityImpl identity) {
        this.identity = identity;
    }

    /**
     * Build a guild object base on provided json.
     *
     * The guild will be added to the identity automatically.
     * @param json The guild JSONObject
     * @return The Guild object
     */
    public Guild buildGuild (JSONObject json) {
        handleBuildError(json);

        String id = json.getString("id");

        if (json.has("unavailable") && json.getBoolean("unavailable")) {
            Guild guild = new Guild(identity, id);
            identity.addGuild(guild);
            return guild;
        } else {
            /* Basic Information */
            String name = json.getString("name");
            String icon = json.isNull("icon") ? null : json.getString("icon");
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

            Guild guild = new Guild(identity, id, name, icon, splash, region, afk_timeout, embed_enabled, verification_level, notifications_level, mfa_level);

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
                buildEmoji(emojiJson, guild);
            }

            /* Build Channels */
            // LastMessage requires role field
            try {
                JSONArray guildChannels = new Requester(identity, HttpPath.Guild.GET_GUILD_CHANNELS).request(id).getAsJSONArray();

                for (int i = 0; i < guildChannels.length(); i++) {

                    JSONObject newChannel = guildChannels.getJSONObject(i);
                    IGuildChannel channel = buildGuildChannel(newChannel);

                    guild.addGuildChannel((IGuildChannel) channel);
                }

            } catch (RuntimeException e) {
                identity.LOG.log(LogLevel.FETAL, "Building guild channels. (Guild: "+guild.toString()+")", e);
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
                identity.LOG.log(LogLevel.FETAL,"Building guild members. (Guild: "+guild.toString()+")", e);
            }

            guild.setOwner(owner).setChannels(afk_channel, embed_channel);

            return guild;
        }
    }

    /**
     * Build a guild object by an id.
     *
     * @param id The id of the guild
     * @return The guild object
     */
    public Guild buildGuildById (String id) {
        JSONObject guild;
        try {
            guild = new Requester(identity, HttpPath.Guild.GET_GUILD).request(id).getAsJSONObject();
        } catch (RuntimeException e) {
            identity.LOG.log(LogLevel.FETAL, "Building Guild By ID (ID: "+id+")", e);
            throw new IllegalArgumentException("Invalid ID!");
        }
        return buildGuild(guild);
    }

    /**
     * Build a guild channel object base on provided json.
     *
     * @param json The IGuildChannel JSONObject
     * @return TextChannel or VoiceChannel, wrapped as a IGuildChannel
     */
    public IGuildChannel buildGuildChannel (JSONObject json) {
        handleBuildError(json);

        String guild_id = json.getString("guild_id");
        String id = json.getString("id");
        String name = json.getString("name");
        int position = json.getInt("position");
        String type = json.getString("type");

        /* Build PermOverwrite Objects */
        List<PermOverwrite> overwrites = new ArrayList<>();
        JSONArray perms = json.getJSONArray("permission_overwrites");

        for (int i = 0; i < perms.length(); i++) {
            JSONObject over = perms.getJSONObject(i);
            String typeId = over.getString("id");
            long allow = over.getLong("allow");
            long deny = over.getLong("deny");
            overwrites.add(new PermOverwrite(identity, guild_id, typeId, allow, deny));
        }

        if (type.equals("text")) {
            String topic = json.isNull("topic") ? null : json.getString("topic");
            String last_msg = !json.has("last_message_id") || json.isNull("last_message_id") ? null : json.getString("last_message_id");
            Message lastMessage = null;
            if (last_msg != null) {
                try {
                    lastMessage = buildMessageById(id, last_msg);
                } catch (HttpErrorException ignored) { }
            }
            TextChannel tc = new TextChannel(identity, guild_id, id, name, position, topic, lastMessage)
                    .setPermOverwrites(overwrites);
            if (lastMessage != null) lastMessage.setChannel(tc);
            return tc;
        } else {
            int bitrate = json.getInt("bitrate");
            int user_limit = json.getInt("user_limit");
            VoiceChannel vc = new VoiceChannel(identity, guild_id, id, name, position, bitrate, user_limit)
                    .setPermOverwrites(overwrites);
            return vc;
        }
    }

    /**
     * Build a guild channel object by an id.
     *
     * @param id The id of the channel
     * @return The IGuildChannel object
     */
    public IGuildChannel buildGuildChannelById (String id) {
        JSONObject gChannel;
        try {
            gChannel = new Requester(identity, HttpPath.Channel.GET_CHANNEL).request(id).getAsJSONObject();
        } catch (RuntimeException e) {
            identity.LOG.log(LogLevel.FETAL, "Building IGuildChannel By ID (ID: "+id+")", e);
            throw new IllegalArgumentException("Invalid ID!");
        }
        return buildGuildChannel(gChannel);
    }

    /**
     * Build a private channel object base on provided json.
     *
     * @param json The PrivateChannel JSONObject
     * @return The PrivateChannel object
     */
    public PrivateChannel buildPrivateChannel (JSONObject json) {
        handleBuildError(json);

        String id = json.getString("id");
        User recipient = buildUser(json.getJSONObject("recipient"));
        String last_msg = !json.has("last_message_id") || json.isNull("last_message_id") ? null : json.getString("last_message_id");
        Message lastMessage = null;
        if (last_msg != null) {
            try {
                lastMessage = buildMessageById(id, last_msg);
            } catch (HttpErrorException ignored) { }
        }

        PrivateChannel dm = new PrivateChannel(identity, id, recipient, lastMessage);
        identity.addPrivateChannel(dm);
        if (lastMessage != null) lastMessage.setChannel(dm);
        return dm;
    }

    /**
     * Build a user object base on provided json.
     *
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
     * Build a webhook base on provided json.
     *
     * @param json The json of this webhook user object.
     * @return The Webhook object
     */
    public Webhook buildWebhook(JSONObject json) {
        String id = json.getString("id");
        Webhook webhook = new Webhook(identity, id);

        String guild_id = json.getString("guild_id");
        String channel_id = json.getString("channel_id");

        String name = json.getString("name");
        String avatar = json.getString("avatar");
        String token = json.getString("token");

        /* Owner */
        if (json.has("user")) {
            webhook.setOwner(buildUser(json.getJSONObject("user")));
        }

        /* User */
        JSONObject user = new JSONObject()
                .put("username", name)
                .put("discriminator", "0000")
                .put("webhook_id", id) // Makes IUser#isWebhook == true
                .put("avatar", avatar)
                .put("bot", true);
        webhook.setUser(buildUser(user));

        webhook.setGuild(identity.getGuild(guild_id))
            .setChannel(identity.getTextChannel(channel_id))
            .setDefaultName(name)
            .setDefaultAvatar(avatar)
            .setToken(token);
        return webhook;
    }

    /**
     * Build a webhook by an id.
     *
     * @param id The id of the webhook.
     * @return The webhook built.
     */
    public Webhook buildWebhookById(String id) {
        JSONObject wh;
        try {
            wh = new Requester(identity, HttpPath.Webhook.GET_WEBHOOK).request(id).getAsJSONObject();
        } catch (RuntimeException e) {
            identity.LOG.log(LogLevel.FETAL, "Building Webhook By ID (ID: "+id+")", e);
            throw new IllegalArgumentException("Invalid ID!");
        }
        return buildWebhook(wh);
    }

    /**
     * Build a member object base on provided json.
     *
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

        List<IRole> memberRoles = new ArrayList<>();

        if (!json.isNull("roles")) {
            JSONArray roles = json.getJSONArray("roles");
            for (int i = 0; i < roles.length(); i++) {
                String roleId = roles.getString(i);
                Role newRole = (Role) guild.getRole(roleId);
                if (newRole != null) memberRoles.add(newRole);
            }
            memberRoles.add(guild.getEveryoneRole());
        }

        return new Member(identity, guild, user, nick, joined_at, memberRoles, isDeaf, isMute);
    }

    /**
     * Build a member object base on provided json.
     *
     * @param json The member JSONObject
     * @return The Member object
     */
    public Member buildMemberById (JSONObject json, String guild_id) {
        Guild guild = (Guild) identity.getGuild(guild_id);
        return buildMember(json, guild);
    }

    /**
     * Build a message object base on provided json.
     *
     * @param json The message JSONObject
     * @return The Message object
     */
    public Message buildMessage (JSONObject json) {
        handleBuildError(json);

        String id = json.getString("id");
        String channel_id = json.getString("channel_id");

        /* Build User (Can be Webhook) */
        User author = json.has("webhook_id") && !json.isNull("webhook_id") ?
                buildUser(json.getJSONObject("author").put("webhook_id", json.getString("webhook_id"))) :
                buildUser(json.getJSONObject("author"));

        String content = json.getString("content");
        String timeStamp = json.getString("timestamp");

        /* Mentioned User */
        List<User> mentions = new ArrayList<>();
        JSONArray mentionUser = json.getJSONArray("mentions");
        for (int i = 0; i < mentionUser.length(); i++) {
            mentions.add((User)identity.getUser(mentionUser.getJSONObject(i).getString("id")));
        }

        /* Mentioned Role (TextChannel only) */
        List<Role> mentionsRole = new ArrayList<>();
        JSONArray mentionRole = json.getJSONArray("mention_roles");
        for (int i = 0; i < mentionRole.length(); i++) {
            mentionsRole.add((Role) identity.getRole(mentionRole.getString(i)));
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

        /* Booleans */
        boolean isTTS = json.getBoolean("tts");
        boolean mentionedEveryone = json.getBoolean("mention_everyone");
        boolean isPinned = json.has("pinned") && json.getBoolean("pinned");

        Message message =  new Message(identity, id, author, content, timeStamp, mentions, mentionsRole, attachments, isTTS, mentionedEveryone, isPinned);

        /* Channel */
        message.setChannel((MessageChannel) identity.getMessageChannel(channel_id));  // Set channel may be null for MessageChannel's LastMessage

        /* Embeds */
        JSONArray embeds = json.getJSONArray("embeds");
        for (int i = 0; i < embeds.length(); i++) {
            JSONObject embed = embeds.getJSONObject(i);

            String title = embed.has("title") ? embed.getString("title") : null;
            String description = embed.has("description") ? embed.getString("description") : null;
            String embed_url = embed.has("embed_url") ? embed.getString("embed_url") : null;
            String time_stamp = embed.has("timestamp") ? embed.getString("timestamp") : null;
            int color = embed.has("color") ? embed.getInt("color") : 0;

            Embed embedMessage = new Embed()
                    .setTitle(title)
                    .setDescription(description)
                    .setUrl(embed_url)
                    .setTimeStamp(time_stamp == null ? null : OffsetDateTime.parse(time_stamp))
                    .setColor(new Color(color));

            if (embed.has("author")) {
                JSONObject emAuthor = embed.getJSONObject("author");

                String name = emAuthor.has("name") ? emAuthor.getString("name") : null;
                String url = emAuthor.has("embed_url") ? emAuthor.getString("embed_url") : null;
                String icon_url = emAuthor.has("icon_url") ? emAuthor.getString("icon_url") : null;
                String proxy_url = emAuthor.has("proxy_icon_url") ? emAuthor.getString("proxy_icon_url") : null;
                embedMessage.setAuthor(new Embed.Author(name, url, icon_url, proxy_url));
            }

            if (embed.has("fields")) {
                JSONArray fields = embed.getJSONArray("fields");

                for (int j = 0; j < fields.length(); j++) {
                    JSONObject field = fields.getJSONObject(j);

                    String name = field.getString("name");
                    String value = field.getString("value");
                    boolean inline = field.getBoolean("inline");
                    embedMessage.addFields(new Embed.Field(name, value, inline));
                }
            }

            if (embed.has("thumbnail")) {
                JSONObject thumbnail = embed.getJSONObject("thumbnail");
                String url = thumbnail.getString("url");
                String proxy_url = thumbnail.getString("proxy_url");
                int height = thumbnail.getInt("height");
                int width = thumbnail.getInt("width");

                embedMessage.setThumbnail(new Embed.Thumbnail(url, proxy_url, height, width));
            }

            if (embed.has("video")) {
                JSONObject video = embed.getJSONObject("video");
                String url = video.isNull("url") ? null : video.getString("url");
                int height = video.getInt("height");
                int width = video.getInt("width");
                embedMessage.setVideo(new Embed.Video(url, height, width));
            }

            if (embed.has("provider")) {
                JSONObject provider = embed.getJSONObject("provider");
                String name = provider.getString("name");
                String url = provider.isNull("url") ? null : provider.getString("url");
                embedMessage.setProvider(new Embed.Provider(name, url));
            }

            if (embed.has("image")) {
                JSONObject image = embed.getJSONObject("image");
                String url = image.getString("url");
                String proxy_url = image.getString("proxy_url");
                int height = image.getInt("height");
                int width = image.getInt("width");
                embedMessage.setImage(new Embed.Image(url, proxy_url, height, width));
            }

            if (embed.has("footer")) {
                JSONObject footer = embed.getJSONObject("footer");

                String name = footer.has("name") ? footer.getString("name") : null;
                String icon_url = footer.has("icon_url") ? footer.getString("icon_url") : null;
                String proxy_url = footer.has("proxy_icon_url") ? footer.getString("proxy_icon_url") : null;
                embedMessage.setFooter(new Embed.Footer(name, icon_url, proxy_url));
            }

            message.addEmbed(embedMessage);
        }

        /* Reactions */
        // Build this at last because GuildEmoji requires Message#getGuild, which can only be called after setting channel
        List<IReaction> reactions = new ArrayList<>();
        if (json.has("reactions")) {
            JSONArray reacts = json.getJSONArray("reactions");

            for (int i = 0; i < reacts.length(); i++) {
                JSONObject react = reacts.getJSONObject(i);
                reactions.add(buildReaction(react, message));
            }
        }
        message.setReactions(reactions);

        return message;
    }

    /**
     * Build a message object by an id.
     *
     * @param channel_id The id of the channel
     * @param message_id The id of the message
     * @return The Message object
     */
    public Message buildMessageById (String channel_id, String message_id) {
        JSONObject message;
        message = new Requester(identity, HttpPath.Channel.GET_CHANNEL_MESSAGE).request(channel_id, message_id).getAsJSONObject();
        return buildMessage(message);
    }

    /**
     * Build a role object base on provided json.
     *
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
     *
     * @param json The emoji JSONObject
     * @param guild The guild this emoji is in
     * @return The GuildEmoji object
     */
    public GuildEmoji buildEmoji(JSONObject json, Guild guild) {
        handleBuildError(json);
        String id = json.getString("id");
        String name = json.getString("name");
        boolean requireColon = json.has("require_colons") & json.getBoolean("require_colons");

        List<Role> roles = new ArrayList<>();
        JSONArray rolesJson = json.getJSONArray("roles");
        for (int i = 0; i < rolesJson.length(); i++) {
            Role role = (Role) guild.getRole(rolesJson.getString(i));
            if (role != null) roles.add(role);
        }

        GuildEmoji emoji = new GuildEmoji(identity, guild, id, name, roles, requireColon);
        guild.addGuildEmoji(emoji);
        return emoji;
    }

    /**
     * Build a reaction by provided json.
     *
     * @param json The reaction json.
     * @param message The message.
     * @return The reaction built.
     */
    public Reaction buildReaction(JSONObject json, Message message) {
        int reactedTimes = json.has("count") ? json.getInt("count") : -1;
        boolean selfReacted = json.has("me") && json.getBoolean("me");

        Reaction reaction;
        JSONObject emojiJson = json.getJSONObject("emoji");

        /* Guild Emoji */
        if (emojiJson.has("id") && !emojiJson.isNull("id")) {
            IGuildEmoji emoji = message.getGuild().getGuildEmoji(emojiJson.getString("id"));
            if (emoji == null) { // Global Guilc Emoji
                reaction = new Reaction(identity, message, reactedTimes, selfReacted, new GuildEmoji(identity, emojiJson.getString("id"), emojiJson.getString("name")));
            } else { // Guild Emoji
                reaction = new Reaction(identity, message, reactedTimes, selfReacted, emoji);
            }

        /* Emoji */
        } else {
            reaction = new Reaction(identity, message, reactedTimes, selfReacted, JCord.EMOJI_TABLE.getByUnicode(emojiJson.getString("name")));
        }
        return reaction;
    }

    /**
     * Built an invite with provided json.
     *
     * @param json The json invite object. May not contains metadata.
     * @return The IInvite built.
     */
    public Invite buildInvite(JSONObject json) {
        String code = json.getString("code");
        Guild guild = (Guild) identity.getGuild(json.getJSONObject("guild").getString("id"));
        IGuildChannel channel = identity.getGuildChannel(json.getJSONObject("channel").getString("id"));
        Invite invite = new Invite(code, guild, channel);

        // If the invites has metadata object
        if (json.has("inviter")) {
            User inviter = (User) identity.getUser(json.getJSONObject("inviter").getString("id"));
            int uses = json.getInt("uses");
            int maxUses = json.getInt("max_uses");
            long maxAge = json.getLong("max_age");
            boolean isTemporary = json.has("temporary") && json.getBoolean("temporary");
            boolean isRevoked = json.has("revoked") && json.getBoolean("revoked");
            String timeStamp = json.getString("created_at");
            invite.setMetaData(inviter, uses, maxUses, maxAge, isTemporary, isRevoked, timeStamp);
        }
        return invite;
    }

    /**
     * Built an user presence with provided json.
     *
     * @param json The json presence object.
     * @return The presence built.
     */
    public Presence buildPresence(JSONObject json, User user) {
        //OnlineStatus
        OnlineStatus status = OnlineStatus.getByKey(json.getString("status"));

        // Game
        Game game = null;

        if (json.has("game") && !json.isNull("game")) {
            JSONObject gameJson = json.getJSONObject("game");
            String name = gameJson.isNull("name") ? null : gameJson.getString("name");
            String url = gameJson.has("type") && gameJson.getInt("type") == 1 ?
                    gameJson.getString("url") : null;
            game = new Game(identity, name, url);
            if (game.isStreaming()) status = OnlineStatus.STREAMING;
        }

        Presence presence = new Presence(identity, user, game, status);
        user.setPresence(presence);
        return presence;
    }

    /**
     * Handle Error Responses or Error Code
     * @param json The json to be check
     */
    private void handleBuildError (JSONObject json) {
        if (json.has("code")) {
            identity.getEventManager().dispatchEvent(new ExceptionEvent(identity,
                    new ErrorResponseException(ErrorResponse.getByKey(json.getInt("code")))));
        }
    }

}
