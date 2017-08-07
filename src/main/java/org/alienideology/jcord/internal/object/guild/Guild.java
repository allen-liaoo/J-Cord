package org.alienideology.jcord.internal.object.guild;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.handle.Icon;
import org.alienideology.jcord.handle.Region;
import org.alienideology.jcord.handle.audit.IAuditLog;
import org.alienideology.jcord.handle.channel.IGuildChannel;
import org.alienideology.jcord.handle.channel.ITextChannel;
import org.alienideology.jcord.handle.channel.IVoiceChannel;
import org.alienideology.jcord.handle.guild.*;
import org.alienideology.jcord.handle.managers.IGuildManager;
import org.alienideology.jcord.handle.managers.IInviteManager;
import org.alienideology.jcord.handle.permission.Permission;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.handle.user.IWebhook;
import org.alienideology.jcord.internal.exception.PermissionException;
import org.alienideology.jcord.internal.object.DiscordObject;
import org.alienideology.jcord.internal.object.Jsonable;
import org.alienideology.jcord.internal.object.ObjectBuilder;
import org.alienideology.jcord.internal.object.channel.TextChannel;
import org.alienideology.jcord.internal.object.channel.VoiceChannel;
import org.alienideology.jcord.internal.object.managers.GuildManager;
import org.alienideology.jcord.internal.object.managers.InviteManager;
import org.alienideology.jcord.internal.object.user.User;
import org.alienideology.jcord.internal.rest.HttpPath;
import org.alienideology.jcord.internal.rest.Requester;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author AlienIdeology
 */
// TODO: GuildUnavailable
public final class Guild extends DiscordObject implements IGuild, Jsonable {

    private final String id;
    private boolean isAvailable = false;

    private GuildManager guildManager;
    private InviteManager inviteManager;

    private String name;

    private String icon;
    private String splash;

    private Member owner;
    private Region region;

    private AFKTimeout afkTimeout;
    private VoiceChannel afkChannel;

    private boolean embedEnabled;
    private TextChannel embedChannel;

    private Verification verificationLevel;
    private Notification notificationLevel;
    private ContentFilterLevel ecfLevel;
    private MFA mfaLevel;

    private List<Role> roles;
    private List<GuildEmoji> emojis;

    private final List<Member> members;
    private final List<TextChannel> textChannels;
    private final List<VoiceChannel> voiceChannels;

    public Guild(Identity identity, String id, boolean isAvailable) {
        super(identity);
        this.id = id;
        this.isAvailable = isAvailable;
        this.roles = new ArrayList<>();
        this.emojis = new ArrayList<>();
        this.members = new ArrayList<>();
        this.textChannels = new ArrayList<>();
        this.voiceChannels = new ArrayList<>();
        this.guildManager = new GuildManager(this);
        this.inviteManager = new InviteManager(this);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject()
                .put("name", name)
                .put("region", region)
                // icon is either raw data from Icon.getData() casted to string
                // or Icon.DEFAULT_ICON
                .put("icon", icon == null ? Icon.DEFAULT_ICON.getData() : icon);
        if (verificationLevel != Verification.UNKNOWN) json.put("verification_level", verificationLevel.key);
        if (notificationLevel != Notification.UNKNOWN) json.put("default_message_notifications", notificationLevel.key);

        JSONArray roles = new JSONArray();
        for (IRole role : this.roles) {
            roles.put(((Role) role).toJson().put("id", 0));
        }

        JSONArray channels = new JSONArray();
        for (IGuildChannel channel : getAllGuildChannels()) {
            channels.put((channel instanceof TextChannel) ? ((TextChannel) channel).toJson() : ((VoiceChannel) channel).toJson());
        }

        json.put("roles", roles)
                .put("channels", channels);

        return json;
    }

    @Override
    public boolean isAvailable() {
        return isAvailable;
    }

    @Override
    public IGuildManager getManager() {
        return guildManager;
    }

