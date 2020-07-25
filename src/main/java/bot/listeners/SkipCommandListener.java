package bot.listeners;

import bot.commands.Command;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class SkipCommandListener extends ListenerAdapter {
    private final Command command;

    public SkipCommandListener(Command command) {
        this.command = command;
    }

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        if (event.getMessage().getContentRaw().startsWith(command.getCommand())) {
            event.getMessage().getTextChannel().sendMessage("COMMAND ISN'T SUPPORTED YET").queue();
        }
    }
}
