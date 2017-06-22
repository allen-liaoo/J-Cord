package org.alienideology.jcord.handle.permission;

import com.sun.istack.internal.Nullable;
import org.alienideology.jcord.handle.ISnowFlake;
import org.alienideology.jcord.handle.channel.IGuildChannel;
import org.alienideology.jcord.internal.object.Buildable;
import org.alienideology.jcord.internal.object.DiscordObject;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.alienideology.jcord.internal.object.guild.Member;
import org.alienideology.jcord.internal.object.guild.Role;
import org.json.JSONObject;

import java.util.Collection;

/**
 * @author AlienIdeology
 */
public class PermOverwrite extends DiscordObject implements ISnowFlake, Buildable {

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

    /**
     * Constructor for buildable PermOverwrite
     */
    public PermOverwrite(String id, Type overwriteType, long allow, long denied) {
        super(null);
        this.id = id;
        this.overwriteType = overwriteType;
        this.allow = allow;
        this.denied = denied;
    }

    @Override
    public JSONObject toJson() {
        return new JSONObject()
                .put("id", id)
                .put("type", overwriteType.name().toLowerCase())
                .put("allow", allow)
                .put("deny", denied);
    }

    /**
     * Get the guild this permission overwrite happened in.
     *
     * @return The guild.
     */
    public Guild getGuild() {
        return guild;
    }

    /**
     * @return True if this permission overwrite is for a role.
     *      Returns false if the overwrite is for a member.
     */
    public boolean isRoleOverwrite() {
        return overwriteType.equals(Type.ROLE);
    }

    /**
     * Get the overwrite type.
     *
     * @return Either role or member type.
     */
    public Type getOverwriteType() {
        return overwriteType;
    }

    /**
     * Get the role of permission overwrite.
     * @return The role, or null if this is a member permission overwrite.
     */
    @Nullable
    public Role getRole() {
        return role;
    }

    /**
     * Get the member of permission overwrite.
     * @return The member, or null if this is a member role overwrite.
     */
    @Nullable
    public Member getMember() {
        return member;
    }

    /**
     * Get the long value of the allowed permissions.
     *
     * @return The allowed permissions.
     */
    public long getAllow() {
        return allow;
    }

    /**
     * Get a collection of allowed permissions.
     *
     * @return The allowed permissions.
     */
    public Collection<Permission> getAllowedPermissions() {
        return Permission.getPermissionsByLong(allow);
    }

    /**
     * Get the long calue of the denied permissions.
     *
     * @return The denied permissions.
     */
    public long getDenied() {
        return denied;
    }

    /**
     * Get a collection of denied permissions.
     *
     * @return The denied permissions.
     */
    public Collection<Permission> getDeniedPermissions() {
        return Permission.getPermissionsByLong(denied);
    }

    @Override
    public String getId() {
        return id;
    }

    private void setType() {
        role = (Role) guild.getRole(id);
        member = (Member) guild.getMember(id);
        overwriteType = role != null ? Type.ROLE : Type.MEMBER;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PermOverwrite)) return false;
        if (!super.equals(o)) return false;

        PermOverwrite overwrite = (PermOverwrite) o;

        if (allow != overwrite.allow) return false;
        if (denied != overwrite.denied) return false;
        if (!id.equals(overwrite.id)) return false;
        if (!guild.equals(overwrite.guild)) return false;
        if (overwriteType != overwrite.overwriteType) return false;
        if (role != null ? !role.equals(overwrite.role) : overwrite.role != null) return false;
        return member != null ? member.equals(overwrite.member) : overwrite.member == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + id.hashCode();
        result = 31 * result + guild.hashCode();
        result = 31 * result + overwriteType.hashCode();
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (member != null ? member.hashCode() : 0);
        result = 31 * result + (int) (allow ^ (allow >>> 32));
        result = 31 * result + (int) (denied ^ (denied >>> 32));
        return result;
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

    /**
     * Types of permission overwrite
     */
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
