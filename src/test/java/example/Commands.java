package example;

import org.alienideology.jcord.JCord;
import org.alienideology.jcord.bot.command.Command;
import org.alienideology.jcord.bot.command.CommandResponder;
import org.alienideology.jcord.handle.builders.EmbedMessageBuilder;
import org.alienideology.jcord.handle.message.IEmbedMessage;
import org.alienideology.jcord.handle.permission.Permission;

import java.time.Instant;

/**
 * @author AlienIdeology
 */
public class Commands implements CommandResponder {

    private Bot bot;

    public Commands(Bot bot) {
        this.bot = bot;
    }

    // A simple hey command
    // This method will not be called when the message starts with "hey there" instead of "hey"
    @Command(name = "Hey", description = "Hello!", aliases = {"hi", "hello", "hey"}, permissions = Permission.ADMINISTRATOR, guildOnly = true)
    public String onHey() {
        return "hi";
    }

    // Sub commands can be achieve simply by extending the aliases.
    // For example, the "Hey there" is a sub command of "Hey".
    // The method will be called when the message starts with "hey there".
    @Command(name = "Hey there", description = "Wassup!", aliases = "hey there")
    public String onHeyThere() {
        return "wassup";
    }

    // A simple implementation of help command
    @Command(name = "Help", description = "A list of commands", aliases = {"help", "commands", "list"})
    public IEmbedMessage onHelp() {
        EmbedMessageBuilder message = new EmbedMessageBuilder()
                .setTitle("Commands", null)
                .setTimeStamp(Instant.now())
                .setDescription("This bot is made in J-Cord :)\n")
                .appendDescription("**Commands:**\n")
                .appendDescription("```css\n\n");

        // Loop through all the commands
        for (Command command : bot.framework.getAnnotations()) {
            String name = command.name();
            String description = command.description();
            // If the command has a name or a description
            if (!name.isEmpty() || !description.isEmpty()) {

                // Print it to the help list
                message.appendDescription(JCord.EMOJI_TABLE.getByAlias(":white_medium_square:") + " " + name + " - " + description + "\n");
            }
        }

        message.appendDescription("```");

        // This embed automatically be sent
        return message.build();
    }


}
