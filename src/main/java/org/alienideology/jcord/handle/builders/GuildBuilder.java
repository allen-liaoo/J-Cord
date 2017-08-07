package org.alienideology.jcord.handle.builders;

import org.alienideology.jcord.handle.Icon;
import org.alienideology.jcord.handle.Region;
import org.alienideology.jcord.handle.channel.IGuildChannel;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.guild.IRole;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.alienideology.jcord.internal.object.guild.Role;

import java.util.ArrayList;
import java.util.List;

/**
 * GuildBuilder - A builder for creating a guild. Used by {@link org.alienideology.jcord.handle.managers.IClientManager}.
 *
 * @author AlienIdeology
 * @since 0.1.4
 */
public final class GuildBuilder implements Buildable<GuildBuilder, IGuild> {

    private String name = null;
    private Region region = null;
    private Icon icon;
    private IGuild.Verification verification;
    private IGuild.Notification notification;

    private List<IRole> roles = new ArrayList<>();
    private List<IGuildChannel> channels = new ArrayList<>();

    public GuildBuilder() {
        clear();
    }

    /**
     * Build a guild.
     *
     * @return The guild built, used in {@link org.alienideology.jcord.handle.managers.IClientManager#createGuild(IGuild)}.
     */
    @Override
    public IGuild build() {
        Guild guild = new Guild(null, null, true)
                .setName(name)
                .setRegion(region.key)
                .setIcon(icon == Icon.DEFAULT_ICON ? null : (String) icon.getData())
                .setVerificationLevel(verification.key)
                .setNotificationLevel(notification.key);

        roles.forEach(r -> guild.addRole((Role) r));
        channels.forEach(guild::addGuildChannel);
        return guild;
    }

    @Override
    public GuildBuilder clear() {
        name = null;
        region = null;
        icon = Icon.DEFAULT_ICON;
        verification = null;
        notification = null;
        roles.clear();
        channels.clear();
        return this;
    }

    /**
     * Set the name of this guild.
     *
     * @exception IllegalArgumentException
     *          If the name is not valid. See {@link IGuild#isValidName(String)}.
     *
     * @param name The string name.
     * @return GuildBuilder for chaining.
     */
    public GuildBuilder setName(String name) {
        if (!IGuild.isValidName(name)) {
            throw new IllegalArgumentException("The guild's name is not valid!");
        }
        this.name = name;
        return this;
    }

    /**
     * Set the region of this guild.
     *
     * @exception IllegalArgumentException
     *          If the region is {@link Region#UNKNOWN}.
     *
     * @param region The voice channel' region.
     * @return GuildBuilder for chaining.
     */
    public GuildBuilder setRegion(Region region) {
        if (region.equals(Region.UNKNOWN)) {
            throw new IllegalArgumentException("The guild's region may not be unknown!");
        }
        this.region = region;
        return this;
    }

    /**
     * Set the icon of this guild.
     *
     * @param icon The image file.
     * @return GuildBuilder for chaining.
     */
    public GuildBuilder setIcon(Icon icon) {
        this.icon = icon;
        return this;
    }

    /**
     * Set the verification level of this guild.
     *
     * @exception IllegalArgumentException
     *          If the verification level is {@link IGuild.Verification#UNKNOWN}.
     *
     * @param verification The verification level.
     * @return GuildBuilder for chaining.
     */
    public GuildBuilder setVerificationLevel(IGuild.Verification verification) {
        if (verification.equals(IGuild.Verification.UNKNOWN)) {
            throw new IllegalArgumentException("The guild's verification level may not be unknown!");
        }
        this.verification = verification;
        return this;
    }

    /**
     * Set the notification of this guild.
     *
     * @exception IllegalArgumentException
     *          If the notification level is {@link Region#UNKNOWN}.
     *
     * @param notification The notification level.
     * @return GuildBuilder for chaining.
     */
    public GuildBuilder setNotificationLevel(IGuild.Notification notification) {
        if (notification.equals(IGuild.Notification.UNKNOWN)) {
            throw new IllegalArgumentException("The guild's notification level may not be unknown!");
        }
        this.notification = notification;
        return this;
    }

    // Discord has an internal bug for roles and channels on guild creation
    // These features will be implement after the bug is fixed and deployed
    // TODO: add role and channel on creation (discord bug fix)
//    public GuildBuilder addRole(IRole role) {
//        roles.add(role);
//        return this;
//    }
//
//    public void setRoles(List<IRole> roles) {
//        this.roles = roles;
//    }
//
//    public GuildBuilder addChannel(IGuildChannel channel) {
//        channels.add(channel);
//        return this;
//    }
//
//    public void setChannels(List<IGuildChannel> channels) {
//        this.channels = channels;
//    }

}
