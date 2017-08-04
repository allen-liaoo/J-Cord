package org.alienideology.jcord.event.handler;

import org.alienideology.jcord.event.guild.member.GuildMemberAddRoleEvent;
import org.alienideology.jcord.event.guild.member.GuildMemberNicknameUpdateEvent;
import org.alienideology.jcord.event.guild.member.GuildMemberRemoveRoleEvent;
import org.alienideology.jcord.handle.guild.IRole;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.alienideology.jcord.internal.object.guild.Member;
import org.alienideology.jcord.internal.object.guild.Role;
import org.alienideology.jcord.util.log.LogLevel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author AlienIdeology
 */
public class GuildMemberUpdateEventHandler extends EventHandler {

    public GuildMemberUpdateEventHandler(IdentityImpl identity) {
        super(identity);
    }

    @Override
    public void dispatchEvent(JSONObject json, int sequence) {
        try {
            Guild guild = (Guild) identity.getGuild(json.getString("guild_id"));
            Member member = (Member) guild.getMember(json.getJSONObject("user").getString("id"));

            if (member == null) {
                logger.log(LogLevel.FETAL, "[UNKNOWN MEMBER] [MEMBER_UPDATE_EVENT]");
                return;
            }

            // Change Nickname
            String nickname = json.isNull("nick") ? null : json.getString("nick");
            if (!Objects.equals(member.getNickname(), nickname)) {
                String oldNick = member.getNickname();
                member.setNickname(nickname);
                dispatchEvent(new GuildMemberNicknameUpdateEvent(identity, guild, sequence, member, oldNick));
            }

            JSONArray rolesJson = json.getJSONArray("roles");

            // Remove Role
            // Remove roles first to make the process faster
            List<IRole> removedRoles = new ArrayList<>();
            for (IRole role : member.getRoles()) {
                if (role.isEveryone()) continue; // Ignores everyone role, since it will now be in the json.
                String id = role.getId();
                boolean isRemoved = true;
                for (int i = 0; i < rolesJson.length(); i++) {
                    if (id.equals(rolesJson.getString(i))) { // Find a match, not removed.
                        isRemoved = false;
                        break;
                    }
                }

                if (isRemoved) {
                    removedRoles.add(role);
                }
            }

            member.removeRoles(removedRoles);

            if (removedRoles.size() > 0) {
                dispatchEvent(new GuildMemberRemoveRoleEvent(identity, guild, sequence, member, removedRoles));
            }

            // Add Role
            List<IRole> addedRoles = new ArrayList<>();
            for (int i = 0; i < rolesJson.length(); i++) {
                String roleId = rolesJson.getString(i);
                IRole role = member.getRole(roleId);
                if (role == null) {
                    Role role1 = (Role) guild.getRole(roleId);
                    addedRoles.add(role1);
                    member.addRole(role1);
                }
            }

            if (addedRoles.size() > 0) {
                dispatchEvent(new GuildMemberAddRoleEvent(identity, guild, sequence, member, addedRoles));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
