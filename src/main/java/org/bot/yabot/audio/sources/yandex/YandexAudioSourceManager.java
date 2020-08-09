package org.bot.yabot.audio.sources.yandex;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.tools.PlayerLibrary;
import org.bot.yabot.audio.sources.yandex.dataReader.DefaultYandexDataReader;
import org.bot.yabot.audio.sources.yandex.dataReader.YandexDataReader;
import org.bot.yabot.audio.sources.yandex.parser.DefaultYandexUrlParser;
import org.bot.yabot.audio.sources.yandex.parser.YandexUrlParser;
import org.bot.yabot.audio.sources.yandex.service.DefaultYandexApiService;
import org.bot.yabot.audio.sources.yandex.service.YandexApiService;
import org.bot.yabot.audio.sources.yandex.structures.YandexId;
import com.sedmelluq.discord.lavaplayer.container.mp3.Mp3AudioTrack;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManager;
import com.sedmelluq.discord.lavaplayer.tools.io.*;
import com.sedmelluq.discord.lavaplayer.track.AudioItem;
import com.sedmelluq.discord.lavaplayer.track.AudioReference;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

import lombok.RequiredArgsConstructor;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;


@RequiredArgsConstructor
public class YandexAudioSourceManager implements AudioSourceManager {
  private final YandexUrlParser urlParser;
  private final YandexApiService apiService;
  private final YandexDataReader dataReader;
  private final HttpInterfaceManager httpInterfaceManager;

  public static YandexAudioSourceManager createDefaultYandexSourceManager() {
    final YandexUrlParser urlParser = new DefaultYandexUrlParser();
    final YandexApiService apiService = new DefaultYandexApiService(
        HttpClient.newHttpClient()
    );
    final YandexDataReader dataReader = new DefaultYandexDataReader();
    final HttpInterfaceManager httpInterfaceManager = HttpClientTools.createDefaultThreadLocalManager();

    final YandexAudioSourceManager sourceManager = new YandexAudioSourceManager(urlParser, apiService, dataReader, httpInterfaceManager);

    return sourceManager;
  }

  @Override
  public String getSourceName() {
    return "yandex";
  }

  @Override
  public AudioItem loadItem(DefaultAudioPlayerManager manager, AudioReference reference) {
    try {
      final AudioItem audioItem = handleReference(reference);

      return audioItem;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  private AudioItem handleReference(AudioReference reference) throws IOException, URISyntaxException, UnsupportedAudioFileException {
    final YandexId id = urlParser.defineUrlType(reference.identifier);

    if (id == null) {
      return null;
    }

    var buffer = apiService.getTrackInputStreamById(id.id);

    var trackInfo = new AudioTrackInfo(
        "TEST",
        "TEST",
        1000,
        "yandex",
        false,
        apiService.getTrackUrlById(id.id)
    );

//    return new Mp3AudioTrack(trackInfo,
//        new PersistentHttpStream(
//            httpInterfaceManager.getInterface(),
//            apiService.getTrackUriById(id.id),
//            null)
//    );

    return new Mp3AudioTrack(trackInfo, new PersistentHttpStream(
        httpInterfaceManager.getInterface(),
        new URI(apiService.getTrackUrlById(id.id)),
        null));
  }

  @Override
  public boolean isTrackEncodable(AudioTrack track) {
    return true;
  }

  @Override
  public void encodeTrack(AudioTrack track, DataOutput output) throws IOException {

  }

  @Override
  public AudioTrack decodeTrack(AudioTrackInfo trackInfo, DataInput input) throws IOException {
    return null;
  }

  @Override
  public void shutdown() {

  }
}
