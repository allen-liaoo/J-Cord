package org.alienideology.jcord.internal.object.modifiers;

import org.alienideology.jcord.handle.ISnowFlake;
import org.alienideology.jcord.handle.audit.AuditAction;
import org.alienideology.jcord.handle.channel.IVoiceChannel;
import org.alienideology.jcord.handle.guild.IMember;
import org.alienideology.jcord.handle.guild.IRole;
import org.alienideology.jcord.handle.modifiers.Attribute;
import org.alienideology.jcord.handle.modifiers.IMemberModifier;
import org.alienideology.jcord.handle.permission.Permission;
import org.alienideology.jcord.internal.exception.ErrorResponseException;
import org.alienideology.jcord.internal.exception.HigherHierarchyException;
import org.alienideology.jcord.internal.exception.HttpErrorException;
import org.alienideology.jcord.internal.exception.PermissionException;
import org.alienideology.jcord.internal.object.guild.Member;
import org.alienideology.jcord.internal.rest.ErrorResponse;
import org.alienideology.jcord.internal.rest.HttpCode;
import org.alienideology.jcord.internal.rest.HttpPath;
import org.alienideology.jcord.internal.rest.Requester;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author AlienIdeology
 */
public final class MemberModifier extends Modifier<AuditAction<Void>> implements IMemberModifier {

    private final Member member;

    private Attribute<IMemberModifier, String> nicknameAttr;
    private Attribute<IMemberModifier, Collection<IRole>> rolesAttr;
    private Attribute<IMemberModifier, IVoiceChannel> voiceChannelAttr;
    private Attribute<IMemberModifier, Boolean> muteAttr;
    private Attribute<IMemberModifier, Boolean> deafenAttr;

    public MemberModifier(Member member) {
        super(member.getIdentity());
        this.member = member;
        setupAttributes();
    }

    @Override
    public IMember getMember() {
        return member;
    }

    @Override
    public IMemberModifier nickname(String nickname) {
        return nicknameAttr.setValue(nickname);
    }

    @Override
    public IMemberModifier roles(Collection<IRole> roles) {
        return rolesAttr.setValue(roles);
    }

    @Override
    public IMemberModifier voiceChannel(IVoiceChannel channel) {
        return voiceChannelAttr.setValue(channel);
    }

    @Override
    public IMemberModifier mute(boolean mute) {
        return muteAttr.setValue(mute);
    }

    @Override
    public IMemberModifier deafen(boolean deafen) {
        return deafenAttr.setValue(deafen);
    }

    @Override
    public Attribute<IMemberModifier, String> getNicknameAttr() {
        return nicknameAttr;
    }

    @Override
    public Attribute<IMemberModifier, Collection<IRole>> getRolesAttr() {
        return rolesAttr;
    }

    @Override
    public Attribute<IMemberModifier, IVoiceChannel> getVoiceChannelAttr() {
        return voiceChannelAttr;
    }

    @Override
    public Attribute<IMemberModifier, Boolean> getMuteAttr() {
        return muteAttr;
    }

    @Override
    public Attribute<IMemberModifier, Boolean> getDeafenAttr() {
        return deafenAttr;
    }

    @Override
    public AuditAction<Void> modify() {
        // Check hierarchy
        if (member.isOwner()) {
            throw new HigherHierarchyException(HigherHierarchyException.HierarchyType.OWNER);
        }
        if (!getGuild().getSelfMember().canModify(member)) {
            throw new HigherHierarchyException(HigherHierarchyException.HierarchyType.MEMBER);
        }

        JSONObject json = getUpdatableJson();

        try {
            return new AuditAction<Void>(getIdentity(), HttpPath.Guild.MODIFY_GUILD_MEMBER, getGuild().getId(), member.getId()) {
                @Override
                protected Void request(Requester requester) {
                    requester.updateRequestWithBody(request -> request.body(json)).performRequest();
                    reset();
                    return null;
                }
            };
        } catch (HttpErrorException ex) {
            if (ex.isPermissionException()) {
                throw new PermissionException(ex.getLocalizedMessage());
            } else if (ex.getCode().equals(HttpCode.BAD_REQUEST) && json.has("roles")) { // Specific bad request for modifying roles
                    throw new IllegalArgumentException("Modifying member roles without giving any changes!");
            } else {
                throw ex;
            }
        }
    }

