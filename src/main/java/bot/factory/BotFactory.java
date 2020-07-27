package bot.factory;

import audio.AudioFactory;
import audio.GuildPlayerManager;
import bot.command.Command;
import bot.listeners.GreetingListener;
import bot.listeners.PlayCommandListener;
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
            //TODO: вынести это дело в отдельные методы или не надо...
            AudioPlayerManager playerManager = AudioFactory.createPlayerManager();
            GuildPlayerManager guildPlayerManager = new GuildPlayerManager(playerManager);

            final var greetingListener = new GreetingListener();
            final var commandListener = new PlayCommandListener(Command.PLAY_SONG, guildPlayerManager);

            bot = JDABuilder.createDefault(token)
                    .addEventListeners(greetingListener, commandListener)
                    .build();
        }

        return bot;
    }
}


