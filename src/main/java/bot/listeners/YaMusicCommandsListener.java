package bot.listeners;

import bot.commands.Command;
import bot.commands.ICommandHandler;
import exceptions.InvalidCommandParamsException;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;

public final class YaMusicCommandsListener extends ListenerAdapter {
    private final ICommandHandler commandHandler;

    public YaMusicCommandsListener(final ICommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        try {
            final var command = event.getMessage().getContentStripped();

            final var currentCommand = commandHandler.handleCommand(command);

            execute(currentCommand, event);
        } catch (InvalidCommandParamsException e) {
            event.getMessage().getTextChannel().sendMessage("Invalid arguments, try: " +
                    e.getCommand().getPattern()).submit();
        } catch (Exception exception) {
            event.getMessage()
                    .getTextChannel()
                    .sendMessage("Command is failed.").submit();
        }
    }

    private void execute(final Command command, final GuildMessageReceivedEvent event) throws Exception {
        switch (command) {
            case HELP:
                event.getMessage().getTextChannel().sendMessage("HELP DOESN'T WORK").submit();
                return;
            case PLAY_SONG:
                handlePlaySong(event);
                return;
            case SKIP_SONG:
                event.getMessage().getTextChannel().sendMessage("SKIP DOESN'T WORK").submit();
                return;
            default:
                return;
        }
    }

    private void handlePlaySong(final GuildMessageReceivedEvent event) throws MalformedURLException {
        final var strippetCommand = event.getMessage().getContentStripped().split(" ");
        final var url = new URL(strippetCommand[1]);

        final var voiceChannel = event.getMember().getVoiceState().getChannel();

        if (voiceChannel == null) {
            event.getMessage().getTextChannel().sendMessage("You must be in voice channel").submit();
            return;
        }

        final var audioManager = event.getMember().getGuild().getAudioManager();

        audioManager.openAudioConnection(voiceChannel);
        //TODO: https://github.com/DV8FromTheWorld/JDA/wiki/4%29-Making-a-Music-Bot#Sending-Audio-to-an-Open-Audio-Connection
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        audioManager.closeAudioConnection();
    }
}
