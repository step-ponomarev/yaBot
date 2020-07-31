package bot.listeners;

import audio.guild.GuildPlayerFasade;
import bot.command.Command;
import exceptions.InvalidCommandParamsException;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

@RequiredArgsConstructor
public class ResumeCommandListener extends ListenerAdapter {
    private final Command command;
    private final GuildPlayerFasade playerManager;

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

            handleResumeSong(event);
        } catch (InvalidCommandParamsException e) {
            event.getMessage().getTextChannel().sendMessage("Invalid arguments, try: " +
                    e.getCommand().getPattern()).queue();
        } catch (Exception exception) {
            event.getMessage()
                    .getTextChannel()
                    .sendMessage("Command is failed.").queue();
        }
    }

    private void handleResumeSong(GuildMessageReceivedEvent event) {
        final var guildId = event.getGuild().getId();

        playerManager.init(guildId);
        playerManager.resumePlay(guildId);
    }
}
