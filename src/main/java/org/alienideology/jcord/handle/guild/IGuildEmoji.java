package org.alienideology.jcord.handle.guild;

import org.alienideology.jcord.handle.IDiscordObject;
import org.alienideology.jcord.handle.IMention;
import org.alienideology.jcord.handle.ISnowFlake;
import org.alienideology.jcord.internal.object.Guild;
import org.alienideology.jcord.internal.object.guild.Member;
import org.alienideology.jcord.internal.object.guild.Role;

import java.util.Collections;
import java.util.List;

/**
 * @author AlienIdeology
 */
public interface IGuildEmoji extends IDiscordObject, ISnowFlake, IMention {

    Guild getGuild();

    String getName();

    String getImage();

    boolean isRequireColon();

    List<Role> getUsableRoles();

    boolean canBeUseBy(Member member);

    boolean canBeUseBy(Role role);
    
    @Override
    default String mention() {
        return "<:"+getName()+":"+getId()+">";
    }
}
