import org.alienideology.jcord.event.EventSubscriber;
import org.alienideology.jcord.event.guild.member.GuildMemberAddRoleEvent;
import org.alienideology.jcord.event.guild.member.GuildMemberNicknameUpdateEvent;

/**
 * @author AlienIdeology
 */
public class ExampleSubscriber {

    @EventSubscriber
    public void onEvent(GuildMemberAddRoleEvent event) {
        System.out.println(event.getAddedRoles());
    }

    @EventSubscriber
    public void on2Event(GuildMemberNicknameUpdateEvent event) {
        System.out.println(event.getNewNickname());
    }

}

