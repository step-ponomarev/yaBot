package bot.factory;

import audio.AudioFactory;
import bot.commands.Command;
import bot.listeners.GreetingListener;
import bot.listeners.CommandListener;
import bot.listeners.SkipCommandListener;
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
            //TODO: Кажется приедтся сделать 1 хендлер на команды. Нам нужен 1 плеер на комнату....
            bot = JDABuilder.createDefault(token)
                    .addEventListeners(
                            new GreetingListener(),
                            new CommandListener(AudioFactory.createPlayerManager()),

                    .build();
        }

        return bot;
    }
}


