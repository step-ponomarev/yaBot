package audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;

public class AudioFactory {
    public static AudioPlayerManager playerManager = null;

    public static AudioPlayerManager createPlayerManager() {
        if (playerManager == null) {
            playerManager = new DefaultAudioPlayerManager();
            AudioSourceManagers.registerLocalSource(playerManager);
            AudioSourceManagers.registerRemoteSources(playerManager);
        }

        return playerManager;
    }

    public static AudioPlayer createAudioPlayer(final AudioPlayerManager playerManager) {
        var player = playerManager.createPlayer();
        player.addListener(new TrackScheduler(player));

        return player;
    }
}
