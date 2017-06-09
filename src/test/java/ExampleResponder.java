import org.alienideology.jcord.command.Command;
import org.alienideology.jcord.command.CommandResponder;
import org.alienideology.jcord.event.message.MessageCreateEvent;

/**
 * @author AlienIdeology
 */
public class ExampleResponder implements CommandResponder {

    @Command(aliases = {"hi", "hello"}, guildOnly = true)
    public String onCommand(MessageCreateEvent event) {
        return "hi";
    }

}
