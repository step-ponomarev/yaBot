package audio.factory;

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
}
