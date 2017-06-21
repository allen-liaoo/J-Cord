import org.alienideology.jcord.event.EventSubscriber;
import org.alienideology.jcord.event.guild.member.GuildMemberNicknameUpdateEvent;
import org.alienideology.jcord.event.guild.role.update.GuildRolePermissionsUpdateEvent;

/**
 * @author AlienIdeology
 */
public class ExampleSubscriber {

    @EventSubscriber
    public void onEvent(GuildRolePermissionsUpdateEvent event) {

    }

    @EventSubscriber
    public void onEvent2(GuildMemberNicknameUpdateEvent event) {
        System.out.println(event.getNewNickname());
    }

}

