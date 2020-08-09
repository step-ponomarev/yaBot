package org.bot.yabot.audio.factory;

import org.bot.yabot.audio.sources.yandex.YandexAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;

public class AudioFactory {
  public static AudioPlayerManager playerManager = null;

  public static AudioPlayerManager createPlayerManager() {
    if (playerManager == null) {
      playerManager = new DefaultAudioPlayerManager();
      playerManager.registerSourceManager(
          YandexAudioSourceManager.createDefaultYandexSourceManager()
      );
      AudioSourceManagers.registerRemoteSources(playerManager);
    }

    return playerManager;
  }
}
