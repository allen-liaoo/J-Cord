import org.alienideology.jcord.Identity;
import org.alienideology.jcord.IdentityBuilder;
import org.alienideology.jcord.IdentityType;

/**
 * @a
 */
public class ExampleBot {

    public static void main(String[] args) {
        try {
            Identity bot = new IdentityBuilder()
                    .setIdentityType(IdentityType.BOT)
                    .useToken(Token.top_secret)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
