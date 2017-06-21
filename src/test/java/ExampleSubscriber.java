import org.alienideology.jcord.event.EventSubscriber;
import org.alienideology.jcord.event.guild.member.GuildMemberNicknameUpdateEvent;
import org.alienideology.jcord.event.message.MessagePinUpdateEvent;

/**
 * @author AlienIdeology
 */
public class ExampleSubscriber {

    @EventSubscriber
    public void onEvent(MessagePinUpdateEvent event) {

    }

    @EventSubscriber
    public void onEvent2(GuildMemberNicknameUpdateEvent event) {

    }

}

