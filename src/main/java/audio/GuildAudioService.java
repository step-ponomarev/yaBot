package audio;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GuildAudioService {
    private final AudioPlayer player;
    private final TrackScheduler trackScheduler;
    private final AudioLoadResultHandler loadResultHandler;
    private final AudioPlayerSendHandler audioPlayerSendHandler;
}
