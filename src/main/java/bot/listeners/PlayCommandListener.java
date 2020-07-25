package bot.listeners;

import bot.commands.Command;
import exceptions.InvalidCommandParamsException;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
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


    private void handlePlaySong(final GuildMessageReceivedEvent event) throws MalformedURLException {
        final var strippetCommand = event.getMessage().getContentStripped().split(" ");
        final var url = new URL(strippetCommand[1]);

        final var voiceChannel = event.getMember().getVoiceState().getChannel();

        if (voiceChannel == null) {
            //TODO: Exception??
            event.getMessage().getTextChannel().sendMessage("You must be in voice channel").queue();
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

    private boolean isCorrectCommand(String contentStripped) {
        return contentStripped.split(" ").length == command.getLength();
    }
}
