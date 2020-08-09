package org.bot.yabot.bot.factory;

import org.bot.yabot.audio.factory.AudioFactory;
import org.bot.yabot.audio.guild.GuildPlayerFasade;
import org.bot.yabot.bot.command.Command;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.bot.yabot.bot.listeners.*;

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
      AudioPlayerManager playerManager = AudioFactory.createPlayerManager();
      GuildPlayerFasade guildPlayerFasade = new GuildPlayerFasade(playerManager);

      final var greetingListener = new GreetingListener();
      final var playCommandListener = new PlayCommandListener(Command.PLAY_SONG, guildPlayerFasade);
      final var skipCommandListener = new SkipCommandListener(Command.SKIP_SONG, guildPlayerFasade);
      final var pauseCommandListener = new PauseCommandListener(Command.PAUSE_SONG, guildPlayerFasade);
      final var resumeCommandListener = new ResumeCommandListener(Command.RESUME_SONG, guildPlayerFasade);
      final var helpCommandListener = new HelpCommandListener(Command.HELP, guildPlayerFasade);

      bot = JDABuilder.createDefault(token)
          .addEventListeners(
              greetingListener,
              playCommandListener,
              skipCommandListener,
              pauseCommandListener,
              resumeCommandListener,
              helpCommandListener)
          .build();
    }

    return bot;
  }
}


