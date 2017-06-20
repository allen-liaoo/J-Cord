package bot;

import org.alienideology.jcord.event.EventSubscriber;
import org.alienideology.jcord.event.channel.guild.text.TextChannelUpdateEvent;

/**
 * @author AlienIdeology
 */
public class ExampleSubscriber {

    @EventSubscriber
    public void onChannelPerm(TextChannelUpdateEvent event) {
        System.out.println(event.getTextChannel());
    }

}

