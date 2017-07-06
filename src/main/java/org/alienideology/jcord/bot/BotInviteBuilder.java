package org.alienideology.jcord.bot;

import org.alienideology.jcord.handle.permission.Permission;
import org.alienideology.jcord.internal.gateway.HttpPath;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * BotInviteBuilder - A builder for building bot invite URL.
 * @author AlienIdeology
 */
public class BotInviteBuilder {

    private String id;
    private Permission[] permissions;
    private String guildId;

    public BotInviteBuilder() {
    }

    public BotInviteBuilder(String id) {
        this.id = id;
    }

    /**
     * Set the ID of the invite link.
     * @return BotInviteBuilder for chaining.
     */
    public BotInviteBuilder setBotId(String id) {
        this.id = id;
        return this;
    }

    /**
     * Set the permissions of the invite link.
     * @param permissions The permissions
     * @return BotInviteBuilder for chaining.
     */
    public BotInviteBuilder setPermissions(Permission... permissions) {
        this.permissions = permissions;
        return this;
    }

    /**
     * Set the targeted guild of the invite link.
     * @param guildId The ID of the guild.
     * @return BotInviteBuilder for chaining.
     */
    public BotInviteBuilder setGuildId(String guildId) {
        this.guildId = guildId;
        return this;
    }

    /**
     * @return The invite link.
     */
    public String build() {
        if (id == null) {
            throw new IllegalStateException("Cannot build an invite when there is no bot ID set!");
        }

        String invite = HttpPath.EndPoint.AUTHORIZE;
        invite += "?client_id=" + id + "&scope=bot";

        if (permissions != null) {
            long permCode = Permission.getLongByPermissions(permissions);
            invite += "&permissions=" + permCode;
        }
        if (guildId != null) {
            invite += "&guild_id=" + guildId;
        }

        return invite;
    }

    /**
     * @return The invite link
     * @throws MalformedURLException
     */
    public URL buildURL() throws MalformedURLException {
        return new URL(build());
    }

}
