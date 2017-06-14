package org.alienideology.jcord.internal.object.guild;

import org.alienideology.jcord.handle.IMention;
import org.alienideology.jcord.handle.ISnowFlake;
import org.alienideology.jcord.internal.Identity;
import org.alienideology.jcord.internal.gateway.HttpPath;
import org.alienideology.jcord.internal.object.DiscordObject;
import org.alienideology.jcord.internal.object.Guild;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * GuildEmoji - A custom emoji that can be used within a guild.
 * @author AlienIdeology
 */
public class GuildEmoji extends DiscordObject implements ISnowFlake, IMention {

    private final Guild guild;
    private final String id;

    private String name;
    private final String image;

    private List<Role> roles;
    private boolean requireColon;

    /**
     * Constructor for global guild emojis
     */
    public GuildEmoji(Identity identity, String id, String name) {
        this(identity, null, id, name, new ArrayList<>(), false);
    }

    /**
     * Default Constructor
     */
    public GuildEmoji(Identity identity, Guild guild, String id, String name, List<Role> roles, boolean requireColon) {
        super(identity);
        this.guild = guild;
        this.id = id;
        this.name = name;
        this.image = String.format(HttpPath.EndPoint.EMOJI_ICON, id);
        this.roles = roles;
        this.requireColon = requireColon;
    }

    public Guild getGuild() {
        return guild;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
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
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + guild.hashCode();
        result = 31 * result + id.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ID: "+id+"\tName: "+name;
    }

}
