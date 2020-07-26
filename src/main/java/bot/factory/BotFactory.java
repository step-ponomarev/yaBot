package bot.factory;

import audio.AudioFactory;
import audio.LoadResultHandler;
import audio.TrackScheduler;
import bot.handler.AudioPlayerSendHandler;
import bot.listeners.GreetingListener;
import bot.listeners.CommandListener;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
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


            //TODO: вынести это дело в отдельные методы или не надо...
            AudioPlayerManager playerManager = AudioFactory.createPlayerManager();

            final var greetingListener = new GreetingListener();
            final var commandListener = new CommandListener(playerManager);

            bot = JDABuilder.createDefault(token)
                    .addEventListeners(greetingListener, commandListener)
                    .build();
        }

        return bot;
    }
}


