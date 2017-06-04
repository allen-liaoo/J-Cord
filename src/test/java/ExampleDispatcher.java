import org.alienideology.jcord.event.DispatcherAdaptor;
import org.alienideology.jcord.event.gateway.ReadyEvent;
import org.alienideology.jcord.event.guild.GuildCreateEvent;
import org.alienideology.jcord.event.guild.GuildRoleCreateEvent;

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
        System.out.println("New Guild created!");
    }

    @Override
    public void onGuildRoleCreate(GuildRoleCreateEvent event) {
        //System.out.println("New Role created!");
    }
}
