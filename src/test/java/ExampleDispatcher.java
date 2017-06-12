import org.alienideology.jcord.JCord;
import org.alienideology.jcord.event.DispatcherAdaptor;
import org.alienideology.jcord.event.gateway.ReadyEvent;
import org.alienideology.jcord.event.guild.GuildCreateEvent;
import org.alienideology.jcord.event.guild.GuildRoleCreateEvent;
import org.alienideology.jcord.event.message.guild.GuildMessageCreateEvent;
import org.alienideology.jcord.event.message.MessageCreateEvent;
import org.alienideology.jcord.event.message.dm.PrivateMessageCreateEvent;
import org.alienideology.jcord.object.EmojiList;
import org.alienideology.jcord.object.message.EmbedMessageBuilder;

/**
 * @author AlienIdeology
 */
public class ExampleDispatcher extends DispatcherAdaptor {

    @Override
    public void onReady(ReadyEvent event) {
        System.out.println("API is Ready!");
    }

    @Override
    public void onGuildCreate(GuildCreateEvent event) {
        System.out.println("New Guild created! " + event.getGuild().toString());
        System.out.println("Size: "+event.getIdentity().getGuilds().size());
        System.out.println(event.getIdentity().getGuilds());
        System.out.println("TC: "+event.getGuild().getTextChannels().size());
        System.out.println("VC: "+event.getGuild().getVoiceChannels().size());
    }

    @Override
    public void onGuildRoleCreate(GuildRoleCreateEvent event) {
        //System.out.println("New Role created!");
    }

    @Override
    public void onMessageCreate(MessageCreateEvent event) {

        // Ignore messages from other bots and webhooks
        if (event.getUser().isBot() || event.getUser().isWebHook())
            return;

        // Ignore messages from the bot itself
        if (event.getMessage().isFromSelf())
            return;

        if (event.getMessage().getContent().startsWith("?")) { // Your prefix

            // Do stuff
            if (event.getMessage().getContent().replaceFirst("\\?", "").equals("ping")) {
                EmbedMessageBuilder builder = new EmbedMessageBuilder();
                event.getChannel().sendMessage(new EmojiList().getByAlias("man_surfing_tone5").getUnicode());
            }

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

    @Override
    public void onException(Exception exception) {
        JCord.LOGGER.fatal(exception);
    }
}
