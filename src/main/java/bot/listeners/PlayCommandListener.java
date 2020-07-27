package bot.listeners;

import audio.GuildPlayerManager;
import bot.command.Command;
import exceptions.InvalidCommandParamsException;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.net.MalformedURLException;
import java.net.URL;

//DOCS: https://github.com/DV8FromTheWorld/JDA/wiki/4%29-Making-a-Music-Bot#Sending-Audio-to-an-Open-Audio-Connection

public final class PlayCommandListener extends ListenerAdapter {
    private final Command command;
    private final GuildPlayerManager playerManager;

    public PlayCommandListener(final Command command, final GuildPlayerManager guildPlayerManager) {
        this.command = command;
        this.playerManager = guildPlayerManager;
    }

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        final var message = event.getMessage().getContentStripped();

        try {
            if (!command.isCorrectCommand(message)) {
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
        final var url = new URL(strippetCommand[1]);
        final var guildId = event.getMessage().getGuild().getId();

        final var voiceChannel = event.getMember().getVoiceState().getChannel();

        if (voiceChannel == null) {
            //TODO: Exception??
            event.getMessage().getTextChannel().sendMessage("You must be in voice channel").queue();
            return;
        }

        playerManager.init(guildId);

        final var audioManager = event.getMember().getGuild().getAudioManager();

        final var sendHandler = playerManager.getSendHandler(guildId);
        audioManager.setSendingHandler(sendHandler);

        final var mockedTrackPath = url.toString(); //"https://youtu.be/MltJnhBLGtw";
        playerManager.addTrack(mockedTrackPath, guildId);

        if (!audioManager.isConnected()) {
            audioManager.openAudioConnection(voiceChannel);
        }
    }
}