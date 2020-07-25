package bot.listeners;

import bot.commands.Command;
import bot.commands.ICommandHandler;
import exceptions.InvalidCommandParamsException;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

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
                    .sendMessage("Unknown Error!").submit();
        }
    }

    private void execute(final Command command, final GuildMessageReceivedEvent event) {
        switch (command) {
            case HELP:
                event.getMessage().getTextChannel().sendMessage("HELP DOESN'T WORK").submit();
                return;
            case PLAY_SONG:
                event.getMessage().getTextChannel().sendMessage("PLAY DOESN'T WORK").submit();
                return;
            case SKIP_SONG:
                event.getMessage().getTextChannel().sendMessage("SKIP DOESN'T WORK").submit();
                return;
            default:
                return;
        }
    }
}
