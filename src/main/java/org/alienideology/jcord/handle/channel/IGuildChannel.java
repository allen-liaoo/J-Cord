package org.alienideology.jcord.handle.channel;

import org.alienideology.jcord.handle.ISnowFlake;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.managers.IChannelManager;
import org.alienideology.jcord.handle.managers.IInviteManager;
import org.alienideology.jcord.handle.permission.OverwriteCheckable;
import org.alienideology.jcord.handle.permission.PermOverwrite;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * IGuildChannel - A Channel that exist in a guild
 * @author AlienIdeology
 */
public interface IGuildChannel extends IChannel, OverwriteCheckable, ISnowFlake {

    /**
     * The minimum length of the channel's name.
     */
    int CHANNEL_NAME_LENGTH_MIN = 2;
    /**
     * The maximum length of the channel's name.
     */
    int CHANNEL_NAME_LENGTH_MAX = 100;

    /**
     * Get the guild this channel belongs to.
     *
     * @return The guild
     */
    IGuild getGuild();

    /**
     * Get the managers that manages this GuildChannel.
     *
     * @return The channel managers.
     */
    IChannelManager getChannelManager();

    /**
     * Get the IInviteManager that manages invites from this GuildChannel.
     * All the invite actions can be found in the manager.
     *
     * @return The invite manager.
     */
    IInviteManager getInviteManager();

    /**
     * Ge the name of this channel.
     *
     * @return The string name
     */
    String getName();

    /**
     * Get the position of this guild channel in a channel list.
     * The position of a channel can be calculated by counting down the channel list, starting from 1.
     *
     * @return The integer value of the position.
     */
    int getPosition();

    /**
     * Get a collection of {@link PermOverwrite} objects.
     *
     * @return A list of permission overwrites.
     */
    Collection<PermOverwrite> getPermOverwrites();

    /**
     * Get a collection of {@link PermOverwrite} objects that are role overwrites.
     *
     * @return A list of role permission overwrites.
     */
    default Collection<PermOverwrite> getRolePermOverwrites() {
        return getPermOverwrites().stream().filter(PermOverwrite::isRoleOverwrite).collect(Collectors.toList());
    }

    /**
     * Get the permission overwrite of a role.
     *
     * @param roleId The role ID.
     * @return The permission overwrite.
     */
    @Nullable
    default PermOverwrite getRolePermOverwrite(String roleId) {
        for (PermOverwrite overwrite : getRolePermOverwrites()) {
            if (overwrite.getId().equals(roleId))
                return overwrite;
        }
        return null;
    }

    /**
     * Get a collection of {@link PermOverwrite} objects that are member overwrites.
     *
     * @return A list of member permission overwrites.
     */
    default Collection<PermOverwrite> getMemberPermOverwrites() {
        return getPermOverwrites().stream().filter(over -> !over.isRoleOverwrite()).collect(Collectors.toList());
    }

    /**
     * Get the permission overwrite of a member.
     *
     * @param memberId The member ID.
     * @return The permission overwrite.
     */
    @Nullable
    default PermOverwrite getMemberPermOverwrite(String memberId) {
        for (PermOverwrite overwrite : getMemberPermOverwrites()) {
            if (overwrite.getId().equals(memberId))
                return overwrite;
        }
        return null;
    }

}
