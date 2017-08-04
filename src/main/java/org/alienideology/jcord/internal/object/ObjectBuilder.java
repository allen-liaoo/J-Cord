package org.alienideology.jcord.internal.object;

import org.alienideology.jcord.JCord;
import org.alienideology.jcord.event.ExceptionEvent;
import org.alienideology.jcord.handle.Region;
import org.alienideology.jcord.handle.audit.*;
import org.alienideology.jcord.handle.channel.*;
import org.alienideology.jcord.handle.client.relation.IRelationship;
import org.alienideology.jcord.handle.client.setting.MessageNotification;
import org.alienideology.jcord.handle.emoji.Emojis;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.guild.IGuildEmoji;
import org.alienideology.jcord.handle.guild.IIntegration;
import org.alienideology.jcord.handle.guild.IRole;
import org.alienideology.jcord.handle.message.IReaction;
import org.alienideology.jcord.handle.oauth.Scope;
import org.alienideology.jcord.handle.permission.PermOverwrite;
import org.alienideology.jcord.handle.user.IConnection;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.handle.user.OnlineStatus;
import org.alienideology.jcord.internal.exception.ErrorResponseException;
import org.alienideology.jcord.internal.gateway.ErrorResponse;
import org.alienideology.jcord.internal.gateway.HttpPath;
import org.alienideology.jcord.internal.gateway.Requester;
import org.alienideology.jcord.internal.object.audit.AuditLog;
import org.alienideology.jcord.internal.object.audit.LogChange;
import org.alienideology.jcord.internal.object.audit.LogEntry;
import org.alienideology.jcord.internal.object.bot.BotApplication;
import org.alienideology.jcord.internal.object.channel.*;
import org.alienideology.jcord.internal.object.client.Client;
import org.alienideology.jcord.internal.object.client.Note;
import org.alienideology.jcord.internal.object.client.Profile;
import org.alienideology.jcord.internal.object.client.app.Application;
import org.alienideology.jcord.internal.object.client.app.AuthApplication;
import org.alienideology.jcord.internal.object.client.call.Call;
import org.alienideology.jcord.internal.object.client.relation.BlockedUser;
import org.alienideology.jcord.internal.object.client.relation.Friend;
import org.alienideology.jcord.internal.object.client.relation.Relationship;
import org.alienideology.jcord.internal.object.client.setting.ChannelSetting;
import org.alienideology.jcord.internal.object.client.setting.ClientSetting;
import org.alienideology.jcord.internal.object.client.setting.GuildSetting;
import org.alienideology.jcord.internal.object.guild.*;
import org.alienideology.jcord.internal.object.message.Embed;
import org.alienideology.jcord.internal.object.message.Message;
import org.alienideology.jcord.internal.object.message.Reaction;
import org.alienideology.jcord.internal.object.user.*;
import org.alienideology.jcord.util.log.LogLevel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.List;

/**
 * ObjectBuilder - A builder for building Discord objects from json.
 *
 * @author AlienIdeology
 */
public final class ObjectBuilder {

    private IdentityImpl identity;

    // Client Only
    private Client client;

    public ObjectBuilder(IdentityImpl identity) {
        this.identity = identity;
    }

    public ObjectBuilder(Client client) {
        this((IdentityImpl) client.getIdentity());
        this.client = client;
    }

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
            int ecf_level = json.getInt("explicit_content_filter");
            int mfa_level = json.getInt("mfa_level");

            Guild guild = new Guild(identity, id, name, icon, splash, region, afk_timeout, embed_enabled, verification_level, notifications_level, ecf_level, mfa_level);

            // Add guilds first because channels, roles, and members have a guild field
            identity.addGuild(guild);

            /* Build Roles */
            JSONArray roles = json.getJSONArray("roles");
            for (int i = 0; i < roles.length(); i++) {
                JSONObject roleJson = roles.getJSONObject(i);
                guild.addRole(buildRole(roleJson, guild));
            }

            /* Build GuildEmojis */
            // Build this after roles because EMOJIS have roles field
            // Build this before channels because channel latest messages requires GuildEmoji
            JSONArray emojis = json.getJSONArray("emojis");
            for (int i = 0; i < emojis.length(); i++) {
                JSONObject emojiJson = emojis.getJSONObject(i);
                buildEmoji(emojiJson, guild);
            }

