package audio;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GuildAudioService {
    private AudioPlayer player;
    private AudioEventAdapter audioEventAdapter;
    private AudioLoadResultHandler loadResultHandler;
    private AudioPlayerSendHandler audioPlayerSendHandler;
}
