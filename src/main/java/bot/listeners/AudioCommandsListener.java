package bot.listeners;

import bot.player.GuildPlayer;
import bot.command.Command;
import exceptions.InvalidCommandParamsException;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;

//DOCS: https://github.com/DV8FromTheWorld/JDA/wiki/4%29-Making-a-Music-Bot#Sending-Audio-to-an-Open-Audio-Connection

public final class AudioCommandsListener extends ListenerAdapter {
    private final GuildPlayer player;

    public AudioCommandsListener(final GuildPlayer guildPlayer) {
        this.player = guildPlayer;
    }

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        final var message = event.getMessage().getContentStripped();
        final var command = defineCommand(message);

        if (command == null) {
            return;
        }

        try {
            checkCommandParams(message, command);
            executeCommand(event, command);
        } catch (InvalidCommandParamsException e) {
            event.getMessage().getTextChannel().sendMessage("Invalid arguments, try: " +
                    e.getCommand().getPattern()).queue();
        } catch (Exception exception) {
            event.getMessage()
                    .getTextChannel()
                    .sendMessage("Command is failed.").queue();
        }
    }

    private Command defineCommand(String message) {
        final boolean isPlayCommand = message.startsWith(Command.PLAY_SONG.getCommand());
        final boolean isSkipCommand = message.startsWith(Command.SKIP_SONG.getCommand());

        if (isPlayCommand) {
            return Command.PLAY_SONG;
        } else if (isSkipCommand) {
            return Command.SKIP_SONG;
        }

        return null;
    }

    private void checkCommandParams(String message, Command command) {
        if (!isCorrectCommand(message, command)) {
            throw new InvalidCommandParamsException(command);
        }
    }

    private boolean isCorrectCommand(String contentStripped, Command command) {
        return contentStripped.split(" ").length == command.getLength();
    }

    private void executeCommand(GuildMessageReceivedEvent event, Command command) throws MalformedURLException, FileNotFoundException {

        switch (command) {
            case PLAY_SONG:
                handlePlaySong(event);
                break;
            case SKIP_SONG:
                return;

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

        player.init(guildId);

        final var audioManager = event.getMember().getGuild().getAudioManager();
        final var sendHandler = player.getSendHandler(guildId);
        audioManager.setSendingHandler(sendHandler);

        final var mockedTrackPath = url.toString(); //"https://youtu.be/MltJnhBLGtw";
        player.addTrack(mockedTrackPath, guildId);

        audioManager.openAudioConnection(voiceChannel);
    }
}