            /* Build Channels */
            // Channels array are only present at Client Ready Event or Guild Create Event.
            // LastMessage requires role field
            JSONArray guildChannels = null;
            if (json.has("channels")) {
                guildChannels = json.getJSONArray("channels");
            } else {
                try {
                    guildChannels = new Requester(identity, HttpPath.Guild.GET_GUILD_CHANNELS).request(id).getAsJSONArray();
                } catch (RuntimeException e) {
                    identity.LOG.log(LogLevel.FETAL, "Building guild channels. (Guild: " + guild.toString() + ")", e);
                }
            }

            for (int i = 0; i < guildChannels.length(); i++) {

                JSONObject newChannel = guildChannels.getJSONObject(i);
                IGuildChannel channel = buildGuildChannel(guild, newChannel); // Sometimes "guild_id" is not present

                guild.addGuildChannel((IGuildChannel) channel);
            }

            guild.setOwner(owner).setChannels(afk_channel, embed_channel);

            /* Build Members */
            // Members array are only present at Client Ready Event or Guild Create Event.
            // Build this after roles because members have roles field
            JSONArray members;
            if (json.has("members")) {
                members = json.getJSONArray("members");
            } else {
                try {
                    members = new Requester(identity, HttpPath.Guild.LIST_GUILD_MEMBERS).request(id)
                            .updateGetRequest(r -> r.queryString("limit", "1000")).getAsJSONArray();
                } catch (RuntimeException e) {
                    identity.LOG.log(LogLevel.FETAL,"Building guild members. (Guild: "+guild.toString()+")", e);
                    return guild;
                }
            }

            // Request guild members after the guild and members are built
            // But I don't think members' length will be larger than threshold
            // Since discord only send online members for large guild
            if (members.length() > JCord.GUILD_MEMBERS_LARGE_THRESHOLD) { // Need to request guild members
                identity.getGateway().sendRequestMembers(json.getString("id"));
            }

            for (int i = 0; i < members.length(); i++) {
                JSONObject member = members.getJSONObject(i);
                guild.addMember(buildMember(member, guild));
            }

