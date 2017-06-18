package org.alienideology.jcord.handle.permission;

import com.sun.istack.internal.Nullable;
import org.alienideology.jcord.handle.channel.IGuildChannel;
import org.alienideology.jcord.internal.object.DiscordObject;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.alienideology.jcord.internal.object.guild.Member;
import org.alienideology.jcord.internal.object.guild.Role;

import java.util.Collection;

/**
 * @author AlienIdeology
 */
public class PermOverwrite extends DiscordObject {

    private String id;
    private Guild guild;

    private Type overwriteType;
    private Role role;
    private Member member;

    private long allow;
    private long denied;

    public PermOverwrite(IdentityImpl identity, String guild_id, String id, long allow, long denied) {
        super(identity);
        this.guild = (Guild) identity.getGuild(guild_id);
        this.id = id;
        setType();
        this.allow = allow;
        this.denied = denied;
    }

    public String getId() {
        return id;
    }

    public Guild getGuild() {
        return guild;
    }

    public boolean isRoleOverwrite() {
        return overwriteType.equals(Type.ROLE);
    }

    public Type getOverwriteType() {
        return overwriteType;
    }

    @Nullable
    public Role getRole() {
        return role;
    }

    @Nullable
    public Member getMember() {
        return member;
    }

    public long getAllow() {
        return allow;
    }

    public Collection<Permission> getAllowedPermissions() {
        return Permission.getPermissionsByLong(allow);
    }

    public long getDenied() {
        return denied;
    }

    public Collection<Permission> getDeniedPermissions() {
        return Permission.getPermissionsByLong(denied);
    }

    private void setType() {
        role = (Role) guild.getRole(id);
        member = (Member) guild.getMember(id);
        overwriteType = role != null ? Type.ROLE : Type.MEMBER;
    }

    @Override
    public String toString() {
        return "PermOverwrite{" +
                "identity=" + identity +
                ", id='" + id + '\'' +
                ", overwriteType=" + overwriteType +
                ", allow=" + allow +
                ", denied=" + denied +
                '}';
    }

    public enum Type {
        /**
         * Permission overwrite for a {@link org.alienideology.jcord.handle.guild.IRole} in a {@link IGuildChannel}.
         */
        ROLE,

        /**
         * Permission overwrite for a {@link org.alienideology.jcord.handle.guild.IMember} in a {@link IGuildChannel}.
         */
        MEMBER;

        @Nullable
        public static Type getByKey(String key) {
            for (Type type : values()) {
                if (type.name().toLowerCase().equals(key))
                    return type;
            }
            return null;
        }
    }

}
