package org.alienideology.jcord.internal.object.managers;

import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.guild.IRole;
import org.alienideology.jcord.handle.managers.IRoleManager;
import org.alienideology.jcord.handle.permission.Permission;
import org.alienideology.jcord.internal.exception.HigherHierarchyException;
import org.alienideology.jcord.internal.exception.HigherHierarchyException.HierarchyType;
import org.alienideology.jcord.internal.exception.HttpErrorException;
import org.alienideology.jcord.internal.exception.PermissionException;
import org.alienideology.jcord.internal.gateway.HttpCode;
import org.alienideology.jcord.internal.gateway.HttpPath;
import org.alienideology.jcord.internal.gateway.Requester;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.alienideology.jcord.internal.object.guild.Role;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * @author AlienIdeology
 */
public final class RoleManager implements IRoleManager {

    private Role role;
    private Guild guild;

    public RoleManager(Role role) {
        this.role = role;
        this.guild = (Guild) role.getGuild();
    }

    @Override
    public IGuild getGuild() {
        return guild;
    }

    @Override
    public IRole getRole() {
        return role;
    }

    @Override
    public void modifyName(String name) {
        if (name == null || name.isEmpty()) return;
        modifyRole(new JSONObject().put("name", name));
    }

    @Override
    public void modifyPermissions(Permission... permissions) {
        modifyPermission(Arrays.asList(permissions));
    }

    @Override
    public void modifyPermissions(Collection<Permission> permissions) {
        modifyPermission(permissions);
    }

    @Override
    public void addPermissions(Permission... permissions) {
        addPermissions(Arrays.asList(permissions));
    }

    @Override
    public void addPermissions(Collection<Permission> permissions) {
        List<Permission> allPerms = new ArrayList<>(role.getPermissions());
        allPerms.addAll(permissions);
        modifyPermission(allPerms);
    }

    @Override
    public void removePermissions(Permission... permissions) {
        removePermissions(Arrays.asList(permissions));
    }

    @Override
    public void removePermissions(Collection<Permission> permissions) {
        List<Permission> allPerms = new ArrayList<>(role.getPermissions());
        allPerms.removeAll(permissions);
        modifyPermission(allPerms);
    }

    private void modifyPermission(Collection<Permission> permissions) {
        modifyRole(new JSONObject().put("permissions", Permission.getLongByPermissions(permissions)));
    }

    @Override
    public void modifyColor(Color color) {
        modifyRole(new JSONObject().put("color", color.getRGB() & 0xFFFFFF));
    }

    @Override
    public void modifyIsSeparateListed(boolean isSeparateListed) {
        modifyRole(new JSONObject().put("hoist", isSeparateListed));
    }

    @Override
    public void modifyCanMention(boolean canMention) {
        modifyRole(new JSONObject().put("mentionable", canMention));
    }

    private void modifyRole(JSONObject json) {
        // Checks hierarchy
        if (!guild.getSelfMember().canModify(role)) {
            throw new HigherHierarchyException(HierarchyType.ROLE);
        }

        try {
            new Requester((IdentityImpl) getIdentity(), HttpPath.Guild.MODIFY_GUILD_ROLE).request(guild.getId(), role.getId())
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
    public void changePosition(int position) {
        if (role.isEveryone()) {
            throw new IllegalArgumentException("Cannot modify the position of a everyone role!");
        }
        if (position <= 0 || position > guild.getRoles().size()) {
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
            if (r.getPosition() <= position) {
                roleMap.put(r.getId(), r.getPosition()-1);
            }
            /* Ignore Higher Position */
            // Since they will not be affected by the role change
        }
        // Add the changed position's role
        roleMap.put(role.getId(), position);

        // Construct JSONArray
        JSONArray array = new JSONArray();
        for (String id : roleMap.keySet()) {
            array.put(new JSONObject()
                .put("key", id)
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
