import org.alienideology.jcord.event.EventSubscriber;
import org.alienideology.jcord.event.guild.member.GuildMemberNicknameUpdateEvent;
import org.alienideology.jcord.event.user.PresenceUpdateEvent;

/**
 * @author AlienIdeology
 */
public class ExampleSubscriber {

    @EventSubscriber
    public void onEvent(PresenceUpdateEvent event) {
        System.out.println(event.getOldPresence());
    }

    @EventSubscriber
    public void onEvent2(GuildMemberNicknameUpdateEvent event) {
        System.out.println(event.getNewNickname());
    }

}

