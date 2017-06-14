import org.alienideology.jcord.internal.event.EventSubscriber;
import org.alienideology.jcord.internal.event.message.MessageCreateEvent;

/**
 * @author AlienIdeology
 */
public class ExampleSubscriber {

    @EventSubscriber
    public void onMessageCreateEvent(MessageCreateEvent event) {
        System.out.println(event.getChannel());
    }

}
