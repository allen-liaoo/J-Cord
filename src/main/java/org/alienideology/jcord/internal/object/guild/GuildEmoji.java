package org.alienideology.jcord.internal.object.guild;

import org.alienideology.jcord.handle.guild.IGuildEmoji;
import org.alienideology.jcord.handle.guild.IRole;
import org.alienideology.jcord.internal.gateway.HttpPath;
import org.alienideology.jcord.internal.object.DiscordObject;
import org.alienideology.jcord.internal.object.IdentityImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author AlienIdeology
 */
public final class GuildEmoji extends DiscordObject implements IGuildEmoji {

    private final Guild guild;
    private final String id;

    private String name;
    private final String image;

    private List<Role> roles;
    private boolean requireColon;

    /**
     * Constructor for global guild emojis
     */
    public GuildEmoji(IdentityImpl identity, String id, String name) {
        this(identity, null, id, name, new ArrayList<>(), false);
    }

    /**
     * Default Constructor
     */
    public GuildEmoji(IdentityImpl identity, Guild guild, String id, String name, List<Role> roles, boolean requireColon) {
        super(identity);
        this.guild = guild;
        this.id = id;
        this.name = name;
        this.image = String.format(HttpPath.EndPoint.EMOJI_ICON, id);
        this.roles = roles;
        this.requireColon = requireColon;
    }

    @Override
    public Guild getGuild() {
        return guild;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getImage() {
        return image;
    }

    @Override
    public boolean isRequireColon() {
        return requireColon;
    }

    @Override
    public List<IRole> getUsableRoles() {
        return Collections.unmodifiableList(roles);
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
        return "GuildEmoji{" +
                "guild=" + guild +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
