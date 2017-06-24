package org.alienideology.jcord.internal.object.managers;

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
import org.alienideology.jcord.util.DataUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
    public void modifyName(String name) {
        if (name == null || name.isEmpty()) return;
        requestModify(new JSONObject().put("name", name));
    }

    @Override
    public void modifyOwner(IMember newOwner) {
        if (newOwner == null || !newOwner.getGuild().equals(guild)) return;
        requestModify(new JSONObject().put("owner_id", newOwner.getId()));
    }

    @Override
    public void modifyRegion(Region region) {
        if (region == Region.UNKNOWN) return;
        try {
            requestModify(new JSONObject().put("region", region.key));
        } catch (HttpErrorException ex) {
            if (ex.getCode().equals(HttpCode.BAD_REQUEST)) {
                throw new HttpErrorException(HttpCode.BAD_REQUEST, "Invalid VIP Region!");
            }
        }
    }

    @Override
    public void modifyVerification(IGuild.Verification verification) {
        if (verification == IGuild.Verification.UNKNOWN) return;
        requestModify(new JSONObject().put("verification_level", verification.key));
    }

    @Override
    public void modifyNotification(IGuild.Notification notification) {
        if (notification == IGuild.Notification.UNKNOWN) return;
        requestModify(new JSONObject().put("default_message_notifications", notification.key));
    }

    @Override
    public void modifyAFKTimeout(IGuild.AFKTimeout afkTimeout) {
        if (afkTimeout == IGuild.AFKTimeout.UNKNOWN) return;
        requestModify(new JSONObject().put("afk_timeout", afkTimeout.timeout));
    }

    @Override
    public void modifyAFKChannel(IVoiceChannel afkChannel) {
        if (afkChannel == null) return;
        modifyAFKChannel(afkChannel.getId());
    }

    @Override
    public void modifyAFKChannel(String afkChannelId) {
        if (afkChannelId == null || afkChannelId.isEmpty()) return;
        requestModify(new JSONObject().put("afk_channel_id", afkChannelId));
    }

    @Override
    public void modifyIcon(BufferedImage image) throws IOException {
        String encoding = DataUtils.encodeIcon(image);
        requestModify(new JSONObject().put("icon", encoding));
    }

    @Override
    public void modifyIcon(String imagePath) throws IOException {
        modifyIcon(ImageIO.read(new File(imagePath)));
    }

    @Override
    public void modifySplash(BufferedImage image) throws IOException {
        String encoding = DataUtils.encodeIcon(image);
        requestModify(new JSONObject().put("splash", encoding));
    }

    @Override
    public void modifySplash(String imagePath) throws IOException {
        modifySplash(ImageIO.read(new File(imagePath)));
    }

    private void requestModify(JSONObject json) {
        try {
            new Requester((IdentityImpl) getIdentity(), HttpPath.Guild.MODIFY_GUILD).request(guild.getId())
                    .updateRequestWithBody(request -> request.header("Content-Type", "application/json").body(json)).performRequest();
        } catch (HttpErrorException ex) {
            if (ex.isPermissionException()) {
                throw new PermissionException(Permission.ADMINISTRATOR, Permission.MANAGE_SERVER);
            } else {
                throw ex;
            }
        }
    }

    @Override
    public boolean kickMember(IMember member) {
        return kickMember(member.getId());
    }

    @Override
    public boolean kickMember(String memberId) {
        IMember member = guild.getMember(memberId);
        // Validate member
        if (member == null) {
            throw new ErrorResponseException(ErrorResponse.UNKNOWN_MEMBER, "Unknown member to kick! ID: "+memberId);
        }
        if (member.equals(guild.getSelfMember())) {
            throw new IllegalArgumentException("Cannot kick the identity itself from a guild!");
        }

        // Checks hierarchy
        if (member.isOwner() && !guild.getSelfMember().isOwner()) {
            throw new HigherHierarchyException(HierarchyType.OWNER);
        }
        if (!guild.getSelfMember().canModify(member)) {
            throw new HigherHierarchyException(HierarchyType.MEMBER);
        }

        HttpCode code;
        try {
            code = new Requester((IdentityImpl) getIdentity(), HttpPath.Guild.REMOVE_GUILD_MEMBER).request(guild.getId(), memberId)
                    .performRequest();
        } catch (HttpErrorException ex) {
            if (ex.isPermissionException()) {
                throw new PermissionException(Permission.KICK_MEMBERS);
            } else {
                throw ex;
            }
        }

        return code.isOK() || code.isSuccess();
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
                throw new PermissionException(Permission.KICK_MEMBERS);
            } else {
                throw ex;
            }
        }
    }

    @Override
    public void pruneMembers() {
        pruneMembers(7);
    }

    @Override
    public void pruneMembers(int days) {
        if (days <= 0 || days > 30) {
            throw new IllegalArgumentException("The days for getting prunable count may not be smaller or equal to 0, or greater than 30! Provided days: "+days);
        }

        // This fires multiple Guild Remove Gateway event
        // Ignored the returning JSONObject (Pruned members)
        try {
            new Requester((IdentityImpl) getIdentity(), HttpPath.Guild.BEGIN_GUILD_PRUNE).request(guild.getId(), String.valueOf(days))
                    .performRequest();
        } catch (HttpErrorException ex) {
            if (ex.isPermissionException()) {
                throw new PermissionException(Permission.KICK_MEMBERS);
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
    public boolean banMember(IMember member) {
        return banMember(member.getId(), 7);
    }

    @Override
    public boolean banMember(IMember member, int days) {
        checkDays(days);
        return banMember(member.getId(), days);
    }

    @Override
    public boolean banMember(String memberId) {
        return banMember(memberId, 7);
    }

    @Override
    public boolean banMember(String memberId, int days) {
        IMember member = guild.getMember(memberId);
        // Validate member
        if (member == null) {
            throw new ErrorResponseException(ErrorResponse.UNKNOWN_MEMBER, "Unknown member to ban! ID: "+memberId);
        }
        if (member.equals(guild.getSelfMember())) {
            throw new IllegalArgumentException("Cannot kick the identity itself from a guild!");
        }

        // Checks hierarchy
        if (member.isOwner() && !guild.getSelfMember().isOwner()) {
            throw new HigherHierarchyException(HierarchyType.OWNER);
        }
        if (!guild.getSelfMember().canModify(member)) {
            throw new HigherHierarchyException(HierarchyType.MEMBER);
        }

        checkDays(days);
        HttpCode code;
        try {
             code = new Requester((IdentityImpl) getIdentity(), HttpPath.Guild.CREATE_GUILD_BAN).request(guild.getId(), memberId)
                    .updateRequestWithBody(request -> request.body(new JSONObject().put("delete-message-days", days)))
                    .performRequest();
        } catch (HttpErrorException ex) {
            if (ex.isPermissionException()) { // Missing Permission
                throw new PermissionException(Permission.ADMINISTRATOR, Permission.BAN_MEMBERS);
            } else {
                throw ex;
            }
        }
        return code.isOK() || code.isSuccess();
    }

    private void checkDays(int days) {
        if (days < 0 || days > 7) {
            throw new IllegalArgumentException("The number of days to delete the member's " +
                    "message may not be smaller than 0 or greater than 7! Provided days: "+days);
        }
    }

    @Override
    public boolean unbanUser(IUser user) {
        return unbanUser(user.getId());
    }

    @Override
    public boolean unbanUser(String memberId) {
        HttpCode code;
        try {
            code = new Requester((IdentityImpl) getIdentity(), HttpPath.Guild.REMOVE_GUILD_BAN).request(guild.getId(), memberId)
                    .performRequest();
        } catch (HttpErrorException ex) {
            if (ex.isPermissionException()) { // Missing Permission
                throw new PermissionException(Permission.ADMINISTRATOR, Permission.BAN_MEMBERS);
            } else if (ex.getCode().equals(HttpCode.NOT_FOUND)) {
                throw new ErrorResponseException(ErrorResponse.UNKNOWN_USER, "Unknown user to unban! ID: "+memberId);
            } else {
                throw ex;
            }
        }
        return code.isOK() || code.isSuccess();
    }

    @Override
    public ITextChannel createTextChannel(ITextChannel channel) {
        // Fires Channel Create Gateway Event
        // Automatically build the channel and add to identity in this method.
        // So the event will not override the channel built here.
        return (ITextChannel) createChannel(((TextChannel) channel).toJson());
    }

    @Override
    public IVoiceChannel createVoiceChannel(IVoiceChannel channel) {
        // Fires Channel Create Gateway Event
        // See documentations above.
        return (IVoiceChannel) createChannel(((VoiceChannel) channel).toJson());
    }

    private IGuildChannel createChannel(JSONObject json) {
        if (!guild.getSelfMember().hasPermissions(true, Permission.MANAGE_CHANNELS)) {
            throw new PermissionException(Permission.ADMINISTRATOR, Permission.MANAGE_CHANNELS);
        }

        JSONObject channel = new Requester((IdentityImpl) getIdentity(), HttpPath.Guild.CREATE_GUILD_CHANNEL).request(guild.getId())
                .updateRequestWithBody(request -> request.body(json)).getAsJSONObject();
        return new ObjectBuilder((IdentityImpl) getIdentity()).buildGuildChannel(channel);
    }

    @Override
    public void deleteGuildChannel(IGuildChannel channel) {
        if (!getGuild().getSelfMember().hasPermissions(true, Permission.MANAGE_CHANNELS)) {
            throw new PermissionException(Permission.ADMINISTRATOR, Permission.MANAGE_CHANNELS);
        }

        if (channel instanceof ITextChannel) {
            if (((ITextChannel) channel).isDefaultChannel()) {
                throw new PermissionException("Cannot delete the default channel of a guild!");
            }
        }

        new Requester((IdentityImpl) getIdentity(), HttpPath.Channel.DELETE_CHANNEL)
                .request(channel.getId()).performRequest();
    }

    @Override
    public IRole createRole(IRole role) {
        if (!guild.getSelfMember().hasPermissions(true, Permission.MANAGE_ROLES)) {
            throw new PermissionException(Permission.ADMINISTRATOR, Permission.MANAGE_ROLES);
        }

        JSONObject newRole = ((Role) role).toJson();

        // Fires Guild Role Create event
        JSONObject roleJson = new Requester((IdentityImpl) getIdentity(), HttpPath.Guild.CREATE_GUILD_ROLE).request(guild.getId())
                .updateRequestWithBody(request -> request.body(newRole)).getAsJSONObject();

        return new ObjectBuilder((IdentityImpl) getIdentity()).buildRole(roleJson, guild);

    }

    @Override
    public void deleteRole(IRole role) {
        if (role == null || !role.getGuild().equals(guild)) {
            throw new ErrorResponseException(ErrorResponse.UNKNOWN_ROLE);
        }

        // Fires Guild Role Delete event
        try {
            new Requester((IdentityImpl) getIdentity(), HttpPath.Guild.DELETE_GUILD_ROLE).request(guild.getId(), role.getId())
                    .performRequest();
        } catch (HttpErrorException ex) {
            if (ex.isPermissionException()) {
                throw new PermissionException(Permission.MANAGE_ROLES);
            } else {
                throw ex;
            }
        }
    }
}
