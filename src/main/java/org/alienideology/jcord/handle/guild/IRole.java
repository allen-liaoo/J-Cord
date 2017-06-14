package org.alienideology.jcord.handle.guild;

import org.alienideology.jcord.handle.IDiscordObject;
import org.alienideology.jcord.handle.IMention;
import org.alienideology.jcord.handle.ISnowFlake;
import org.alienideology.jcord.internal.object.Guild;
import org.alienideology.jcord.handle.Permission;

import java.awt.*;

/**
 * @author AlienIdeology
 */
public interface IRole extends IDiscordObject, ISnowFlake, IMention, Comparable<IRole> {

    Guild getGuild();

    String getName();

    Color getColor();

    int getPosition();

    boolean hasPermission (Permission permission);

    /**
    * Check if this role have all the given permissions
    * @param permissions The varargs of permission enums to be checked
    * @return True if the role have all given permissions
    */
    boolean hasAllPermissions (Permission... permissions);

    /**
    * Check if this role have one of the given permissions
    * To check if this member have all the permissions, see #hasAllPermissions(Permission...)
    * @param permissions The varargs of permission enums to be checked
    * @return True if the role have one of the given permissions
    */
    boolean hasPermissions (Permission... permissions);

    long getPermissionsLong();

    java.util.List<Permission> getPermissions();

    boolean isSeparateListed();

    boolean canMention();

    boolean isEveryone();

    @Override
    int compareTo(IRole o);

    @Override
    default String mention(){
        return "<#"+getId()+">";
    }
    
}
