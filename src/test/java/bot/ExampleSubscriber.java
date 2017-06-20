package bot;

import org.alienideology.jcord.event.EventSubscriber;
import org.alienideology.jcord.event.guild.emoji.GuildEmojiNameUpdateEvent;

/**
 * @author AlienIdeology
 */
public class ExampleSubscriber {

    @EventSubscriber
    public void onEmoji(GuildEmojiNameUpdateEvent event) {
        System.out.println(event.getOldName());
    }

}

