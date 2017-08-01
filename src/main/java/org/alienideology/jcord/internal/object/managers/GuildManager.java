package org.alienideology.jcord.internal.object.managers;

import org.alienideology.jcord.handle.Icon;
import org.alienideology.jcord.handle.audit.AuditAction;
import org.alienideology.jcord.handle.channel.IGuildChannel;
import org.alienideology.jcord.handle.channel.ITextChannel;
import org.alienideology.jcord.handle.channel.IVoiceChannel;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.guild.IMember;
import org.alienideology.jcord.handle.guild.IRole;
import org.alienideology.jcord.handle.guild.Region;
import org.alienideology.jcord.handle.managers.IGuildManager;
import org.alienideology.jcord.handle.permission.Permission;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.internal.exception.ErrorResponseException;
import org.alienideology.jcord.internal.exception.HigherHierarchyException;
import org.alienideology.jcord.internal.exception.HigherHierarchyException.HierarchyType;
import org.alienideology.jcord.internal.exception.HttpErrorException;
import org.alienideology.jcord.internal.exception.PermissionException;
import org.alienideology.jcord.internal.gateway.ErrorResponse;
import org.alienideology.jcord.internal.gateway.HttpCode;
import org.alienideology.jcord.internal.gateway.HttpPath;
import org.alienideology.jcord.internal.gateway.Requester;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.ObjectBuilder;
import org.alienideology.jcord.internal.object.channel.TextChannel;
import org.alienideology.jcord.internal.object.channel.VoiceChannel;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.alienideology.jcord.internal.object.guild.Role;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author AlienIdeology
 */
public final class GuildManager implements IGuildManager {

    private Guild guild;

    public GuildManager(Guild guild) {
        this.guild = guild;
    }

    @Override
    public IGuild getGuild() {
        return guild;
    }

    @Override
    public AuditAction<Void> modifyName(String name) {
        if (name == null || name.isEmpty()) return new AuditAction.EmptyAuditAction<>();;
        if (!IGuild.isValidName(name)) {
            throw new IllegalArgumentException("The name is not valid!");
        }
        return requestModify(new JSONObject().put("name", name));
    }

    @Override
    public AuditAction<Void> modifyOwner(IMember newOwner) {
        if (newOwner == null || !newOwner.getGuild().equals(guild)) return new AuditAction.EmptyAuditAction<>();;
        return requestModify(new JSONObject().put("owner_id", newOwner.getId()));
    }

    @Override
    public AuditAction<Void> modifyRegion(Region region) {
        if (region == Region.UNKNOWN) return new AuditAction.EmptyAuditAction<>();
        try {
            return requestModify(new JSONObject().put("region", region.key));
        } catch (HttpErrorException ex) {
            if (ex.getCode().equals(HttpCode.BAD_REQUEST)) {
                throw new HttpErrorException(HttpCode.BAD_REQUEST, "Invalid VIP Region!");
            } else {
                throw ex;
            }
        }
    }

    @Override
    public AuditAction<Void> modifyVerification(IGuild.Verification verification) {
        if (verification == IGuild.Verification.UNKNOWN) return new AuditAction.EmptyAuditAction<>();;
        return requestModify(new JSONObject().put("verification_level", verification.key));
    }

    @Override
    public AuditAction<Void> modifyNotification(IGuild.Notification notification) {
        if (notification == IGuild.Notification.UNKNOWN) return new AuditAction.EmptyAuditAction<>();;
        return requestModify(new JSONObject().put("default_message_notifications", notification.key));
    }

    @Override
    public AuditAction<Void> modifyAFKTimeout(IGuild.AFKTimeout afkTimeout) {
        if (afkTimeout == IGuild.AFKTimeout.UNKNOWN) return new AuditAction.EmptyAuditAction<>();;
        return requestModify(new JSONObject().put("afk_timeout", afkTimeout.timeout));
    }

    @Override
    public AuditAction<Void> modifyAFKChannel(IVoiceChannel afkChannel) {
        if (afkChannel == null) return new AuditAction.EmptyAuditAction<>();;
        return  modifyAFKChannel(afkChannel.getId());
    }

