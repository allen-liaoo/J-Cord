import org.alienideology.jcord.Identity;
import org.alienideology.jcord.IdentityBuilder;
import org.alienideology.jcord.IdentityType;

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
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
