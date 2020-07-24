import bot.factory.BotFactory;
import bot.listeners.GreetingListener;
import config.ApplicationProperties;
import consts.Consts;

import javax.security.auth.login.LoginException;

public class YaBotApplication {
    static final ApplicationProperties properties = new ApplicationProperties(
            YaBotApplication.class.getResourceAsStream(Consts.PROPERTIES_FILE_NAME)
    );

    public static void main(String[] args) {
        try {
            var bot = BotFactory.getInstance(properties.getProperty(Consts.BOT_TOKEN));
            bot.addEventListener(new GreetingListener());
        } catch (LoginException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