    @Override
    public IInviteManager getInviteManager() {
        return inviteManager;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getIconHash() {
        return icon;
    }

    @Override
    public String getSplash() {
        return splash;
    }

    @Override
    public Region getRegion() {
        return region;
    }

    @Override
    public AFKTimeout getAfkTimeout() {
        return afkTimeout;
    }

    @Override
    public boolean isEmbedEnabled() {
        return embedEnabled;
    }

    @Override
    public Verification getVerificationLevel() {
        return verificationLevel;
    }

    @Override
    public Notification getNotificationsLevel() {
        return notificationLevel;
    }

    @Override
    public ContentFilterLevel getContentFilterLevel() {
        return ecfLevel;
    }

    @Override
    public MFA getMFALevel() {
        return mfaLevel;
    }

    @Override
    @Nullable
    public VoiceChannel getAfkChannel() {
        return afkChannel;
    }

    @Override
    @Nullable
    public TextChannel getEmbedChannel() {
        return embedChannel;
    }

    @Override
    public Member getOwner() {
        return owner;
    }

    @Override
    public IAuditLog getAuditLog(int amount) {
        return getAuditLog(null, amount);
    }

    @Override
    public IAuditLog getAuditLogBefore(String entryId, int amount) {
        return getAuditLog(entryId, amount);
    }

    private IAuditLog getAuditLog(String entryId, int amount) {
        JSONObject audit;
        if (entryId == null) {
            audit = new Requester(identity, HttpPath.Audit.GET_GUILD_AUDIT_LOG)
                    .request(id, String.valueOf(amount))
                    .getAsJSONObject();
        } else {
            audit = new Requester(identity, HttpPath.Audit.GET_GUILD_AUDIT_LOG_BEFORE)
                .request(id, entryId, String.valueOf(amount))
                .getAsJSONObject();
        }

        return new ObjectBuilder(identity).buildAuditLog(this, audit);
    }

    @Override
    public List<IUser> getUsers() {
        List<User> users = new ArrayList<>();
        for (Member mem : members) {
            users.add((User) mem.getUser());
        }
        return Collections.unmodifiableList(users);
    }

    @Override
    public IMember getSelfMember() {
        for (Member member : members) {
            if (member.getUser().isSelf())
                return member;
        }
        return null;
    }

    @Override
    @Nullable
    public IMember getMember(String id) {
        for (Member member : members) {
            if (member.getId().equals(id))
                return member;
        }
        return null;
    }

    @Override
    public List<IMember> getMembers() {
        return Collections.unmodifiableList(members);
    }

    @Override
    public IWebhook getWebhook(String id) {
        for (ITextChannel channel : textChannels) {
            IWebhook webhook = channel.getWebhook(id);
            if (webhook != null) return webhook;
        }
        return null;
    }

    @Override
    public List<IWebhook> getWebhooks() {
        if (!getSelfMember().hasPermissions(true, Permission.MANAGE_WEBHOOKS)) {
            throw new PermissionException(Permission.ADMINISTRATOR, Permission.MANAGE_WEBHOOKS);
        }

        List<IWebhook> webhooks = new ArrayList<>();
        for (ITextChannel channel : textChannels) {
            webhooks.addAll(channel.getWebhooks());
        }
        return webhooks;
    }

    @Override
    @Nullable
    public IRole getRole(String id) {
        for (Role role : roles) {
            if (role.getId().equals(id))
                return role;
        }
        return null;
    }

    @Override
    @NotNull
    public IRole getEveryoneRole() {
        for (Role role : roles) {
            if (role.isEveryone())
                return role;
        }
        return null;
    }

    @Override
    public List<IRole> getRoles() {
        return Collections.unmodifiableList(roles);
    }

    @Override
    @Nullable
    public IGuildEmoji getGuildEmoji(String id) {
        for (GuildEmoji emoji : emojis) {
            if (emoji.getId().equals(id)) {
                return emoji;
            }
        }
        return null;
    }

    @Override
    public List<IGuildEmoji> getGuildEmojis() {
        return Collections.unmodifiableList(emojis);
    }

    @Override
    public IGuildChannel getGuildChannel(String id) {
        for (IGuildChannel channel : getAllGuildChannels()) {
            if (channel.getId().equals(id))
                return channel;
        }
        return null;
    }

    @Override
    public List<IGuildChannel> getAllGuildChannels() {
        List<IGuildChannel> channels = new ArrayList<>(getTextChannels());
        channels.addAll(getVoiceChannels());
        return Collections.unmodifiableList(channels);
    }

    @Override
    @Nullable
    public ITextChannel getTextChannel(String id) {
        for (TextChannel tc : textChannels) {
            if (tc.getId().equals(id)) {
                return tc;
            }
        }
        return null;
    }

    @Override
    public ITextChannel getDefaultChannel() {
        return getDefaultChannel(getSelfMember());
    }

    @Override
    public ITextChannel getDefaultChannel(IMember member) {
        return textChannels.stream()
                .filter(c -> c.hasPermission(member, Permission.ADMINISTRATOR, Permission.READ_MESSAGES))
                .sorted(ITextChannel::compareTo)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<ITextChannel> getTextChannels() {
        return Collections.unmodifiableList(textChannels);
    }

    @Override
    @Nullable
    public IVoiceChannel getVoiceChannel(String id) {
        for (VoiceChannel vc : voiceChannels) {
            if (vc.getId().equals(id)) {
                return vc;
            }
        }
        return null;
    }

    @Override
    public List<IVoiceChannel> getVoiceChannels() {
        return Collections.unmodifiableList(voiceChannels);
    }

    @Override
    public List<IIntegration> getIntegrations() {
        List<IIntegration> integrations = new ArrayList<>();
        JSONArray ints = new Requester(identity, HttpPath.Guild.GET_GUILD_INTEGRATIONS)
                .request(id).getAsJSONArray();
        ObjectBuilder builder = new ObjectBuilder(identity);
        for (int i = 0; i < ints.length(); i++) {
            integrations.add(builder.buildIntegration(ints.getJSONObject(i)));
        }

        return integrations;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Guild) && Objects.equals(this.id, ((Guild) obj).getId());
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Guild{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    //----------------Setters----------------
    public Guild setOwner (String id) {
        for (Member mem : members) {
            if (mem.getId().equals(id)) {
                this.owner = mem;
                break;
            }
        }
        return this;
    }

    public Guild setUnavailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
        return this;
    }

    public Guild setName(String name) {
        this.name = name;
        return this;
    }

    public Guild setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public Guild setSplash(String splash) {
        this.splash = splash;
        return this;
    }

    public Guild setOwner(Member owner) {
        this.owner = owner;
        return this;
    }

    public Guild setRegion(String region) {
        this.region = Region.getByKey(region);
        return this;
    }

    public Guild setAfkTimeout(int afkTimeout) {
        this.afkTimeout = AFKTimeout.getByTimeout(afkTimeout);
        return this;
    }

    public Guild setAfkChannel(String afkChannel) {
        this.afkChannel = (VoiceChannel) getVoiceChannel(afkChannel);
        return this;
    }

    public Guild setEmbedEnabled(boolean embedEnabled) {
        this.embedEnabled = embedEnabled;
        return this;
    }

    public Guild setEmbedChannel(String embedChannel) {
        this.embedChannel = (TextChannel) getTextChannel(embedChannel);
        return this;
    }

    public Guild setVerificationLevel(int verificationLevel) {
        this.verificationLevel = Verification.getByKey(verificationLevel);
        return this;
    }

    public Guild setNotificationLevel(int notificationLevel) {
        this.notificationLevel = Notification.getByKey(notificationLevel);
        return this;
    }

    public Guild setEcfLevel(int ecfLevel) {
        this.ecfLevel = ContentFilterLevel.getByKey(ecfLevel);
        return this;
    }

    public Guild setMfaLevel(int mfaLevel) {
        this.mfaLevel = MFA.getByKey(mfaLevel);
        return this;
    }

    public Guild addGuildChannel (IGuildChannel... channels) {
        for (IGuildChannel channel : channels) {
            if (channel instanceof TextChannel) {
                if (!textChannels.contains(channel))
                    textChannels.add((TextChannel) channel);
            } else if (channel instanceof VoiceChannel) {
                if (!voiceChannels.contains(channel))
                    voiceChannels.add((VoiceChannel) channel);
            }
        }
        return this;
    }

    public TextChannel removeTextChannel(String channelId) {
        TextChannel channel = (TextChannel) getTextChannel(channelId);
        this.textChannels.remove(channel);
        return channel;
    }

    public VoiceChannel removeVoiceChannel(String channelId) {
        VoiceChannel channel = (VoiceChannel) getVoiceChannel(channelId);
        this.voiceChannels.remove(channel);
        return channel;
    }

    public Guild addMember (Member member) {
        if (!members.contains(member))
            members.add(member);
        return this;
    }

    public Member removeMember(String memberId) {
        Member member = (Member) getMember(memberId);
        this.members.remove(member);
        return member;
    }

    public Guild addRole (Role role) {
        if (!roles.contains(role))
            roles.add(role);
        return this;
    }

    public Role removeRole(String roleId) {
        Role role = (Role) getRole(roleId);
        this.roles.remove(role);
        return role;
    }

    public Guild addGuildEmoji (GuildEmoji emoji) {
        if (!emojis.contains(emoji))
            emojis.add(emoji);
        return this;
    }

    public GuildEmoji removeGuildEmoji(String emojiId) {
        GuildEmoji emoji = (GuildEmoji) getGuildEmoji(emojiId);
        this.roles.remove(emoji);
        return emoji;
    }

}
