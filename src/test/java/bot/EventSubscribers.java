package bot;

import org.alienideology.jcord.event.EventSubscriber;
import org.alienideology.jcord.event.guild.member.GuildMemberJoinEvent;
import org.alienideology.jcord.event.guild.member.GuildMemberLeaveEvent;
import org.alienideology.jcord.event.message.MessageCreateEvent;

/**
 * @author AlienIdeology
 */
public class EventSubscribers {

    @EventSubscriber
    public void onEvent(GuildMemberJoinEvent event) {
        event.getGuild().getDefaultChannel().sendMessage("Let's welcome " + event.getMember().getNameNotNull() + " to the server!");
    }

    @EventSubscriber
    public void onEvent2(GuildMemberLeaveEvent event) {
        event.getGuild().getDefaultChannel().sendMessage("Oh no! " + event.getMember().getNameNotNull() + " left the server!");
    }

}

