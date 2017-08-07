package org.alienideology.jcord.internal.object.modifiers;

import org.alienideology.jcord.handle.audit.AuditAction;
import org.alienideology.jcord.handle.guild.IRole;
import org.alienideology.jcord.handle.modifiers.Attribute;
import org.alienideology.jcord.handle.modifiers.IRoleModifier;
import org.alienideology.jcord.handle.permission.Permission;
import org.alienideology.jcord.internal.exception.HigherHierarchyException;
import org.alienideology.jcord.internal.exception.HttpErrorException;
import org.alienideology.jcord.internal.exception.PermissionException;
import org.alienideology.jcord.internal.object.guild.Role;
import org.alienideology.jcord.internal.rest.HttpPath;
import org.alienideology.jcord.internal.rest.Requester;

import java.awt.*;
import java.util.Collection;

/**
 * @author AlienIdeology
 */
public final class RoleModifier extends Modifier<AuditAction<Void>> implements IRoleModifier {

    private final Role role;

    private Attribute<IRoleModifier, String> nameAttr;
    private Attribute<IRoleModifier, Color> colorAttr;
    private Attribute<IRoleModifier, Collection<Permission>> permissionsAttr;
    private Attribute<IRoleModifier, Boolean> isSeparateListedAttr;
    private Attribute<IRoleModifier, Boolean> canMentionAttr;

    public RoleModifier(Role role) {
        super(role.getIdentity());
        this.role = role;
        setupAttributes();
    }

    @Override
    public IRole getRole() {
        return role;
    }

    @Override
    public IRoleModifier name(String name) {
        return nameAttr.setValue(name);
    }

    @Override
    public IRoleModifier color(Color color) {
        return colorAttr.setValue(color);
    }

    @Override
    public IRoleModifier permissions(Collection<Permission> permissions) {
        return permissionsAttr.setValue(permissions);
    }

    @Override
    public IRoleModifier isSeparateListed(boolean isSeparateListed) {
        return isSeparateListedAttr.setValue(isSeparateListed);
    }

    @Override
    public IRoleModifier canMention(boolean canMention) {
        return canMentionAttr.setValue(canMention);
    }

    @Override
    public Attribute<IRoleModifier, String> getNameAttr() {
        return nameAttr;
    }

    @Override
    public Attribute<IRoleModifier, Color> getColorAttr() {
        return colorAttr;
    }

    @Override
    public Attribute<IRoleModifier, Collection<Permission>> getPermissionsAttr() {
        return permissionsAttr;
    }

    @Override
    public Attribute<IRoleModifier, Boolean> getIsSeparateListedAttr() {
        return isSeparateListedAttr;
    }

    @Override
    public Attribute<IRoleModifier, Boolean> getCanMentionAttr() {
        return canMentionAttr;
    }

    @Override
    public AuditAction<Void> modify() {
        // Checks hierarchy
        if (!role.getGuild().getSelfMember().canModify(role)) {
            throw new HigherHierarchyException(HigherHierarchyException.HierarchyType.ROLE);
        }

        try {
            return new AuditAction<Void>(getIdentity(), HttpPath.Guild.MODIFY_GUILD_ROLE, role.getGuild().getId(), role.getId()) {
                @Override
                protected Void request(Requester requester) {
                    requester.updateRequestWithBody(request -> request.body(getUpdatableJson())).performRequest();
                    reset();
                    return null;
                }
            };
        } catch (HttpErrorException ex) {
            if (ex.isPermissionException()) {
                if (!role.getGuild().getSelfMember().hasPermissions(true, Permission.MANAGE_ROLES)) { // Modify roles without Manage Roles permission.
                    throw new PermissionException(Permission.ADMINISTRATOR, Permission.MANAGE_ROLES);
                } else if (permissionsAttr.needUpdate()) { // Modify permissions that identity itself does not have
                    throw new PermissionException("Cannot manage a role's permission because identity itself does not have that permission!");
                }
            }
            throw ex;
        }
    }

    @Override
    protected void setupAttributes() {
        nameAttr = new Attribute<IRoleModifier, String>("name", this, role::getName) {
            @Override
            public void checkValue(String value) throws IllegalArgumentException {}

            @Override
            public Object getAltValue() {
                if (newValue == null)
                    newValue = "";
                return super.getAltValue();
            }
        };
        colorAttr = new Attribute<IRoleModifier, Color>("color", this, role::getColor) {
            @Override
            public void checkValue(Color value) throws IllegalArgumentException {}

            @Override
            public Object getAltValue() {
                Color color = newValue == null ? oldValue.get() : newValue;
                return color.getRGB() & 0xFFFFFF;
            }
        };
        permissionsAttr = new Attribute<IRoleModifier, Collection<Permission>>("permissions", this, role::getPermissions) {
            @Override
            public void checkValue(Collection<Permission> value) throws IllegalArgumentException {}

            @Override
            public Object getAltValue() {
                Collection<Permission> val = newValue == null ? getOldValue() : newValue;
                return Permission.getLongByPermissions(val);
            }

            @Override
            public boolean needUpdate() {
                return isSet();
            }
        };
        isSeparateListedAttr = new Attribute<IRoleModifier, Boolean>("hoist", this, role::isSeparateListed) {
            @Override
            public void checkValue(Boolean value) throws IllegalArgumentException {}
        };
        canMentionAttr = new Attribute<IRoleModifier, Boolean>("mentionable", this, role::canMention) {
            @Override
            public void checkValue(Boolean value) throws IllegalArgumentException {}
        };
        addAttributes(nameAttr, colorAttr, permissionsAttr, isSeparateListedAttr, canMentionAttr);
    }

}
