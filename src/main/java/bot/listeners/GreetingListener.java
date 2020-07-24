package bot.listeners;

import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.UnavailableGuildJoinedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class GreetingListener extends ListenerAdapter {
    @Override
    public void onUnavailableGuildJoined(@Nonnull UnavailableGuildJoinedEvent event) {
        super.onUnavailableGuildJoined(event);
    }

    @Override
    public void onGuildJoin(@Nonnull GuildJoinEvent event) {
        event.getGuild().getTextChannels().get(0).sendMessage("Привет, я музыкальный бот, который пока не работает.").submit();
    }
}