    @Override
    public AuditAction<Void> modifyAFKChannel(String afkChannelId) {
        if (afkChannelId == null || afkChannelId.isEmpty()) return new AuditAction.EmptyAuditAction<>();;
        return requestModify(new JSONObject().put("afk_channel_id", afkChannelId));
    }

    @Override
    public AuditAction<Void> modifyIcon(Icon icon) {
        return requestModify(new JSONObject().put("icon", icon.getData()));
    }

    @Override
    public AuditAction<Void> modifySplash(Icon icon) {
        return requestModify(new JSONObject().put("splash", icon.getData()));
    }

    private AuditAction<Void> requestModify(JSONObject json) {
        try {
            return new AuditAction<Void>((IdentityImpl) getIdentity(), HttpPath.Guild.MODIFY_GUILD, guild.getId()) {
                @Override
                protected Void request(Requester requester) {
                    requester.updateRequestWithBody(request -> request.header("Content-Type", "application/json")
                            .body(json))
                            .performRequest();
                    return null;
                }
            };
        } catch (HttpErrorException ex) {
            if (ex.isPermissionException()) {
                throw new PermissionException(Permission.ADMINISTRATOR, Permission.MANAGE_SERVER);
            } else {
                throw ex;
            }
        }
    }

    @Override
    public AuditAction<Boolean> kickMember(IMember member) {
        return kickMember(member.getId());
    }

    @Override
    public AuditAction<Boolean> kickMember(String memberId) {
        IMember member = guild.getMember(memberId);
        // Validate member
        if (member == null) {
            throw new ErrorResponseException(ErrorResponse.UNKNOWN_MEMBER, "Unknown member to kick! ID: "+memberId);
        }
        if (member.equals(guild.getSelfMember())) {
            throw new IllegalArgumentException("Cannot kick the identity itself from a guild!");
        }

        // Checks hierarchy
        if (member.isOwner()) {
            throw new HigherHierarchyException(HierarchyType.OWNER);
        }
        if (!guild.getSelfMember().canModify(member)) {
            throw new HigherHierarchyException(HierarchyType.MEMBER);
        }

        try {
            return new AuditAction<Boolean>((IdentityImpl) getIdentity(), HttpPath.Guild.REMOVE_GUILD_MEMBER, guild.getId(), memberId) {
                @Override
                protected Boolean request(Requester requester) {
                    HttpCode code = requester.performRequest();
                    return code.isOK() || code.isSuccess();
                }
            };
        } catch (HttpErrorException ex) {
            if (ex.isPermissionException()) {
                throw new PermissionException(Permission.ADMINISTRATOR, Permission.KICK_MEMBERS);
            } else {
                throw ex;
            }
        }
    }

    @Override
    public int getPrunableCount() {
        return getPrunableCount(7);
    }

    @Override
    public int getPrunableCount(int days) {
        if (days <= 0 || days > 30) {
            throw new IllegalArgumentException("The days for getting prunable count may not be smaller or equal to 0, or greater than 30! Provided days: "+days);
        }

        try {
            JSONObject pruneCount = new Requester((IdentityImpl) getIdentity(), HttpPath.Guild.GET_GUILD_PRUNE_COUNT).request(guild.getId(), String.valueOf(days))
                .getAsJSONObject();
            return pruneCount.getInt("pruned");
        } catch (HttpErrorException ex) {
            if (ex.isPermissionException()) {
                throw new PermissionException(Permission.ADMINISTRATOR, Permission.KICK_MEMBERS);
            } else {
                throw ex;
            }
        }
    }

    @Override
    public AuditAction<Void> pruneMembers(int days) {
        if (days <= 0 || days > 30) {
            throw new IllegalArgumentException("The days for getting prunable count may not be smaller or equal to 0, or greater than 30! Provided days: "+days);
        }

        // This fires multiple Guild Remove Gateway event
        // Ignored the returning JSONObject (Pruned members)
        try {
            return new AuditAction<Void>((IdentityImpl) getIdentity(), HttpPath.Guild.BEGIN_GUILD_PRUNE, guild.getId(), String.valueOf(days)) {
                @Override
                protected Void request(Requester requester) {
                    requester.performRequest();
                    return null;
                }
            };
        } catch (HttpErrorException ex) {
            if (ex.isPermissionException()) {
                throw new PermissionException(Permission.ADMINISTRATOR, Permission.KICK_MEMBERS);
            } else {
                throw ex;
            }
        }
    }

