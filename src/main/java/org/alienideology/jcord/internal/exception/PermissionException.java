package org.alienideology.jcord.internal.exception;

import org.alienideology.jcord.handle.Permission;

/**
 * @author AlienIdeology
 */
public class PermissionException extends RuntimeException {

    private Permission permission;

    public PermissionException(Permission permission) {
        super("Missing permission: " + permission.toString());
        this.permission = permission;
    }

    public PermissionException(String cause) {
        super(cause);
        this.permission = null;
    }

    public Permission getMissingPermission() {
        return permission;
    }
}
