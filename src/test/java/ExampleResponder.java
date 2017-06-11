import org.alienideology.jcord.command.Command;
import org.alienideology.jcord.command.CommandResponder;
import org.alienideology.jcord.event.message.MessageCreateEvent;

import java.util.Arrays;

/**
 * @author AlienIdeology
 */
public class ExampleResponder implements CommandResponder {

    // This method will not be called when the message starts with "hey there" instead of "hey"
    @Command(aliases = {"hi", "hello", "hey"}, guildOnly = true)
    public String onHey() {
        return "hi";
    }

    @Command(aliases = "test")
    public String onHeyThere() {
        return "test";
    }


}
