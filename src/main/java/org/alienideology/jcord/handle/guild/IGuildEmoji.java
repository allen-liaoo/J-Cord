package org.alienideology.jcord.handle.guild;

import org.alienideology.jcord.handle.IDiscordObject;
import org.alienideology.jcord.handle.IMention;
import org.alienideology.jcord.handle.ISnowFlake;
import org.alienideology.jcord.internal.object.guild.Role;

import java.util.List;

/**
 * GuildEmoji - A custom emoji that can be used within a guild.
 * @author AlienIdeology
 */
public interface IGuildEmoji extends IDiscordObject, ISnowFlake, IMention {

    /**
     * Get the guild this emoji belongs to.
     *
     * @return The guild.
     */
    IGuild getGuild();

    /**
     * Get the name of this emoji.
     *
     * @return The string name.
     */
    String getName();

    /**
     * Get the image link of this emoji.
     *
     * @return The string image url.
     */
    String getImage();

    /**
     * @return True if it is requried to use colon when typing this emoji.
     */
    boolean isRequireColon();

    /**
     * @return A list of roles that can access this emoji.
     */
    List<IRole> getUsableRoles();

    /**
     * Check if a member can use this emoji.
     *
     * @param member The member
     * @return True if the member can use this emoji in a conversation.
     */
    default boolean canBeUseBy(IMember member) {
        for (IRole role : member.getRoles()) {
            if (getUsableRoles().contains(role))
                return true;
        }
        return false;
    }

    /**
     * Check if a role can access this emoji.
     *
     * @param role The role
     * @return True if the role can use this emoji.
     */
    default boolean canBeUseBy(IRole role) {
        return  getUsableRoles().contains(role);
    }
    
    @Override
    default String mention() {
        return "<:"+getName()+":"+getId()+">";
    }
}
