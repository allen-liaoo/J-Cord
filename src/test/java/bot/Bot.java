package bot;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.IdentityBuilder;
import org.alienideology.jcord.IdentityType;
import org.alienideology.jcord.bot.command.CommandFramework;
import org.alienideology.jcord.event.Event;
import org.alienideology.jcord.event.EventManager;
import org.alienideology.jcord.event.message.MessageCreateEvent;
import org.alienideology.jcord.handle.channel.IChannel;
import org.alienideology.jcord.handle.channel.IPrivateChannel;
import org.alienideology.jcord.handle.channel.ITextChannel;
import org.alienideology.jcord.handle.channel.IVoiceChannel;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.util.log.LogMode;

import java.io.IOException;

/**
 * A simple test bot for J-Cord
 *
 * @author AlienIdeology
 */
public class Bot {

    public Identity bot;
    public CommandFramework framework;

    public static void main(String[] args) {
        // Start the bot
        Bot main = new Bot();
    }

    // Initialize fields
    public Bot() {
        // Constructing the Command Framework
        framework = new CommandFramework();

        setUpIdentity();

        // Set the prefix
        // There can be multiple prefixes
        // For bot, the prefix sets are
        // 1. =
        // 2. Bot mention (user mention, without nickname)
        // 3. Bot mention with nickname. (Member mention)
        framework.setPrefixes("=", bot.getSelf().mention(), bot.getSelf().mention(true))

            // Register Command Responders
            // The responders will have methods that is annotated as a command.
            .registerCommandResponders(new Commands(this));

        playGround();
    }

    // Build the identity using IdentityBuilder
    // Set the token and event managers
    private void setUpIdentity() {
        try {
            // Construct an IdentityBuilder for building the identity.
            bot = new IdentityBuilder()

                    // Use IdentityType.BOT for a bot account.
                    // Use IdentityType.CLIENT for selfbot.
                    .setIdentityType(IdentityType.BOT)

                    // Remember to keep the token secret
                    // The token class would looks like
                    //
                    // public class Token {
                    //      public static final String YOUR_SECRET_TOKEN_HERE = "";
                    // }
                    //
                    .useToken(Token.YOUR_SECRET_TOKEN_HERE)

                    // Set the event manager to listen to events
                    .setEventManager(
                            new EventManager()

                                    // Dispatcher Adaptor is a class that you can extend for listening to events.
                                    // You listen to events by overriding methods in Dispatcher Adaptor that related specific events.
                                    .registerDispatcherAdaptors(new EventAdaptor())

                                    // Event Subscribers are annotated listeners. J-Cord will dispatch the events via reflection.
                                    .registerEventSubscribers(new EventSubscribers())

                                    // Command Frameworks are pre-made command system that you can customize.
                                    .registerCommandFrameworks(framework)
                    )

                    // Set the Logger for this identity
                    .setLogger(logger ->
                            logger.setMode(LogMode.ALL) // We use LoggerMode.ALL for debugging the system
                            .setShowDate(false) // The logger will not show the dates for the log
                    )

                    // Build the identity blocking the thread
                    // To prevent the program from doing any farther actions until the identity is build.
                    // If you want to build the identity asynchronously,
                    // Use .build(false); or .build();
                    .build(true);

        // Remember to catch the exceptions thrown when building identity.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Test the API here :D
    private void playGround() {
        for (IGuild guild : bot.getGuilds()) {
            System.out.println("Guild:\t" + guild.getName());
            for (ITextChannel tc : guild.getTextChannels()) {
                System.out.println("\t--TC: " + tc.getName());
            }
            for (IVoiceChannel vc : guild.getVoiceChannels()) {
                System.out.println("\t--VC: " + vc.getName());
            }
        }

        for (IPrivateChannel dm : bot.getPrivateChannels()) {
            System.out.println("DM:\t" + dm.getRecipient().getName());
        }

        System.out.println(bot.getGuild("311250670068170752").getCreatedTime());

    }

}
