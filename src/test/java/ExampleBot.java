import org.alienideology.jcord.Identity;
import org.alienideology.jcord.IdentityBuilder;
import org.alienideology.jcord.IdentityType;
import org.alienideology.jcord.gateway.HttpPath;

/**
 * @author AlienIdeology
 */
public class ExampleBot {

    public static void main(String[] args) {
        HttpPath path = HttpPath.Guild.GET_GUILD.useId("311250670068170752");
        try {
            Identity bot = new IdentityBuilder()
                    .setIdentityType(IdentityType.BOT)
                    .useToken(Token.TOP_SECRET)
                    .build();
            System.out.println(path.request(bot).asJson().getBody().getObject().toString(4));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