    @Override
    protected void setupAttributes() {
        nicknameAttr = new Attribute<IMemberModifier, String>("nick", this, member::getNickname) {
            @Override
            public void checkValue(String value) throws IllegalArgumentException {
                if (!IMember.isValidNickname(value)) {
                    throw new IllegalArgumentException("The nickname is not valid!");
                }

                if (member.equals(getGuild().getSelfMember()) && !member.hasPermissions(true, Permission.CHANGE_NICKNAME)) {
                    throw new PermissionException(Permission.ADMINISTRATOR, Permission.CHANGE_NICKNAME);
                } else if (!member.hasPermissions(true, Permission.MANAGE_NICKNAMES)) {
                    throw new PermissionException(Permission.ADMINISTRATOR, Permission.MANAGE_NICKNAMES);
                }
            }

            @Override
            public IMemberModifier setValue(String newValue) {
                if(member.getUser().isSelf()) { // Cannot add this to #checkValue, since IMemberModifier uses that method for checking value
                    throw new IllegalArgumentException("Cannot modify the nickname attribute of the identity itself! Use IMemberModifier#modifyNickname instead!");
                }
                return super.setValue(newValue);
            }

            @Override
            public Object getAltValue() {
                if (newValue == null)
                    newValue = "";
                return super.getAltValue();
            }
        };
        rolesAttr = new Attribute<IMemberModifier, Collection<IRole>>("roles", this, member::getRoles) {
            @Override
            public void checkValue(Collection<IRole> value) throws IllegalArgumentException {
                for (IRole role : value) {
                    if (role == null || !role.getGuild().equals(getGuild())) {
                        throw new ErrorResponseException(ErrorResponse.UNKNOWN_ROLE);
                    }
                }

                if (!member.hasPermissions(true, Permission.MANAGE_ROLES)) {
                    throw new PermissionException(Permission.MANAGE_ROLES);
                }
            }

            @Override
            public Object getAltValue() {
                Collection<IRole> roles = needUpdate() ? newValue : oldValue.get();
                List<String> roleIds = roles.stream().map(ISnowFlake::getId).collect(Collectors.toList());
                return new JSONArray(roleIds);
            }
        };
        voiceChannelAttr = new Attribute<IMemberModifier, IVoiceChannel>("channel_id", this, member.getVoiceState()::getVoiceChannel) {
            @Override
            public void checkValue(IVoiceChannel value) throws IllegalArgumentException {
                if (value == null) {
                    throw new ErrorResponseException(ErrorResponse.UNKNOWN_CHANNEL);
                }
                if (member.hasPermissions(true, Permission.MOVE_MEMBERS)) {
                    throw new PermissionException(Permission.ADMINISTRATOR, Permission.MOVE_MEMBERS);
                }
                if (!value.hasPermission(getGuild().getSelfMember(), Permission.ADMINISTRATOR, Permission.CONNECT)) {
                    throw new PermissionException(Permission.ADMINISTRATOR, Permission.CONNECT);
                }
            }

            @Override
            public Object getAltValue() {
                return needUpdate() ? newValue.getId() : oldValue.get().getId();
            }
        };
        muteAttr = new Attribute<IMemberModifier, Boolean>("mute", this, member.getVoiceState()::isMuted) {
            @Override
            public void checkValue(Boolean value) throws IllegalArgumentException {
                if (!member.hasPermissions(true, Permission.MUTE_MEMBERS)) {
                    throw new PermissionException(Permission.MUTE_MEMBERS);
                }
            }
        };
        deafenAttr = new Attribute<IMemberModifier, Boolean>("deaf", this, member.getVoiceState()::isDeafened) {
            @Override
            public void checkValue(Boolean value) throws IllegalArgumentException {
                if (!member.hasPermissions(true, Permission.DEAFEN_MEMBERS)) {
                    throw new PermissionException(Permission.DEAFEN_MEMBERS);
                }
            }
        };
        addAttributes(nicknameAttr, rolesAttr, voiceChannelAttr, muteAttr, deafenAttr);
    }

}
