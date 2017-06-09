package org.alienideology.jcord.object.guild;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.object.DiscordObject;
import org.alienideology.jcord.object.Mention;
import org.alienideology.jcord.object.SnowFlake;

import java.util.Collections;
import java.util.List;

/**
 * GuildEmoji - A custom emoji that can be used within a guild.
 * @author AlienIdeology
 */
public class GuildEmoji extends DiscordObject implements SnowFlake, Mention {

    private final Guild guild;
    private final String id;
    private String name;

    private List<Role> roles;
    private boolean requireColon;

    public GuildEmoji(Identity identity, Guild guild, String id, String name, List<Role> roles, boolean requireColon) {
        super(identity);
        this.guild = guild;
        this.id = id;
        this.name = name;
        this.roles = roles;
        this.requireColon = requireColon;
    }

    public Guild getGuild() {
        return guild;
    }

    public String getName() {
        return name;
    }

    public boolean isRequireColon() {
        return requireColon;
    }

    public List<Role> getUsableRoles() {
        return Collections.unmodifiableList(roles);
    }

    public boolean canBeUseBy(Member member) {
        for (Role role : member.getRoles()) {
            if (roles.contains(role))
                return true;
        }
        return false;
    }

    public boolean canBeUseBy(Role role) {
        if (roles.contains(role)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String mention() {
        return "<:"+name+":"+id+">";
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof GuildEmoji) && ((GuildEmoji) obj).getId().equals(this.id);
    }

    @Override
    public String toString() {
        return "ID: "+id+"\tName: "+name;
    }
}
