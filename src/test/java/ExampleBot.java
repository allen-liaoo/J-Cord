import org.alienideology.jcord.Identity;
import org.alienideology.jcord.handle.channel.IPrivateChannel;
import org.alienideology.jcord.handle.channel.ITextChannel;
import org.alienideology.jcord.handle.channel.IVoiceChannel;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.IdentityBuilder;
import org.alienideology.jcord.IdentityType;
import org.alienideology.jcord.command.CommandFramework;
import org.alienideology.jcord.event.EventManager;

/**
 * A simple test bot for J-Cord
 *
 * @author AlienIdeology
 */
public class ExampleBot {

    public static void main(String[] args) {

        try {
            Identity bot = new IdentityBuilder()
                    .setIdentityType(IdentityType.BOT)
                    .useToken(Token.YOUR_SECRET_TOKEN_HERE) // Remember to keep this secret
                    .setEventManager(
                            new EventManager()
                                    .registerDispatcherAdaptors(new ExampleDispatcher())
                                    .registerCommandFrameworks(new CommandFramework().setPrefixes("=")
                                            .registerCommandResponders(new ExampleResponder()))
                                    .registerEventSubscribers(new ExampleSubscriber())
                    )
                    .build(true);

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

            IGuild guild = bot.getGuild("311250670068170752");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
