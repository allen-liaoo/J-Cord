package org.alienideology.jcord.internal.object.guild;

import org.alienideology.jcord.handle.Permission;
import org.alienideology.jcord.handle.Region;
import org.alienideology.jcord.handle.channel.IVoiceChannel;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.guild.IGuildManager;
import org.alienideology.jcord.handle.guild.IMember;
import org.alienideology.jcord.internal.exception.HttpErrorException;
import org.alienideology.jcord.internal.exception.PermissionException;
import org.alienideology.jcord.internal.gateway.HttpCode;
import org.alienideology.jcord.internal.gateway.HttpPath;
import org.alienideology.jcord.internal.gateway.Requester;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.util.FileUtils;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author AlienIdeology
 */
// TODO: Finish methods, error handling
public class GuildManager implements IGuildManager {

    private final Guild guild;

    public GuildManager(Guild guild) {
        this.guild = guild;
    }

    @Override
    public Guild getGuild() {
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
            if (ex.getCode() == HttpCode.BAD_REQUEST) {
                throw new HttpErrorException(404, HttpCode.BAD_REQUEST, "Invalid VIP Region!");
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
        String encoding = FileUtils.encodeIcon(image);
        requestModify(new JSONObject().put("icon", encoding));
    }

    @Override
    public void modifyIcon(String imagePath) throws IOException {
        modifyIcon(ImageIO.read(new File(imagePath)));
    }

    @Override
    public void modifySplash(BufferedImage image) throws IOException {
        String encoding = FileUtils.encodeIcon(image);
        requestModify(new JSONObject().put("splash", encoding));
    }

    @Override
    public void modifySplash(String imagePath) throws IOException {
        modifySplash(ImageIO.read(new File(imagePath)));
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
        checkDays(days);
        HttpCode code;
        try {
             code = new Requester((IdentityImpl) getIdentity(), HttpPath.Guild.CREATE_GUILD_BANS).request(guild.getId(), memberId)
                    .updateRequestWithBody(request -> request.body(new JSONObject().put("delete-message-days", days)))
                    .performRequest();
        } catch (HttpErrorException ex) {
            if (ex.getCode().equals(HttpCode.FORBIDDEN)) { // Missing Permission
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

    private void requestModify(JSONObject json) {
        try {
            new Requester((IdentityImpl) getIdentity(), HttpPath.Guild.MODIFY_GUILD).request(guild.getId())
                    .updateRequestWithBody(request -> request.header("Content-Type", "application/json").body(json)).performRequest();
        } catch (HttpErrorException ex) {
            if (ex.getCode().equals(HttpCode.FORBIDDEN)) { // Missing Permission
                throw new PermissionException(Permission.ADMINISTRATOR, Permission.MANAGE_SERVER);
            } else {
                throw ex;
            }
        }
    }

}
