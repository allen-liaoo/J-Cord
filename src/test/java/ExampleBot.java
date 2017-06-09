import org.alienideology.jcord.Identity;
import org.alienideology.jcord.IdentityBuilder;
import org.alienideology.jcord.IdentityType;
import org.alienideology.jcord.object.Emoji;
import org.alienideology.jcord.object.Emojis;
import org.alienideology.jcord.object.Permission;
import org.alienideology.jcord.object.channel.PrivateChannel;
import org.alienideology.jcord.object.channel.TextChannel;
import org.alienideology.jcord.object.channel.VoiceChannel;
import org.alienideology.jcord.object.guild.Guild;
import org.alienideology.jcord.object.message.StringMessage;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

            Emojis emojiList = Emoji.loadEmoji();
            System.out.println(emojiList.getByCategory(Emoji.Category.ACTIVITIES));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
