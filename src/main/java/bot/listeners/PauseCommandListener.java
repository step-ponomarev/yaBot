package bot.listeners;

import audio.guild.GuildPlayerFasade;
import bot.command.Command;
import exceptions.InvalidCommandParamsException;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

@RequiredArgsConstructor
public class PauseCommandListener extends ListenerAdapter {
  private final Command command;
  private final GuildPlayerFasade playerManager;

  @Override
  public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
    final var message = event.getMessage().getContentStripped();

    if (event.getMessage().getAuthor().isBot()) {
      return;
    }

    if (!command.isCorrectCommand(message)) {
      return;
    }

    try {
      if (!command.isCorrectArgsAmount(message)) {
        throw new InvalidCommandParamsException(command);
      }

      handlePauseSong(event);
    } catch (InvalidCommandParamsException e) {
      event.getMessage().getTextChannel().sendMessage("Invalid arguments, try: " +
          e.getCommand().getPattern()).queue();
    } catch (Exception exception) {
      event.getMessage()
          .getTextChannel()
          .sendMessage("Command is failed.").queue();
    }
  }

  public void handlePauseSong(GuildMessageReceivedEvent event) {
    final var guildId = event.getGuild().getId();
    final var voiceChannel = event.getMember().getVoiceState().getChannel();

    if (voiceChannel == null) {
      event.getMessage().getTextChannel().sendMessage("You must be in voice channel").queue();
      return;
    }

    if (!playerManager.isInitialized(guildId)) {
      playerManager.init(guildId);

      playerManager.pauseTrack(guildId);
    }
  }
}
