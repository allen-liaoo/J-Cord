package org.alienideology.jcord.handle.guild;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import org.alienideology.jcord.handle.IDiscordObject;
import org.alienideology.jcord.handle.IMention;
import org.alienideology.jcord.handle.ISnowFlake;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.handle.Permission;

import java.time.OffsetDateTime;
import java.util.*;

/**
 * Member - A user representation in a guild.
 * @author AlienIdeology
 */
public interface IMember extends IDiscordObject, ISnowFlake, IMention {

    /**
    * Check if this member have all the given permissions
     *
    * @param permissions The varargs of permission enums to be checked
    * @return True if the member have all given permissions
    */
    boolean hasAllPermissions (Permission... permissions);

    /**
    * Check if this member have one of the given permissions.
     *
    * @param checkAdmin If true, returns true if the member have administrator permission.
    * @param permissions The varargs of permission enums to be checked.
    * @return True if the member have one of the given permissions.
    */
    boolean hasPermissions (boolean checkAdmin, Permission... permissions) ;

    /**
    * Check if this member have one of the given permissions
     *
    * @deprecated #hasPermissions(boolean, Permission...) To check with the member having administrator permission.
    * @see #hasAllPermissions(Permission...) To check if this member have all the permissions.
    * @param permissions The varargs of permission enums to be checked
    * @return True if the member have one of the given permissions
    */
    @Deprecated
    boolean hasPermissions (Permission... permissions);

    /**
     * Get the guild this member belongs to.
     *
     * @return The guild.
     */
    IGuild getGuild();

    /**
     * Get the user instance of the member.
     *
     * @return Theuser
     */
    IUser getUser();

    /**
     * Get the nickname of this member.
     *
     * @return The string nickname, or null if no nickname is found.
     */
    @Nullable
    String getNickname();

    /**
     * Get the name of this member.
     * If this member has a nickname, this method will return the nickname. If not, this will returns the username.
     *
     * @return A not-null string of this member's name.
     */
    @NotNull
    default String getNameNotNull() {
        return getNickname() == null ? getUser().getName() : getNickname();
    }

    /**
     * Get the offset datetime of the date this member join the guild.
     *
     * @return The datetime.
     */
    OffsetDateTime getJoinedDate();

    /**
     * Get the highest role this member has.
     *
     * @return The highest role, or the default (@everyone) role if the member does not have any role.
     */
    @NotNull
    IRole getHighestRole();

    /**
     * @return A list of roles this member has.
     */
    List<IRole> getRoles();

    /**
     * @return A list of permissions this member has.
     */
    List<Permission> getPermissions();

    /**
     * @return True if the member is the owner of the guild.
     */
    default boolean isOwner() {
        return this.equals(getGuild().getOwner());
    }

    /**
     * @return True if this member is deafened (Does not have permission to listen in a voice channel)
     */
    boolean isDeafened();

    /**
     * @return True if this member is muted (Does not have permission to speak in a voice channel)
     */
    boolean isMuted();

    /**
     * Mention this member by nickname (Or username if there is no nickname)
     * @see IUser#mention(boolean)
     *
     * @return The string mention.
     */
    @Override
    default String mention() {
        return "<!@"+getId()+">";
    }

}
