package org.alienideology.jcord.internal.object.guild;

import org.alienideology.jcord.handle.Permission;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.guild.IRole;
import org.alienideology.jcord.handle.guild.IRoleManager;
import org.alienideology.jcord.internal.exception.ErrorResponseException;
import org.alienideology.jcord.internal.exception.HigherHierarchyException;
import org.alienideology.jcord.internal.exception.HigherHierarchyException.HierarchyType;
import org.alienideology.jcord.internal.exception.HttpErrorException;
import org.alienideology.jcord.internal.exception.PermissionException;
import org.alienideology.jcord.internal.gateway.ErrorResponse;
import org.alienideology.jcord.internal.gateway.HttpCode;
import org.alienideology.jcord.internal.gateway.HttpPath;
import org.alienideology.jcord.internal.gateway.Requester;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * @author AlienIdeology
 */
public class RoleManager implements IRoleManager {

    private Guild guild;

    public RoleManager(Guild guild) {
        this.guild = guild;
    }

    @Override
    public IGuild getGuild() {
        return guild;
    }

    @Override
    public void modifyRoleName(IRole role, String name) {
        modifyRoleName(role.getId(), name);
    }

    @Override
    public void modifyRoleName(String roleId, String name) {
        if (name == null || name.isEmpty()) return;
        modifyRole(roleId, new JSONObject().put("name", name));
    }

    @Override
    public void modifyRolePermissions(IRole role, Permission... permissions) {
        modifyPermission(role, Arrays.asList(permissions));
    }

    @Override
    public void modifyRolePermissions(IRole role, Collection<Permission> permissions) {
        modifyPermission(role, permissions);
    }

    @Override
    public void addPermissionsToRole(IRole role, Permission... permissions) {
        addPermissionsToRole(role, Arrays.asList(permissions));
    }

    @Override
    public void addPermissionsToRole(IRole role, Collection<Permission> permissions) {
        List<Permission> allPerms = new ArrayList<>(role.getPermissions());
        allPerms.addAll(permissions);
        modifyPermission(role, allPerms);
    }

    @Override
    public void removePermissionsFromRole(IRole role, Permission... permissions) {
        removePermissionsFromRole(role, Arrays.asList(permissions));
    }

    @Override
    public void removePermissionsFromRole(IRole role, Collection<Permission> permissions) {
        List<Permission> allPerms = new ArrayList<>(role.getPermissions());
        allPerms.removeAll(permissions);
        modifyPermission(role, allPerms);
    }

    private void modifyPermission(IRole role, Collection<Permission> permissions) {
        modifyRole(role.getId(), new JSONObject().put("permissions", Permission.getLongByPermissions(permissions)));
    }

    @Override
    public void modifyRoleColor(IRole role, Color color) {
        modifyRoleColor(role.getId(), color);
    }

    @Override
    public void modifyRoleColor(String roleId, Color color) {
        modifyRole(roleId, new JSONObject().put("color", color.getRGB() & 0xFFFFFF));
    }

    @Override
    public void modifyIsSeparateListed(IRole role, boolean isSeparateListed) {
        modifyIsSeparateListed(role.getId(), isSeparateListed);
    }

    @Override
    public void modifyIsSeparateListed(String roleId, boolean isSeparateListed) {
        modifyRole(roleId, new JSONObject().put("hoist", isSeparateListed));
    }

    @Override
    public void modifyCanMention(IRole role, boolean canMention) {
        modifyCanMention(role.getId(), canMention);
    }

    @Override
    public void modifyCanMention(String roleId, boolean canMention) {
        modifyRole(roleId, new JSONObject().put("mentionable", canMention));
    }

    private void modifyRole(String roleId, JSONObject json) {
        IRole role = guild.getRole(roleId);
        if (role == null) {
            throw new ErrorResponseException(ErrorResponse.UNKNOWN_ROLE);
        }

        // Checks hierarchy
        if (!guild.getSelfMember().canModify(role)) {
            throw new HigherHierarchyException(HierarchyType.ROLE);
        }

        try {
            new Requester((IdentityImpl) getIdentity(), HttpPath.Guild.MODIFY_GUILD_ROLE).request(guild.getId(), roleId)
                    .updateRequestWithBody(request -> request.body(json)).performRequest();
        } catch (HttpErrorException ex) {
            if (ex.isPermissionException()) {
                if (!guild.getSelfMember().hasPermissions(true, Permission.MANAGE_ROLES)) { // Modify roles without Manage Roles permission.
                    throw new PermissionException(Permission.ADMINISTRATOR, Permission.MANAGE_ROLES);
                } else if (json.has("permissions")) { // Modify permissions that identity itself does not have
                    throw new PermissionException("Cannot manage a role's permission because identity itself does not have that permission!");
                }
            } else {
                throw ex;
            }
        }
    }

    @Override
    public void changeRolePosition(IRole role, int newPosition) {
        changeRolePosition(role.getId(), newPosition);
    }

    @Override
    public void changeRolePosition(String roleId, int newPosition) {
        IRole role = guild.getRole(roleId);
        if (role == null) {
            throw new ErrorResponseException(ErrorResponse.UNKNOWN_ROLE);
        }
        if (role.isEveryone()) {
            throw new IllegalArgumentException("Cannot modify the position of a everyone role!");
        }
        if (newPosition <= 0 || newPosition > guild.getRoles().size()) {
            throw new IllegalArgumentException("The position cannot be smaller or equal to zero, or greater than the total number of roles!");
        }

        // Checks hierarchy
        if (!guild.getSelfMember().canModify(role)) {
            throw new HigherHierarchyException(HierarchyType.ROLE);
        }

        // HashMap for new positions mapped with ID
        HashMap<String, Integer> roleMap = new HashMap<>();

        LinkedList<IRole> allRoles = new LinkedList<>(guild.getRoles());
        for (int i = 0; i < allRoles.size(); i++) {
            IRole r = allRoles.get(i);
            // Ignore same role object
            if (r.equals(role)) continue;

            /* Lower Position */
            if (r.getPosition() <= newPosition) {
                roleMap.put(r.getId(), r.getPosition()-1);
            }
            /* Ignore Higher Position */
            // Since they will not be affected by the role change
        }
        // Add the changed position's role
        roleMap.put(role.getId(), newPosition);

        // Construct JSONArray
        JSONArray array = new JSONArray();
        for (String id : roleMap.keySet()) {
            array.put(new JSONObject()
                .put("id", id)
                .put("position", roleMap.get(id))
            );
        }

        try {
            new Requester((IdentityImpl) getIdentity(), HttpPath.Guild.MODIFY_GUILD_ROLE_POSITIONS).request(guild.getId())
                    .updateRequestWithBody(request -> request.body(array)).performRequest();
        } catch (HttpErrorException ex) {
            if (ex.isPermissionException()) {
                throw new PermissionException(Permission.ADMINISTRATOR, Permission.MANAGE_ROLES);
            } else if (ex.getCode().equals(HttpCode.BAD_REQUEST)) {
                throw new HigherHierarchyException("Cannot move a role higher than the highest role this identity has!");
            } else {
                throw ex;
            }
        }
    }
}
