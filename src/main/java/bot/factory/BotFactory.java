package bot.factory;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class BotFactory {
    private static JDA bot = null;

    /**
     * Creates singleton bot;
     *
     * @param token
     * @return
     * @throws LoginException
     */
    public static JDA getInstance(final String token) throws LoginException {
        if (bot == null) {
            //TODO: ДОбавить конфигурации
            bot = JDABuilder.createDefault(token)
                    .build();
        }

        return bot;
    }
}
