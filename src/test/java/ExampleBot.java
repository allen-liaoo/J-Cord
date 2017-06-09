import org.alienideology.jcord.Identity;
import org.alienideology.jcord.IdentityBuilder;
import org.alienideology.jcord.IdentityType;
import org.alienideology.jcord.command.CommandFramework;
import org.alienideology.jcord.object.channel.PrivateChannel;
import org.alienideology.jcord.object.channel.TextChannel;
import org.alienideology.jcord.object.channel.VoiceChannel;
import org.alienideology.jcord.object.Guild;

/**
 * A simple test bot for J-Cord
 * @author AlienIdeology
 */
public class ExampleBot {

    public static void main(String[] args) {

        try {
            Identity bot = new IdentityBuilder()
                    .setIdentityType(IdentityType.BOT)
                    .useToken(Token.TOP_SECRET)
                    .registerDispatchers(new ExampleDispatcher())
                    .registerCommandFramework(new CommandFramework().setPrefixes("=")
                            .registerCommandResponder(new ExampleResponder()))
                    .build(true);

            for (Guild guild : bot.getGuilds()) {
                System.out.println("Guild:\t"+guild.getName());
                for (TextChannel tc : guild.getTextChannels()) {
                    System.out.println("\t--TC: "+tc.getName());
                }
                for (VoiceChannel vc : guild.getVoiceChannels()) {
                    System.out.println("\t--VC: "+vc.getName());
                }
            }

            for (PrivateChannel dm : bot.getPrivateChannels()) {
                System.out.println("DM:\t"+dm.getRecipient().getName());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
