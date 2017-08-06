package org.alienideology.jcord.internal.object.guild;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.guild.IGuildEmoji;
import org.alienideology.jcord.handle.guild.IRole;
import org.alienideology.jcord.internal.object.DiscordObject;
import org.alienideology.jcord.internal.rest.HttpPath;

import java.util.Collections;
import java.util.List;

/**
 * @author AlienIdeology
 */
public final class GuildEmoji extends DiscordObject implements IGuildEmoji {

    private final String id;
    private final IGuild guild;

    private String name;
    private final String image;

    private List<Role> roles;
    private boolean requireColon;

    /**
     * Constructor for global guild emojis
     */
    public GuildEmoji(Identity identity, String id, String name) {
        super(identity);
        this.guild = null;
        this.id = id;
        this.image = String.format(HttpPath.EndPoint.EMOJI_ICON, id);
        setName(name);
    }

    /**
     * Default Constructor
     */
    public GuildEmoji(Identity identity, IGuild guild, String id) {
        super(identity);
        this.guild = guild;
        this.id = id;
        this.image = String.format(HttpPath.EndPoint.EMOJI_ICON, id);
    }

    @Override
    public IGuild getGuild() {
        return guild;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getImageUrl() {
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

    public GuildEmoji setName(String name) {
        this.name = name;
        return this;
    }

    public GuildEmoji setRoles(List<Role> roles) {
        this.roles = roles;
        return this;
    }

    public GuildEmoji setRequireColon(boolean requireColon) {
        this.requireColon = requireColon;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof GuildEmoji) && ((GuildEmoji) o).getId().equals(id);
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