    @Override
    public List<IMember> getBans() {
        try {
            JSONArray members = new Requester((IdentityImpl) getIdentity(), HttpPath.Guild.GET_GUILD_BANS).request(guild.getId())
                    .getAsJSONArray();
            List<IMember> bannedMembers = new ArrayList<>();

            for (int i = 0; i < members.length(); i++) {
                JSONObject user = members.getJSONObject(i);
                bannedMembers.add(guild.getMember(user.getString("id")));
            }
            return bannedMembers;
        } catch (HttpErrorException ex) {
            if (ex.isPermissionException()) {
                throw new PermissionException(Permission.BAN_MEMBERS);
            } else {
                throw ex;
            }
        }
    }

    @Override
    public AuditAction<Boolean> banMember(IMember member) {
        return banMember(member.getId(), 7);
    }

    @Override
    public AuditAction<Boolean> banMember(IMember member, int days) {
        checkDays(days);
        return banMember(member.getId(), days);
    }

    @Override
    public AuditAction<Boolean> banMember(String memberId) {
        return banMember(memberId, 7);
    }

    @Override
    public AuditAction<Boolean> banMember(String memberId, int days) {
        IMember member = guild.getMember(memberId);
        // Validate member
        if (member == null) {
            throw new ErrorResponseException(ErrorResponse.UNKNOWN_MEMBER, "Unknown member to ban! ID: "+memberId);
        }
        if (member.equals(guild.getSelfMember())) {
            throw new IllegalArgumentException("Cannot kick the identity itself from a guild!");
        }

        // Checks hierarchy
        if (member.isOwner()) {
            throw new HigherHierarchyException(HierarchyType.OWNER);
        }
        if (!guild.getSelfMember().canModify(member)) {
            throw new HigherHierarchyException(HierarchyType.MEMBER);
        }

        checkDays(days);
        try {
             return new AuditAction<Boolean>((IdentityImpl) getIdentity(), HttpPath.Guild.CREATE_GUILD_BAN, guild.getId(), memberId) {
                 @Override
                 protected Boolean request(Requester requester) {
                     HttpCode code = requester.updateRequestWithBody(request -> request.body(new JSONObject().put("delete-message-days", days)))
                             .performRequest();
                     return code.isOK() || code.isSuccess();
                 }
             };
        } catch (HttpErrorException ex) {
            if (ex.isPermissionException()) { // Missing Permission
                throw new PermissionException(Permission.ADMINISTRATOR, Permission.BAN_MEMBERS);
            } else {
                throw ex;
            }
        }
    }

    private void checkDays(int days) {
        if (days < 0 || days > 7) {
            throw new IllegalArgumentException("The number of days to delete the member's " +
                    "message may not be smaller than 0 or greater than 7! Provided days: "+days);
        }
    }

    @Override
    public AuditAction<Boolean> unbanUser(IUser user) {
        return unbanUser(user.getId());
    }

    @Override
    public AuditAction<Boolean> unbanUser(String memberId) {
        try {
            return new AuditAction<Boolean>((IdentityImpl) getIdentity(), HttpPath.Guild.REMOVE_GUILD_BAN, guild.getId(), memberId) {
                @Override
                protected Boolean request(Requester requester) {
                    HttpCode code = requester.performRequest();
                    return code.isOK() || code.isSuccess();
                }
            };
        } catch (HttpErrorException ex) {
            if (ex.isPermissionException()) { // Missing Permission
                throw new PermissionException(Permission.ADMINISTRATOR, Permission.BAN_MEMBERS);
            } else if (ex.getCode().equals(HttpCode.NOT_FOUND)) {
                throw new ErrorResponseException(ErrorResponse.UNKNOWN_USER, "Unknown user to unban! ID: "+memberId);
            } else {
                throw ex;
            }
        }
    }

