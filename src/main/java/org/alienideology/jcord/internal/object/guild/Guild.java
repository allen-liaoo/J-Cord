package org.alienideology.jcord.internal.object.guild;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import org.alienideology.jcord.handle.Region;
import org.alienideology.jcord.handle.channel.IGuildChannel;
import org.alienideology.jcord.handle.channel.ITextChannel;
import org.alienideology.jcord.handle.channel.IVoiceChannel;
import org.alienideology.jcord.handle.guild.*;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.internal.gateway.HttpPath;
import org.alienideology.jcord.internal.object.DiscordObject;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.channel.TextChannel;
import org.alienideology.jcord.internal.object.channel.VoiceChannel;
import org.alienideology.jcord.internal.object.user.User;

import java.util.*;

/**
 * @author AlienIdeology
 */
public final class Guild extends DiscordObject implements IGuild {

    private final String id;
    private boolean isAvailable = false;

    private GuildManager guildManager;

    private String name;

    private String icon;
    private String splash;

    private Member owner;
    private Region region;

    private AFKTimeout afk_timeout;
    private VoiceChannel afk_channel;

    private boolean embed_enabled;
    private TextChannel embed_channel;

    private Verification verification_level;
    private Notification notifications_level;
    private MFA mfa_level;

    private List<Role> roles;
    private List<GuildEmoji> emojis;

    private final List<Member> members;
    private final List<TextChannel> textChannels;
    private final List<VoiceChannel> voiceChannels;

    /**
     * Unavailable Guild
     * @param identity The IdentityImpl this guild belongs to.
     * @param id The ID of this guild
     */
    public Guild (IdentityImpl identity, String id) {
        this(identity, id, null, null, null, null,
                -1, false, -1, -1, -1);
    }


    /**
     * Available Guild
     * @param identity The IdentityImpl this guild belongs to.
     * @param id The ID of this guild
     * @param icon The icon of this guild
     * @param splash The splash hash of this guild
     * @param region The string value of the region
     * @param afk_timeout The afk timeout
     * @param embed_enabled Is embed enabled (i.e. widget)
     * @param verification_level Level of verification
     * @param notification_level Level of notification
     * @param mfa_level Required MFA level
     */
    public Guild (IdentityImpl identity, String id, String name, String icon, String splash, String region,
                  int afk_timeout, boolean embed_enabled, int verification_level, int notification_level, int mfa_level) {
        super(identity);
        this.id = id;
        isAvailable = true;
        this.name = name;
        this.icon = icon;
        setIcon();
        this.splash = splash;
        this.region = Region.getByKey(region);
        this.afk_timeout = AFKTimeout.getByTimeout(afk_timeout);
        this.embed_enabled = embed_enabled;
        this.verification_level = Verification.getByKey(verification_level);
        this.notifications_level = Notification.getByKey(notification_level);
        this.mfa_level = MFA.getByKey(mfa_level);
        this.roles = new ArrayList<>();
        this.emojis = new ArrayList<>();
        this.members = new ArrayList<>();
        this.textChannels = new ArrayList<>();
        this.voiceChannels = new ArrayList<>();
        this.guildManager = new GuildManager(this);
    }

    @Override
    public boolean isAvailable() {
        return isAvailable;
    }

    @Override
    public IGuildManager getGuildManager() {
        return guildManager;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getIcon() {
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
        return afk_timeout;
    }

    @Override
    public boolean isEmbedEnabled() {
        return embed_enabled;
    }

    @Override
    public Verification getVerificationLevel() {
        return verification_level;
    }

    @Override
    public Notification getNotificationsLevel() {
        return notifications_level;
    }

    @Override
    public MFA getMFALevel() {
        return mfa_level;
    }

    @Override
    @Nullable
    public VoiceChannel getAfkChannel() {
        return afk_channel;
    }

    @Override
    @Nullable
    public TextChannel getEmbedChannel() {
        return embed_channel;
    }

    @Override
    public Member getOwner() {
        return owner;
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
    @NotNull
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
    @NotNull
    public ITextChannel getDefaultChannel() {
        for (TextChannel tc : textChannels) {
            if (tc.isDefaultChannel()) {
                return tc;
            }
        }
        return null;
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
        return "ID: "+id+"\tName: "+name;
    }

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

    public Guild setChannels (String afk, String embed) {
        this.afk_channel = (VoiceChannel) getVoiceChannel(afk);
        this.embed_channel = (TextChannel) getTextChannel(embed);
        return this;
    }

    private void setIcon() {
        this.icon = icon == null ?
                null : String.format(HttpPath.EndPoint.GUILD_ICON, id, icon);
    }

    public Guild addGuildChannel (IGuildChannel... channels) {
        for (IGuildChannel channel : channels) {
            if (channel instanceof TextChannel) {
                textChannels.add((TextChannel) channel);
            } else if (channel instanceof VoiceChannel) {
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

    public Guild addMember (Member... members) {
        this.members.addAll(Arrays.asList(members));
        return this;
    }

    public Member removeMember(String memberId) {
        Member member = (Member) getMember(memberId);
        this.members.remove(member);
        return member;
    }

    public Guild addRole (Role... roles) {
        this.roles.addAll(Arrays.asList(roles));
        return this;
    }

    public Role removeRole(String roleId) {
        Role role = (Role) getRole(roleId);
        this.roles.remove(role);
        return role;
    }

    public Guild addGuildEmoji (GuildEmoji... emojis) {
        this.emojis.addAll(Arrays.asList(emojis));
        return this;
    }

    public GuildEmoji removeGuildEmoji(String emojiId) {
        GuildEmoji emoji = (GuildEmoji) getGuildEmoji(emojiId);
        this.roles.remove(emoji);
        return emoji;
    }

}
