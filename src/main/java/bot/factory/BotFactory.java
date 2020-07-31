package bot.factory;

import audio.factory.AudioFactory;
import audio.guild.GuildPlayerFasade;
import bot.command.Command;
import bot.listeners.*;
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
            GuildPlayerFasade guildPlayerFasade = new GuildPlayerFasade(playerManager);

            final var greetingListener = new GreetingListener();
            final var playCommandListener = new PlayCommandListener(Command.PLAY_SONG, guildPlayerFasade);
            final var skipCommandListener = new SkipCommandListener(Command.SKIP_SONG, guildPlayerFasade);
            final var pauseCommandListener = new PauseCommandListener(Command.PAUSE_SONG, guildPlayerFasade);
            final var resumeCommandListener = new ResumeCommandListener(Command.RESUME_SONG, guildPlayerFasade);

            bot = JDABuilder.createDefault(token)
                    .addEventListeners(
                            greetingListener,
                            playCommandListener,
                            skipCommandListener,
                            pauseCommandListener,
                            resumeCommandListener)
                    .build();
        }

        return bot;
    }
}


