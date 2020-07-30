package audio.guild;

import audio.TrackScheduleAdapter;
import audio.handlers.AudioPlayerSendHandler;
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