            return guild;
        }
    }

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

    // Build guild channel when the key "guild_id" is present.
    // Otherwise use provided guild instance.
    public IGuildChannel buildGuildChannel (IGuild guild, JSONObject json) {
        handleBuildError(json);

        String guild_id = json.has("guild_id") ? json.getString("guild_id") : guild.getId();
        String id = json.getString("id");
        String name = json.getString("name");
        int position = json.getInt("position");
        IChannel.Type type = IChannel.Type.getByKey(json.getInt("type"));

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

        if (type.equals(IChannel.Type.GUILD_TEXT)) {
            String topic = json.isNull("topic") ? null : json.getString("topic");
            return new TextChannel(identity, guild_id, id, name, position, topic)
                    .setPermOverwrites(overwrites);
        } else {
            int bitrate = json.getInt("bitrate");
            int user_limit = json.getInt("user_limit");
            return new VoiceChannel(identity, guild_id, id, name, position, bitrate, user_limit)
                    .setPermOverwrites(overwrites);
        }
    }

    // Build guild, assuming "guild_id" is present
    public IGuildChannel buildGuildChannel (JSONObject json) {
        return buildGuildChannel(null, json);
    }

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

    public PrivateChannel buildPrivateChannel (JSONObject json) {
        handleBuildError(json);

        String id = json.getString("id");
        JSONArray recipients = json.getJSONArray("recipients");
        if (recipients.length() > 1) {
            throw new RuntimeException("Cannot build a Group as a Private Channel!");
        }
        User recipient = buildUser(recipients.getJSONObject(0));

        PrivateChannel dm = new PrivateChannel(identity, id, recipient);
        identity.addPrivateChannel(dm);
        return dm;
    }

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

    public Webhook buildWebhook(JSONObject json) {
        String id = json.getString("id");
        Webhook webhook = new Webhook(identity, id);

        String guild_id = json.getString("guild_id");
        String channel_id = json.getString("channel_id");

        String name = json.has("name") && !json.isNull("name") ? json.getString("name") : null;
        String avatar = json.has("avatar") && !json.isNull("avatar") ? json.getString("avatar") : null;
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

    public Member buildMember (JSONObject json, Guild guild) {
        handleBuildError(json);
        String nick = !json.has("nick") || json.isNull("nick") ? null : json.getString("nick");
        String joined_at = json.getString("joined_at");
        boolean muted = json.getBoolean("mute");
        boolean deafened = json.getBoolean("deaf");
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

        return new Member(identity, guild, user, nick, joined_at, memberRoles, muted, deafened);
    }

    public Member buildMemberById (JSONObject json, String guild_id) {
        Guild guild = (Guild) identity.getGuild(guild_id);
        return buildMember(json, guild);
    }

    public Message buildMessage (JSONObject json) {
        handleBuildError(json);

        String id = json.getString("id");
        String channel_id = json.getString("channel_id");

        /* Build User (Can be Webhook) */
        User author = json.has("webhook_id") && !json.isNull("webhook_id") ?
                buildUser(json.getJSONObject("author").put("webhook_id", json.getString("webhook_id"))) :
                buildUser(json.getJSONObject("author"));

        String content = json.getString("content");
        int type = json.getInt("type");
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

        Message message =  new Message(identity, id, author, content, type, timeStamp, mentions, mentionsRole, attachments, isTTS, mentionedEveryone, isPinned);

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

    public Message buildMessageById (String channel_id, String message_id) {
        JSONObject message;
        message = new Requester(identity, HttpPath.Channel.GET_CHANNEL_MESSAGE).request(channel_id, message_id).getAsJSONObject();
        return buildMessage(message);
    }

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

    public Reaction buildReaction(JSONObject json, Message message) {
        handleBuildError(json);
        int reactedTimes = json.has("count") ? json.getInt("count") : -1;
        boolean selfReacted = json.has("me") && json.getBoolean("me");

        Reaction reaction;
        JSONObject emojiJson = json.getJSONObject("emoji");

        /* Guild Emoji */
        if (emojiJson.has("id") && !emojiJson.isNull("id")) {
            IGuildEmoji emoji = message.getGuild().getGuildEmoji(emojiJson.getString("id"));
            if (emoji == null) { // Global Guild Emoji
                reaction = new Reaction(identity, message, reactedTimes, selfReacted, new GuildEmoji(identity, emojiJson.getString("id"), emojiJson.getString("name")));
            } else { // Guild Emoji
                reaction = new Reaction(identity, message, reactedTimes, selfReacted, emoji);
            }

        /* Emoji */
        } else {
            reaction = new Reaction(identity, message, reactedTimes, selfReacted, Emojis.getByUnicode(emojiJson.getString("name")));
        }
        return reaction;
    }

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

    public Presence buildPresence(JSONObject json, User user) {
        handleBuildError(json);
        /* OnlineStatus */
        OnlineStatus status = OnlineStatus.getByKey(json.getString("status"));

        /* Game */
        Game game = null;

        if (json.has("game") && !json.isNull("game")) {
            JSONObject gameJson = json.getJSONObject("game");
            String name = gameJson.isNull("name") ? null : gameJson.getString("name");
            String url = gameJson.has("type") && gameJson.getInt("type") == 1 ?
                    gameJson.getString("url") : null;
            game = new Game(identity, name, url);
            if (game.isStreaming()) status = OnlineStatus.STREAMING;
        }

        // Since
        Long since = json.has("since") || !json.isNull("since") ? json.getLong("since") : null;

        Presence presence = new Presence(identity, user, game, status, since);
        user.setPresence(presence);
        return presence;
    }

    public Integration buildIntegration(JSONObject json) {
        handleBuildError(json);

        String id = json.getString("id");
        String name = json.getString("name");
        IConnection.Type type = IConnection.Type.getByKey(json.getString("type"));
        String lastSynced = json.getString("synced_at");
        boolean enabled = json.has("enabled") && json.getBoolean("enabled");
        boolean syncing = json.has("syncing") && json.getBoolean("syncing");

        Integration integration = new Integration(identity, id, name, type, lastSynced, enabled, syncing);

        /* User */
        IUser user = identity.getUser(json.getString("user"));
        integration.setUser(user);

        /* Account */
        JSONObject acc = json.getJSONObject("account");
        integration.setAccount(new IIntegration.Account(acc.getString("id"), acc.getString("name")));

        /* Role */
        IRole role = identity.getRole(json.getString("role_id"));
        integration.setRole(role);

        return integration;
    }

    public Connection buildConnection(JSONObject json, IUser user) {
        String id = json.getString("id");
        String name = json.getString("name");
        IConnection.Type type = IConnection.Type.getByKey(json.getString("type"));

        boolean displayOnProfile = json.getInt("visibility") == 1;
        boolean friend_sync = json.has("friend_sync") && json.getBoolean("friend_sync");
        boolean verified = json.has("verified") && json.getBoolean("verified");
        boolean revoked = json.has("revoked") && json.getBoolean("revoked");

        JSONArray ints = json.getJSONArray("integrations");
        List<IIntegration> integrations = new ArrayList<>();
        for (int i = 0; i < ints.length(); i++) {
            integrations.add(buildIntegration(ints.getJSONObject(i)));
        }
        return new Connection(client, id, user, name, type, displayOnProfile, friend_sync, verified, revoked, integrations);
    }

    //---------------------Audio---------------------
    public VoiceState buildVoiceState(JSONObject json) {
        IUser user = identity.getUser(json.getString("user_id"));
        IAudioChannel channel = json.isNull("channel_id") ? null : identity.getAudioChannel(json.getString("channel_id"));
        String session_id = json.getString("session_id");
        boolean selfMute = json.getBoolean("self_mute");
        boolean selfDeaf = json.getBoolean("self_deaf");
        VoiceState state = new VoiceState(identity, user);
        state.setChannel(channel);
        state.setSessionId(session_id);
        state.setSelfMuted(selfMute);
        state.setSelfDeafened(selfDeaf);
        return state;
    }

    //---------------------Audit---------------------
    public AuditLog buildAuditLog(IGuild guild, JSONObject json) {
        handleBuildError(json);
        JSONArray array = json.getJSONArray("audit_log_entries");
        List<ILogEntry> entries = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            entries.add(buildLogEntry(array.getJSONObject(i)));
        }
        return new AuditLog(guild, entries);
    }

    public LogEntry buildLogEntry(JSONObject json) {
        handleBuildError(json);
        LogType type = LogType.getByKey(json.getInt("action_type"));
        String id = json.getString("id");
        String targetId = json.getString("target_id");
        User user = (User) identity.getUser(json.getString("user_id"));
        String reason = json.has("reason") ? json.getString("reason") : null;

        LogEntry entry = new LogEntry(type, id, targetId, user, reason);

        // Log Changes
        Map<ChangeType, ILogChange> changes = new HashMap<>();
        JSONArray array = json.getJSONArray("changes");
        for (int i = 0; i < array.length(); i++) {
            LogChange change = buildLogChange(array.getJSONObject(i));
            changes.put(change.getType(), change);
        }
        entry.setChanges(changes);

        // Log Options
        Map<LogOption, String> options = new HashMap<>();
        if (json.has("options")) {
            JSONObject option = json.getJSONObject("options");
            for (String key : option.keySet()) {
                options.put(LogOption.getByKey(key), option.getString(key));
            }
        }
        entry.setOptions(options);

        return entry;
    }

    public LogChange buildLogChange(JSONObject json) {
        handleBuildError(json);
        ChangeType type = ChangeType.getByKey(json.getString("key"));
        Object newVal = json.has("new_value") ? json.get("new_value") : null;
        Object oldVal = json.has("old_value") ? json.get("old_value") : null;
        return new LogChange(type, newVal, oldVal);
    }

    //---------------------IBotUser---------------------

    public BotApplication buildBotApplication(JSONObject json) {
        String id = json.getString("id");
        String name = json.getString("name");
        String icon = json.isNull("icon") ? null : json.getString("icon");
        String description = json.getString("description");

        String ownerId = json.getJSONObject("owner").getString("id");
        User owner = (User) identity.getUser(ownerId);
        if (owner == null) {
            owner = buildUser(json.getJSONObject("owner"));
        }

        List<String> rpcOrigins = new ArrayList<>();
        JSONArray array = json.has("rpc_origins") ? json.getJSONArray("rpc_origins") : new JSONArray();
        for (int i = 0 ; i < array.length(); i++) {
            rpcOrigins.add(array.getString(i));
        }

        boolean isPublicBot = json.has("bot_public") && json.getBoolean("bot_public");
        boolean requireCodeGrant = json.has("bot_require_code_grant") && json.getBoolean("bot_require_code_grant");

        return new BotApplication(identity, id, name, icon, description, owner, rpcOrigins, isPublicBot, requireCodeGrant);
    }

    //---------------------Client---------------------

    public Profile buildProfile(JSONObject json, User user) {
        return new Profile(client, user,
                json.has("mobile") && json.getBoolean("mobile"),
                json.has("premium") && json.getBoolean("premium"));
    }

    public Group buildGroup(JSONObject json) {
        handleBuildError(json);

        String id = json.getString("id");
        String name = json.isNull("name") ? null : json.getString("name");
        String icon = json.isNull("icon") ? null : json.getString("icon");
        String owner_id = json.getString("owner_id");

        JSONArray rs = json.getJSONArray("recipients");
        List<IUser> recipients = new ArrayList<>();
        for (int i = 0; i < rs.length(); i++) {
            JSONObject userJson = rs.getJSONObject(i);
            IUser user = identity.getUser(userJson.getString("id"));
            if (user == null) user = buildUser(userJson);
            recipients.add(user);
        }

        Group group = new Group(client, id, name, icon, owner_id, recipients);
        client.addGroup(group);
        return group;
    }

    public Call buildCall(JSONObject json) {
        String id = json.getString("channel_id");
        Region region = Region.getByKey(json.getString("region"));
        ICallChannel channel = client.getCallChannel(id);
        return new Call(client, id, region, channel);
    }

    public Relationship buildRelationship(JSONObject json) {
        IRelationship.Type type = IRelationship.Type.getByKey(json.getInt("type"));
        User user = (User) identity.getUser(json.getString("id"));
        if (user == null) {
            user = buildUser(json.getJSONObject("user"));
        }

        Relationship relationship;

        switch (type) {
            case FRIEND: {
                relationship = new Friend(client, type, user);
                break;
            }
            case BLOCK: {
                relationship = new BlockedUser(client, type, user);
                break;
            }
            default: {
                relationship = new Relationship(client, type, user);
                break;
            }
        }

        client.addRelationship(relationship);
        return relationship;
    }

    public Note buildNote(String userId, String content) {
        Note note = new Note(client, identity.getUser(userId), content);
        client.addNote(note);
        return note;
    }

    public ClientSetting buildClientSetting(JSONObject json) {
        OnlineStatus status = OnlineStatus.getByKey(json.getString("status"));
        Locale locale = new Locale(json.getString("locale"));

        List<IGuild> guildsPositions = new ArrayList<>();
        JSONArray gps = json.getJSONArray("guild_positions");
        for (int i = 0; i < gps.length(); i++) {
            guildsPositions.add(identity.getGuild(gps.getString(i)));
        }

        List<IGuild> restrictedGuilds = new ArrayList<>();
        JSONArray rgs = json.getJSONArray("restricted_guilds");
        for (int i = 0; i < rgs.length(); i++) {
            guildsPositions.add(identity.getGuild(rgs.getString(i)));
        }

        ClientSetting setting = new ClientSetting(client, status, locale, guildsPositions, restrictedGuilds);

        setting.setShowCurrentGame(json.has("show_current_game") && json.getBoolean("show_current_game"));
        setting.setDeveloperMode(json.has("developer_mode") && json.getBoolean("developer_mode"));
        setting.setMessageDisplayCompact(json.has("message_display_compact") && json.getBoolean("message_display_compact"));

        setting.setEnableTTS(json.has("enable_tts_command") && json.getBoolean("enable_tts_command"));
        setting.setConvertEmoticons(json.has("convert_emoticons") && json.getBoolean("convert_emoticons"));
        setting.setRenderReaction(json.has("render_reactions") && json.getBoolean("render_reactions"));
        setting.setRenderEmbeds(json.has("render_embeds") && json.getBoolean("render_embeds"));
        setting.setInlineEmbedMedia(json.has("inline_embed_media") && json.getBoolean("inline_embed_media"));
        setting.setInlineAttachmentMedia(json.has("inline_attachment_media") && json.getBoolean("inline_attachment_media"));

        setting.setDetectPlatformAccounts(json.has("detect_platform_accounts") && json.getBoolean("detect_platform_accounts"));

        return setting;
    }

    public GuildSetting buildGuildSetting(JSONObject json) {
        Guild guild = (Guild) identity.getGuild(json.getString("guild_id"));

        if (guild == null) {
            identity.LOG.log(LogLevel.TRACE, "Encounter a guild setting with unknown guild!"); // This is intentional
            return null;
        }

        MessageNotification notifSetting = MessageNotification.getByKey(json.getInt("message_notifications"));
        boolean muted = json.has("muted") && json.getBoolean("muted");
        boolean mobilePush = json.has("mobile_push") && json.getBoolean("mobile_push");
        boolean suppressEveryone = json.has("suppress_everyone") && json.getBoolean("suppress_everyone");

        GuildSetting setting = new GuildSetting(client, guild, notifSetting, muted, mobilePush, suppressEveryone);

        JSONArray channels = json.getJSONArray("channel_overrides");
        for (int i = 0; i < channels.length(); i++) {
            ChannelSetting cs = buildTextChannelSetting(guild, channels.getJSONObject(i));
            if (cs.getChannel() == null) {
                identity.LOG.log(LogLevel.TRACE, "Encounter a channel setting with unknown channel! Guild: " + guild);  // This is intentional
                continue;
            }
            setting.addChannelSetting(cs);
        }
        client.addGuildSetting(setting);
        return setting;
    }

    public ChannelSetting buildTextChannelSetting(Guild guild, JSONObject json) {
        ITextChannel channel = guild.getTextChannel(json.getString("channel_id"));
        MessageNotification notifSetting = MessageNotification.getByKey(json.getInt("message_notifications"));
        boolean muted = json.has("muted") && json.getBoolean("muted");
        return new ChannelSetting(client, channel, notifSetting, muted);
    }

    public Application buildApplication(JSONObject json) {
        String id = json.getString("id");
        String secret = json.getString("secret");
        String name = json.getString("name");
        String icon = json.isNull("icon") ? null : json.getString("icon");
        String description = json.getString("description");

        JSONArray uris = json.getJSONArray("redirect_uris");
        List<String> redirectUris = new ArrayList<>();
        for (int i = 0; i < uris.length(); i++) {
            redirectUris.add(uris.getString(i));
        }

        if (json.has("bot")) {
            boolean isPublicBot = json.has("bot_public") && json.getBoolean("bot_public");
            boolean requireCodeGrant = json.has("bot_require_code_grant") && json.getBoolean("bot_require_code_grant");

            JSONObject botJson = json.getJSONObject("bot");
            String bId = botJson.getString("id");
            String bToken = botJson.getString("token");
            String bName = botJson.getString("username");
            String bDiscriminator = botJson.getString("discriminator");
            String bIcon = botJson.isNull("avatar") ? null : botJson.getString("avatar");
            Application.BotUser bot = new Application.BotUser(bId, bToken, bName, bDiscriminator, bIcon);

            return new Application(client, id, secret, name, icon, description, redirectUris, bot, isPublicBot, requireCodeGrant);
        } else {
            return new Application(client, id, secret, name, icon, description, redirectUris);
        }
    }

    public AuthApplication buildAuthApplication(JSONObject json) {
        String authId = json.getString("id");
        JSONObject app = json.getJSONObject("application");
        String id = app.getString("id");
        String name = app.getString("name");
        String icon = app.isNull("icon") ? null : app.getString("icon");
        String description = app.getString("description");

        boolean isPublicBot = app.has("bot_public") && app.getBoolean("bot_public");
        boolean requireCodeGrant = app.has("bot_require_code_grant") && app.getBoolean("bot_require_code_grant");

        JSONArray s = json.getJSONArray("scopes");
        List<Scope> scopes = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            scopes.add(Scope.getByKey(s.getString(i)));
        }

        return new AuthApplication(client, id, authId, name, icon, description, scopes, isPublicBot, requireCodeGrant);
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
