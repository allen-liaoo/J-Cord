package bot;

import org.alienideology.jcord.event.EventSubscriber;
import org.alienideology.jcord.event.channel.guild.TextChannelDeleteEvent;

/**
 * @author AlienIdeology
 */
public class ExampleSubscriber {

    @EventSubscriber
    public void onChannelRemove(TextChannelDeleteEvent event) {
        System.out.println(event.getVoiceChannel());
        System.out.println(event.getTextChannel());
    }

}

