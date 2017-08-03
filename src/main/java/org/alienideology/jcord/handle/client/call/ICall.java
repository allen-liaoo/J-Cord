package org.alienideology.jcord.handle.client.call;

import org.alienideology.jcord.handle.ISnowFlake;
import org.alienideology.jcord.handle.Region;
import org.alienideology.jcord.handle.channel.ICallChannel;
import org.alienideology.jcord.handle.channel.IGroup;
import org.alienideology.jcord.handle.channel.IPrivateChannel;
import org.alienideology.jcord.handle.client.IClientObject;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * ICall - A voice communication between Discord client users.
 *
 * @author AlienIdeology
 */
public interface ICall extends IClientObject, ISnowFlake {

    /**
     * Get the channel id this call is from.
     *
     * @return The channel id.
     */
    @Override
    String getId();

    /**
     * Get the region this call belongs to.
     *
     * @return The call region.
     */
    Region getRegion();

    /**
     * Get the {@link ICallChannel} that this call started in.
     *
     * @return The call channel.
     */
    ICallChannel getChannel();

    /**
     * Get the {@link IGroup} of this call.
     * If this call is in a {@link IPrivateChannel}, then this returns {@code null}.
     *
     * @return The group.
     */
    @Nullable
    IGroup getGroup();

    /**
     * Get the {@link IPrivateChannel} of this call.
     * If this call is in a {@link IGroup}, then this returns {@code null}.
     *
     * @return The private channel.
     */
    @Nullable
    IPrivateChannel getPrivateChannel();

    /**
     * Get a list of users that are still being waited to join the call.
     *
     * @return A list of waiting users.
     */
    List<ICallUser> getWaitingUsers();

    /**
     * Get a list of users that once connected to this call.
     * They may or may not be disconnected.
     *
     * @return A list of connected users.
     */
    List<ICallUser> getConnectedUsers();

    /**
     * Get all users that have connected or still waiting.
     *
     * @return A list of users.
     */
    List<ICallUser> getAllUsers();

    /**
     * Check if this call is a group call.
     *
     * @return True if this is a group call.
     */
    default boolean isGroupCall() {
        return getGroup() != null;
    }

}
