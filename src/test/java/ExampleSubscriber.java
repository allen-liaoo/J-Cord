import org.alienideology.jcord.event.EventSubscriber;
import org.alienideology.jcord.event.message.MessageReactionAddEvent;
import org.alienideology.jcord.event.message.MessageReactionRemoveEvent;

/**
 * @author AlienIdeology
 */
public class ExampleSubscriber {

    @EventSubscriber
    public void onEvent(MessageReactionRemoveEvent event) {
        System.out.println("Remove: "+event.getMessage());
    }

    @EventSubscriber
    public void onEvent2(MessageReactionAddEvent event) {
        System.out.println("Add: "+event.getMessage());
    }

}

