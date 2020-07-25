import bot.commands.YaMusicCommandHandler;
import bot.factory.BotFactory;
import bot.listeners.GreetingListener;
import bot.listeners.YaMusicCommandsListener;
import config.ApplicationProperties;
import consts.Consts;

import java.io.InputStream;

public final class YaBotApplication {
    static final ApplicationProperties properties = new ApplicationProperties();

    public static void main(String[] args) {
        try {
            properties.init(getResourceAsStream(Consts.PROPERTIES_FILE_NAME));

            var bot = BotFactory.getInstance(properties.getProperty(Consts.BOT_TOKEN));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static InputStream getResourceAsStream(final String filename) {
        return YaBotApplication.class.getResourceAsStream(filename);
    }
}
