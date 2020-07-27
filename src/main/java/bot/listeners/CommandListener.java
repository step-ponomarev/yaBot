package bot.listeners;

import audio.LoadResultHandler;
import audio.TrackScheduler;
import bot.commands.Command;
import bot.handler.AudioPlayerSendHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import exceptions.InvalidCommandParamsException;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

//DOCS: https://github.com/DV8FromTheWorld/JDA/wiki/4%29-Making-a-Music-Bot#Sending-Audio-to-an-Open-Audio-Connection

public final class CommandListener extends ListenerAdapter {
    private final AudioPlayerManager audioPlayerManager;
    private final Map<String, LoadResultHandler> audioLoadHandlers;

    public CommandListener
            (
                    final AudioPlayerManager audioPlayerManager
            ) {

        this.audioPlayerManager = audioPlayerManager;
        this.audioLoadHandlers = new HashMap<>();
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
        final var guildId = event.getMessage().getGuild().getId();

        //TODO: Реализовать раздельную обрабаботку запросов для каждой guild

        // Проверяем сохранен ли у нас LoadResultHandler по такому guildId
        // если да, то ничего не делаем,
        // если нет, до создаем и добавляем

        final var voiceChannel = event.getMember().getVoiceState().getChannel();

        if (voiceChannel == null) {
            //TODO: Exception??
            event.getMessage().getTextChannel().sendMessage("You must be in voice channel").queue();
            return;
        }

        //TODO: Сделать красиов ежжи (рефакторинг)
        final var audioManager = event.getMember().getGuild().getAudioManager();

        if (!audioLoadHandlers.containsKey(guildId)) {
            //Все ниже должно создаваться для каждой гилды свое
            var player = audioPlayerManager.createPlayer();
            var trackScheduler = new TrackScheduler(player);
            player.addListener(trackScheduler);

            var audioPlayerSendHandler = new AudioPlayerSendHandler(player);
            audioManager.setSendingHandler(audioPlayerSendHandler);

            audioLoadHandlers.put(guildId, new LoadResultHandler(trackScheduler));
        }

        //TODO: Mock
        final var mockedTrackPath = url.toString(); //"https://youtu.be/MltJnhBLGtw";
        audioPlayerManager.loadItem(mockedTrackPath, audioLoadHandlers.get(guildId));

        audioManager.openAudioConnection(voiceChannel);
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