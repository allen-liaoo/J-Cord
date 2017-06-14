import org.alienideology.jcord.internal.Identity;
import org.alienideology.jcord.internal.IdentityBuilder;
import org.alienideology.jcord.internal.IdentityType;
import org.alienideology.jcord.command.CommandFramework;
import org.alienideology.jcord.internal.event.EventManager;
import org.alienideology.jcord.internal.object.Guild;
import org.alienideology.jcord.internal.object.channel.PrivateChannel;
import org.alienideology.jcord.internal.object.channel.TextChannel;
import org.alienideology.jcord.internal.object.channel.VoiceChannel;

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

            for (Guild guild : bot.getGuilds()) {
                System.out.println("Guild:\t" + guild.getName());
                for (TextChannel tc : guild.getTextChannels()) {
                    System.out.println("\t--TC: " + tc.getName());
                }
                for (VoiceChannel vc : guild.getVoiceChannels()) {
                    System.out.println("\t--VC: " + vc.getName());
                }
            }

            for (PrivateChannel dm : bot.getPrivateChannels()) {
                System.out.println("DM:\t" + dm.getRecipient().getName());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
