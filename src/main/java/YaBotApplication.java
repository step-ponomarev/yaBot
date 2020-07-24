import bot.factory.BotFactory;
import config.ApplicationProperties;
import consts.Consts;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class YaBotApplication {
    static final ApplicationProperties properties = new ApplicationProperties(Consts.PROPERTIES_FILE_NAME);

    public static void main(String[] args) {
        try {
            var bot = BotFactory.getInstance(Consts.BOT_TOKEN);
        } catch (LoginException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("IDK EXCEPTION: " + e.getMessage());
        }
    }
}
