package org.alienideology.jcord.object.guild;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.object.DiscordObject;
import org.alienideology.jcord.object.Mention;
import org.alienideology.jcord.object.SnowFlake;
import org.alienideology.jcord.object.User;

import java.time.OffsetDateTime;
import java.util.Objects;

/**
 * Member - A user representation in a guild.
 * @author AlienIdeology
 */
public class Member extends DiscordObject implements SnowFlake, Mention {

    private final Guild guild;
    private final User user;
    private String nickname;
    private OffsetDateTime joinedDate;

//    private List<Role> roles;
    private boolean isDeafened;
    private boolean isMuted;

    public Member(Identity identity, Guild guild, User user, String nickname, String joinedDate, boolean isDeafened, boolean isMuted) {
        super(identity);
        this.guild = guild;
        this.user = user;
        this.nickname = nickname;
        this.joinedDate = OffsetDateTime.parse(joinedDate);
        this.isDeafened = isDeafened;
        this.isMuted = isMuted;
    }

    public Guild getGuild() {
        return guild;
    }

    public User getUser() {
        return user;
    }

    public String getNickname() {
        return nickname;
    }

    public OffsetDateTime getJoinedDate() {
        return joinedDate;
    }

    public boolean isDeafened() {
        return isDeafened;
    }

    public boolean isMuted() {
        return isMuted;
    }

    @Override
    public String mention() {
        return "<@!"+user.getId()+">";
    }

    @Override
    public String getId() {
        return user.getId();
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof User) && Objects.equals(user.getId(), ((User) obj).getId());
    }

    @Override
    public String toString() {
        return "ID: "+user.getId()+"\tNickName: "+nickname;
    }

}
