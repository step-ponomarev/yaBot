package org.bot.yabot.audio.guild;

import org.bot.yabot.audio.TrackScheduleAdapter;
import org.bot.yabot.audio.handlers.AudioPlayerSendHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GuildAudioServiceSet {
  private final AudioPlayer player;
  private final TrackScheduleAdapter trackScheduleAdapter;
  private final AudioLoadResultHandler loadResultHandler;
  private final AudioPlayerSendHandler audioPlayerSendHandler;
}
