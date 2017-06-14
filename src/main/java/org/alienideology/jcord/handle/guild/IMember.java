package org.alienideology.jcord.handle.guild;

import org.alienideology.jcord.handle.IDiscordObject;
import org.alienideology.jcord.handle.IMention;
import org.alienideology.jcord.handle.ISnowFlake;
import org.alienideology.jcord.internal.object.Guild;
import org.alienideology.jcord.handle.Permission;
import org.alienideology.jcord.internal.object.guild.Role;
import org.alienideology.jcord.internal.object.user.User;

import java.time.OffsetDateTime;
import java.util.*;

/**
 * @author AlienIdeology
 */
public interface IMember extends IDiscordObject, ISnowFlake, IMention {

    /**
    * Check if this member have all the given permissions
    * @param permissions The varargs of permission enums to be checked
    * @return True if the member have all given permissions
    */
    boolean hasAllPermissions (Permission... permissions);

    /**
    * Check if this member have one of the given permissions.
    * @param checkAdmin If true, returns true if the member have administrator permission.
    * @param permissions The varargs of permission enums to be checked.
    * @return True if the member have one of the given permissions.
    */
    boolean hasPermissions (boolean checkAdmin, Permission... permissions) ;

    /**
    * Check if this member have one of the given permissions
    * @deprecated #hasPermissions(boolean, Permission...) To check with the member having administrator permission.
    * @see #hasAllPermissions(Permission...) To check if this member have all the permissions.
    * @param permissions The varargs of permission enums to be checked
    * @return True if the member have one of the given permissions
    */
    @Deprecated
    boolean hasPermissions (Permission... permissions);

    Guild getGuild();

    User getUser();

    String getNickname();

    OffsetDateTime getJoinedDate();

    Role getHighestRole();

    List<Role> getRoles();

    List<Permission> getPermissions();

    boolean isOwner();

    boolean isDeafened();

    boolean isMuted();

    @Override
    default String mention() {
        return "<!@"+getId()+">";
    }

}
