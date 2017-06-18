package bot;

import org.alienideology.jcord.event.EventSubscriber;
import org.alienideology.jcord.event.message.MessageCreateEvent;

/**
 * @author AlienIdeology
 */
public class ExampleSubscriber {

    @EventSubscriber
    public void onMessageCreateEvent(MessageCreateEvent event) {
        System.out.println(event.getChannel());
    }

}
