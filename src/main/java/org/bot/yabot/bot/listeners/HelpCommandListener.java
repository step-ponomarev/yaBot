package org.bot.yabot.bot.listeners;

import org.bot.yabot.audio.guild.GuildPlayerFasade;
import org.bot.yabot.bot.command.Command;
import org.bot.yabot.exceptions.InvalidCommandParamsException;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class HelpCommandListener extends ListenerAdapter {
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

      handleHelp(event);
    } catch (InvalidCommandParamsException e) {
      event.getMessage().getTextChannel().sendMessage("Invalid arguments, try: " +
          e.getCommand().getPattern()).queue();
    } catch (Exception exception) {
      event.getMessage()
          .getTextChannel()
          .sendMessage("Command is failed.").queue();
    }
  }

  private void handleHelp(final GuildMessageReceivedEvent event) {
    final var guildId = event.getGuild().getId();
    String commandsPattern = "Command list: \n";

    if (!playerManager.isInitialized(guildId)) {
      playerManager.init(guildId);
    }

    commandsPattern += Arrays.stream(Command.values()).map(c ->
        c.getPattern() + " - " + c.getDiscription() + "\n"
    ).collect(Collectors.joining());

    event.getMessage().getTextChannel().sendMessage(commandsPattern).submit();
  }
}
