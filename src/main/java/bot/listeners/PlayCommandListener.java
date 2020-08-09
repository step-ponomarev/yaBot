package bot.listeners;

import audio.guild.GuildPlayerFasade;
import bot.command.Command;
import exceptions.InvalidCommandParamsException;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.net.MalformedURLException;
import java.net.URL;

//DOCS: https://github.com/DV8FromTheWorld/JDA/wiki/4%29-Making-a-Music-Bot#Sending-Audio-to-an-Open-Audio-Connection
@RequiredArgsConstructor
public final class PlayCommandListener extends ListenerAdapter {
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

      handlePlaySong(event);
    } catch (InvalidCommandParamsException e) {
      event.getMessage().getTextChannel().sendMessage("Invalid arguments, try: " +
          e.getCommand().getPattern()).queue();
    } catch (Exception exception) {
      event.getMessage()
          .getTextChannel()
          .sendMessage("Command is failed.").queue();
    }
  }

  private void handlePlaySong(final GuildMessageReceivedEvent event) throws MalformedURLException {
    final var strippetCommand = event.getMessage().getContentStripped().split(" ");
    final var voiceChannel = event.getMember().getVoiceState().getChannel();
    final var audioManager = event.getMember().getGuild().getAudioManager();

    final var url = new URL(strippetCommand[1]);
    final var guildId = event.getMessage().getGuild().getId();

    if (voiceChannel == null) {
      event.getMessage().getTextChannel().sendMessage("You must be in voice channel").queue();
      return;
    }

    if (!playerManager.isInitialized(guildId)) {
      playerManager.init(guildId);

      final var sendHandler = playerManager.getSendHandler(guildId);
      audioManager.setSendingHandler(sendHandler);
    }

    final var trackPath = url.toString(); //"https://youtu.be/MltJnhBLGtw";
    playerManager.addTrack(trackPath, guildId);

    if (!audioManager.isConnected()) {
      audioManager.openAudioConnection(voiceChannel);
    }
  }
}