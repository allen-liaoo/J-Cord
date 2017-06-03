import org.alienideology.jcord.event.DispatcherAdaptor;
import org.alienideology.jcord.event.GuildEvent.GuildCreateEvent;
import org.alienideology.jcord.event.GuildEvent.GuildRoleCreateEvent;

/**
 * @author AlienIdeology
 */
public class ExampleDispatcher extends DispatcherAdaptor {

    @Override
    public void onGuildCreate(GuildCreateEvent event) {
        System.out.println("New Guild created!");
    }

    @Override
    public void onGuildRoleCreate(GuildRoleCreateEvent event) {
        //System.out.println("New Role created!");
    }
}
