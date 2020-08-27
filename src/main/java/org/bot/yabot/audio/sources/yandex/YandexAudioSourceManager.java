package org.bot.yabot.audio.sources.yandex;

import com.sedmelluq.discord.lavaplayer.track.*;
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

import lombok.RequiredArgsConstructor;
import org.json.JSONObject;

import java.io.DataInput;
import java.io.DataOutput;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.util.ArrayList;

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

  private AudioItem handleReference(AudioReference reference) throws URISyntaxException {
    final YandexId id = urlParser.defineUrlType(reference.identifier);

    if (id == null) {
      return null;
    }

    System.err.println(id.type);

    AudioItem item = switch (id.type) {
      case PLAYLIST -> handlePlaylist(id.id);
      case TRACK -> handleTrack(id.id);
      case ALBUM -> handleAlbum(id.id);
    };

    return item;
  }

  private AudioTrack handleTrack(String id) throws URISyntaxException {
    final JSONObject trackInfo = apiService.getTrackInfoById(id);
    var audioTrackInfo = dataReader.createTrackInfo(trackInfo);

    return new Mp3AudioTrack(
        audioTrackInfo,
        new PersistentHttpStream(
            httpInterfaceManager.getInterface(),
            new URI(apiService.getTrackUrlById(id)),
            null)
    );
  }

  private BasicAudioPlaylist handlePlaylist(String id) {
    return null;
  }

  private BasicAudioPlaylist handleAlbum(String id) throws URISyntaxException {
    var albumInfo = apiService.getAlbumInfoById(id);
    var trackIdList = dataReader.getAlbumTrackIdList(albumInfo);

    var audioTrackList = new ArrayList<AudioTrack>();
    for (String trackId : trackIdList) {
      audioTrackList.add(handleTrack(trackId));
    }

    return new BasicAudioPlaylist("Album " + id, audioTrackList, audioTrackList.get(0), false);
  }

  @Override
  public boolean isTrackEncodable(AudioTrack track) {
    return true;
  }

  @Override
  public void encodeTrack(AudioTrack track, DataOutput output) {
  }

  @Override
  public AudioTrack decodeTrack(AudioTrackInfo trackInfo, DataInput input) {
    return null;
  }

  @Override
  public void shutdown() {
  }
}
