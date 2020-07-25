import bot.factory.BotFactory;
import bot.listeners.GreetingListener;
import config.ApplicationProperties;
import consts.Consts;

import javax.security.auth.login.LoginException;
import java.io.InputStream;

public class YaBotApplication {
    static final ApplicationProperties properties = new ApplicationProperties();

    public static void main(String[] args) {
        try {
            properties.init(getResourceAsStream(Consts.PROPERTIES_FILE_NAME));

            var bot = BotFactory.getInstance(properties.getProperty(Consts.BOT_TOKEN));
            bot.addEventListener(new GreetingListener());
        } catch (LoginException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static InputStream getResourceAsStream(final String filename) {
        return YaBotApplication.class.getResourceAsStream(filename);
    }
}
