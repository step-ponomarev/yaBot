package bot.listeners;

import audio.GuildPlayerManager;
import bot.command.Command;
import exceptions.InvalidCommandParamsException;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class SkipCommandListener extends ListenerAdapter {
    private final Command command;
    private final GuildPlayerManager playerManager;

    public SkipCommandListener(Command command, GuildPlayerManager playerManager) {
        this.command = command;
        this.playerManager = playerManager;
    }

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        final var message = event.getMessage().getContentStripped();

        if (!command.isCorrectCommand(message)) {
            return;
        }

        try {
            if (!command.isCorrectArgsAmount(message)) {
                throw new InvalidCommandParamsException(command);
            }

            handleSkipSong(event);
        } catch (InvalidCommandParamsException e) {
            event.getMessage().getTextChannel().sendMessage("Invalid arguments, try: " +
                    e.getCommand().getPattern()).queue();
        } catch (Exception exception) {
            event.getMessage()
                    .getTextChannel()
                    .sendMessage("Command is failed.").queue();
        }
    }

    private void handleSkipSong(GuildMessageReceivedEvent event) {
        final var guildId = event.getMessage().getGuild().getId();
        final var voiceChannel = event.getMember().getVoiceState().getChannel();
        final var audioManager = event.getMember().getGuild().getAudioManager();

        if (!audioManager.isConnected()) {
            return;
        }

        if (voiceChannel == null) {
            event.getMessage().getTextChannel().sendMessage("You must be in voice channel").queue();
            return;
        }

        playerManager.skipTrack(guildId);
    }
}
