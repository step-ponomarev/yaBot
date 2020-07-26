package bot.listeners;

import audio.AudioFactory;
import audio.LoadResultHandler;
import audio.TrackScheduler;
import bot.commands.Command;
import bot.handler.AudioPlayerSendHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import exceptions.InvalidCommandParamsException;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

import javax.annotation.Nonnull;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;

public final class CommandListener extends ListenerAdapter {
    private final AudioSendHandler audioSendHandler;
    private final AudioPlayerManager audioPlayerManager;
    private final AudioPlayer player;

    public CommandListener(
            final AudioSendHandler audioSendHandler,
            final AudioPlayerManager audioPlayerManager
    ) {
        this.audioSendHandler = audioSendHandler;
        this.audioPlayerManager = audioPlayerManager;
        this.player = audioPlayerManager.createPlayer();

        TrackScheduler trackScheduler = new TrackScheduler(player);
        player.addListener(trackScheduler);
    }

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        final boolean isPlayCommand = event.getMessage().getContentRaw().startsWith(Command.PLAY_SONG.getCommand());

        try {
            if (!isPlayCommand) {
                return;
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


    private void handlePlaySong(final GuildMessageReceivedEvent event) throws MalformedURLException, FileNotFoundException {
        final var strippetCommand = event.getMessage().getContentStripped().split(" ");
        final var url = new URL(strippetCommand[1]);

        final var voiceChannel = event.getMember().getVoiceState().getChannel();

        if (voiceChannel == null) {
            //TODO: Exception??
            event.getMessage().getTextChannel().sendMessage("You must be in voice channel").queue();
            return;
        }

        //TODO: Сделать красиов ежжи (рефакторинг)
        final var audioManager = event.getMember().getGuild().getAudioManager();

        final var playerManager = AudioFactory.createPlayerManager();
        final AudioPlayer player = playerManager.createPlayer();

        TrackScheduler trackScheduler = new TrackScheduler(player);
        player.addListener(trackScheduler);

        //TODO: Mock
        final var mockedTrackPath = url.toString(); //"https://youtu.be/MltJnhBLGtw";
        playerManager.loadItem(mockedTrackPath, new LoadResultHandler(trackScheduler));

        audioManager.setSendingHandler(new AudioPlayerSendHandler(player));
        audioManager.openAudioConnection(voiceChannel);

        //TODO: https://github.com/DV8FromTheWorld/JDA/wiki/4%29-Making-a-Music-Bot#Sending-Audio-to-an-Open-Audio-Connection
    }

    private boolean isCorrectCommand(String contentStripped, Command command) {
        return contentStripped.split(" ").length == command.getLength();
    }

    private void execute(GuildMessageReceivedEvent event) {
        // Проверяем что за команда.
        // Проверяем количество аргументов
        // Кайфуем
    }
}