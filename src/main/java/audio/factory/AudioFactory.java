package audio.factory;

import audio.sources.yandex.YandexAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;

public class AudioFactory {
  public static AudioPlayerManager playerManager = null;

  public static AudioPlayerManager createPlayerManager() {
    if (playerManager == null) {
      playerManager = new DefaultAudioPlayerManager();
      //AudioSourceManagers.registerRemoteSources(playerManager);
      playerManager.registerSourceManager(
          YandexAudioSourceManager.createDefaultYandexSourceManager()
      );
    }

    return playerManager;
  }
}
