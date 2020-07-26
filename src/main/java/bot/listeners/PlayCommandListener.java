package bot.listeners;

import audio.AudioFactory;
import audio.TrackScheduler;
import bot.commands.Command;
import bot.handler.AudioPlayerSendHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import exceptions.InvalidCommandParamsException;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;

public final class PlayCommandListener extends ListenerAdapter {
    private final Command command;

    public PlayCommandListener(final Command command) {
        this.command = command;
    }

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        try {
            final boolean isPlayCommand = event.getMessage().getContentRaw().startsWith(command.getCommand());
            if (!isPlayCommand) {
                return;
            }

            final var textCommand = event.getMessage().getContentStripped();
            if (!isCorrectCommand(textCommand)) {
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

        final var mockedTrackPath = url.toString();//"https://youtu.be/MltJnhBLGtw";
        playerManager.loadItem(mockedTrackPath, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                trackScheduler.queue(track);
                trackScheduler.play();
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                for (AudioTrack track : playlist.getTracks()) {
                    trackScheduler.queue(track);
                }
                trackScheduler.play();
            }

            @Override
            public void noMatches() {
                // Notify the user that we've got nothing
                System.err.println("NOMATHES");
            }

            @Override
            public void loadFailed(FriendlyException throwable) {
                System.err.println("LOAD FAILED" + throwable.getMessage());
                // Notify the user that everything exploded
            }
        });

        audioManager.setSendingHandler(new AudioPlayerSendHandler(player));
        audioManager.openAudioConnection(voiceChannel);


        //TODO: https://github.com/DV8FromTheWorld/JDA/wiki/4%29-Making-a-Music-Bot#Sending-Audio-to-an-Open-Audio-Connection
    }

    private boolean isCorrectCommand(String contentStripped) {
        return contentStripped.split(" ").length == command.getLength();
    }
}