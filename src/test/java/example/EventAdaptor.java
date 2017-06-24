package example;

import org.alienideology.jcord.event.DispatcherAdaptor;
import org.alienideology.jcord.event.gateway.ReadyEvent;
import org.alienideology.jcord.event.guild.GuildCreateEvent;
import org.alienideology.jcord.event.message.MessageCreateEvent;
import org.alienideology.jcord.event.message.dm.PrivateMessageCreateEvent;
import org.alienideology.jcord.event.message.guild.GuildMessageCreateEvent;
import org.alienideology.jcord.handle.EmojiTable;

import java.util.Arrays;

/**
 * @author AlienIdeology
 */
public class EventAdaptor extends DispatcherAdaptor {

    @Override
    public void onReady(ReadyEvent event) {
        System.out.println("API is Ready!");
    }

    @Override
    public void onGuildCreate(GuildCreateEvent event) {
        System.out.println("New Guild created! " + event.getGuild().toString());
    }

    @Override
    public void onMessageCreate(MessageCreateEvent event) {

        // Ignore messages from other bots and webhooks
        if (event.getUser().isBot() || event.getUser().isWebHook())
            return;

        // Ignore messages from the bot itself
        if (event.getMessage().isFromSelf())
            return;

        // The below documentation uses this message as an example:
        // Message: "=avatar me"

        String message = event.getMessage().getContent();

        // Check if the message starts with the prefix
        // Which it does starts with "="
        if (message.startsWith("=")) {

            // Trim the message without the starting prefix
            // noPrefix will be "avatar me"
            String noPrefix = message.replaceFirst("=", "");

            // Then we split the message by white space or tab (Regex: \\s+)
            // The array will look like {"avatar", "me"}
            String[] args = message.replaceFirst("=", "").split("\\s+");

            // Then we get the alias of this command:
            String alias = null;

            // By getting the first element of the args array
            // But if args does not have any elements,
            // we let the alias be null.
            if (args.length > 0) {
                alias = args[0];
            }

            // Now, the alias would be "avatar"

            // Then we delete the original alias from the array:

            // If the args array has 1 or less elements
            // Which would be the following scenarios:
            // 1. {}
            // 2. {"avatar"}
            if (args.length <= 1) {
                args = new String[]{};

            // If the args array has more than 1 elements
            // Which would be like {"avatar", "me"}
            // We copy the array, without the first element (which is the alias)
            } else {
                args = Arrays.copyOfRange(args, 1, args.length);
            }

            // Now the args would be {"me"}

            // Then we can respond the the message with corresponding code.
            //
            // There are several choices you can use:
            // 1. If-else statements (Not recommended)
            // 2. Switch statements (For beginners)
            // 3. A HashMap of <alias, CommandClass>
            // 4. Reflection

            // The example code below uses the first choice
            if (alias != null) {
                if (alias.equals("avatar")) {

                    if (args[0].equals("me")) {

                        event.getChannel().sendMessage(event.getUser().getAvatar());

                    }

                } else if (alias.equals("ping")) {

                    event.getChannel().sendMessage(new EmojiTable().getByAlias("ping_pong").getUnicode());

                } else if (alias.equals("help")) {

                    if (args[0].equals("avatar")) {

                        event.getChannel().sendMessage("Avatar command is for getting a user's avatar!");

                    } else if (args[0].equals("ping")) {

                        event.getChannel().sendMessage("Pong... duh?");

                    } else {
                        // Do not respond with a "Command Not Found" message
                        // It is generally a bad practice
                    }
                }
            }

        } else {
            // If the message do not start with the prefix
            // Do not respond to the message
            // Otherwise the bot/user would easily be banned
        }
    }

    @Override
    public void onGuildMessageCreate(GuildMessageCreateEvent event) {
        System.out.println("------------\nGuild msg: \n"+event.getChannel());
        System.out.println(event.getMessage()+"\n------------");
    }

    @Override
    public void onPrivateMessageCreate(PrivateMessageCreateEvent event) {
        System.out.println("------------\nPrivate msg: \n"+event.getChannel());
        System.out.println(event.getMessage()+"\n------------");
    }
}
