package org.alienideology.jcord.event.handler;

import org.alienideology.jcord.event.guild.role.update.*;
import org.alienideology.jcord.handle.permission.Permission;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.alienideology.jcord.internal.object.guild.Role;
import org.alienideology.jcord.util.log.LogLevel;
import org.json.JSONObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author AlienIdeology
 */
public class GuildRoleUpdateEventHandler extends EventHandler {

    public GuildRoleUpdateEventHandler(IdentityImpl identity) {
        super(identity);
    }

    @Override
    public void dispatchEvent(JSONObject json, int sequence) {
        try {
            Guild guild = (Guild) identity.getGuild(json.getString("guild_id"));
            JSONObject roleJson = json.getJSONObject("role");

            if (guild == null) {
                logger.log(LogLevel.FETAL, "[UNKNOWN GUILD] [GUILD_ROLE_UPDATE_EVENT]");
                return;
            }

            Role role = (Role) guild.getRole(roleJson.getString("id"));

            String name = roleJson.getString("name");
            Color color = roleJson.has("color") ? new Color(roleJson.getInt("color")) : null;
            int position = roleJson.getInt("position");
            long permissions = roleJson.getLong("permissions");
            boolean isSeparateListed = roleJson.has("hoist") && roleJson.getBoolean("hoist");
            boolean canMention = roleJson.has("mentionable")&& roleJson.getBoolean("mentionable");

            if (!Objects.equals(role.getName(), name)) {
                String oldname = role.getName();
                role.setName(name);
                dispatchEvent(new GuildRoleNameUpdateEvent(identity, guild, sequence, role, oldname));
            }

            if (!Objects.equals(role.getPosition(), position)) {
                int oldpos = role.getPosition();
                role.setPosition(position);
                dispatchEvent(new GuildRolePositionUpdateEvent(identity, guild, sequence, role, oldpos));
            }

            if (!Objects.equals(role.getColor(), color)) {
                Color color1 = role.getColor();
                role.setColor(color);
                dispatchEvent(new GuildRoleColorUpdateEvent(identity, guild, sequence, role, color1));
            }

            if (role.isSeparateListed() != isSeparateListed) {
                dispatchEvent(new GuildRoleSeparateListedUpdateEvent(identity, guild, sequence, role, !isSeparateListed));
            }

            if (role.canMention() != canMention) {
                dispatchEvent(new GuildRoleMentionUpdateEvent(identity, guild, sequence, role, !canMention));
            }

            List<Permission> perms = Permission.getPermissionsByLong(permissions);
            List<Permission> allowed = new ArrayList<>();
            // Set denied to all old role permissions
            // Then remove allowed and original permissions, what's left are the denied permissions
            List<Permission> denied = new ArrayList<>(role.getPermissions());

            for (Permission permission : perms) {
                // Allowed Permission
                // Old role does not have a permission new role have
                if (!role.hasPermissions(false, permission)) {
                    allowed.add(permission);
                    denied.remove(permission);
                }

                // Denied Permission
                // Old role has permission the denied has, which means the permission did not change
                if (role.getPermissions().contains(permission)) {
                    denied.remove(permission);
                }
            }

            role.setPermissionsLong(permissions);

            if (allowed.size() > 0 || denied.size() > 0) {
                dispatchEvent(new GuildRolePermissionsUpdateEvent(identity, guild, sequence, role, allowed, denied));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