    @Override
    public AuditAction<ITextChannel> createTextChannel(ITextChannel channel) {
        // Fires Channel Create Gateway Event
        // Automatically build the channel and add to identity in this event.
        // So the event will not override the channel built here.
        if (!guild.getSelfMember().hasPermissions(true, Permission.MANAGE_CHANNELS)) {
            throw new PermissionException(Permission.ADMINISTRATOR, Permission.MANAGE_CHANNELS);
        }

        return new AuditAction<ITextChannel>((IdentityImpl) getIdentity(), HttpPath.Guild.CREATE_GUILD_CHANNEL, guild.getId()) {
            @Override
            protected ITextChannel request(Requester requester) {
                JSONObject ch = requester.updateRequestWithBody(request -> request.body(((TextChannel) channel).toJson())).getAsJSONObject();
                return (ITextChannel) new ObjectBuilder((IdentityImpl) getIdentity()).buildGuildChannel(ch);
            }
        };
    }

    @Override
    public AuditAction<IVoiceChannel> createVoiceChannel(IVoiceChannel channel) {
        // Fires Channel Create Gateway Event
        // See documentations above.
        if (!guild.getSelfMember().hasPermissions(true, Permission.MANAGE_CHANNELS)) {
            throw new PermissionException(Permission.ADMINISTRATOR, Permission.MANAGE_CHANNELS);
        }

        return new AuditAction<IVoiceChannel>((IdentityImpl) getIdentity(), HttpPath.Guild.CREATE_GUILD_CHANNEL, guild.getId()) {
            @Override
            protected IVoiceChannel request(Requester requester) {
                JSONObject ch = requester.updateRequestWithBody(request -> request.body(((VoiceChannel) channel).toJson())).getAsJSONObject();
                return (IVoiceChannel) new ObjectBuilder((IdentityImpl) getIdentity()).buildGuildChannel(ch);
            }
        };
    }

    @Override
    public AuditAction<Void> deleteGuildChannel(IGuildChannel channel) {
        if (!getGuild().getSelfMember().hasPermissions(true, Permission.MANAGE_CHANNELS)) {
            throw new PermissionException(Permission.ADMINISTRATOR, Permission.MANAGE_CHANNELS);
        }

        if (channel instanceof ITextChannel) {
            if (((ITextChannel) channel).isDefaultChannel()) {
                throw new PermissionException("Cannot delete the default channel of a guild!");
            }
        }

        return new AuditAction<Void>((IdentityImpl) getIdentity(), HttpPath.Channel.DELETE_CHANNEL, channel.getId()) {
            @Override
            protected Void request(Requester requester) {
                requester.performRequest();
                return null;
            }
        };
    }

    @Override
    public AuditAction<IRole> createRole(IRole role) {
        if (!guild.getSelfMember().hasPermissions(true, Permission.MANAGE_ROLES)) {
            throw new PermissionException(Permission.ADMINISTRATOR, Permission.MANAGE_ROLES);
        }

        // Fires Guild Role Create event
        return new AuditAction<IRole>((IdentityImpl) getIdentity(), HttpPath.Guild.CREATE_GUILD_ROLE, guild.getId()) {
            @Override
            protected IRole request(Requester requester) {
                JSONObject r = requester.updateRequestWithBody(request -> request.body(((Role) role).toJson())).getAsJSONObject();
                return new ObjectBuilder((IdentityImpl) getIdentity()).buildRole(r, guild);
            }
        };
    }

    @Override
    public AuditAction<Void> deleteRole(IRole role) {
        if (role == null || !role.getGuild().equals(guild)) {
            throw new ErrorResponseException(ErrorResponse.UNKNOWN_ROLE);
        }

        // Fires Guild Role Delete event
        try {
            return new AuditAction<Void>((IdentityImpl) getIdentity(), HttpPath.Guild.DELETE_GUILD_ROLE, guild.getId(), role.getId()) {
                @Override
                protected Void request(Requester requester) {
                    requester.performRequest();
                    return null;
                }
            };
        } catch (HttpErrorException ex) {
            if (ex.isPermissionException()) {
                throw new PermissionException(Permission.MANAGE_ROLES);
            } else {
                throw ex;
            }
        }
    }
}
