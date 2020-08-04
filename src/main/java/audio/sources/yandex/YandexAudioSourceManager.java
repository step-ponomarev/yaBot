package audio.sources.yandex;

import audio.sources.yandex.dataReader.DefaultYandexDataReader;
import audio.sources.yandex.dataReader.YandexDataReader;
import audio.sources.yandex.parser.DefaultYandexUrlParser;
import audio.sources.yandex.parser.YandexUrlParser;
import audio.sources.yandex.service.DefaultYandexApiService;
import audio.sources.yandex.service.YandexApiService;
import audio.sources.yandex.structures.YandexId;
import com.sedmelluq.discord.lavaplayer.container.mp3.Mp3AudioTrack;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManager;
import com.sedmelluq.discord.lavaplayer.tools.JsonBrowser;
import com.sedmelluq.discord.lavaplayer.track.AudioItem;
import com.sedmelluq.discord.lavaplayer.track.AudioReference;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.net.http.HttpClient;

@RequiredArgsConstructor
public class YandexAudioSourceManager implements AudioSourceManager {
  private final YandexUrlParser urlParser;
  private final YandexApiService apiService;
  private final YandexDataReader dataReader;

  public static YandexAudioSourceManager createDefaultYandexSourceManager() {
    final YandexUrlParser urlParser = new DefaultYandexUrlParser();
    final YandexApiService apiService = new DefaultYandexApiService(
        HttpClient.newHttpClient()
    );
    final YandexDataReader dataReader = new DefaultYandexDataReader();

    final YandexAudioSourceManager sourceManager = new YandexAudioSourceManager(urlParser, apiService, dataReader);

    return sourceManager;
  }

  @Override
  public String getSourceName() {
    return "yandex";
  }

  @Override
  public AudioItem loadItem(DefaultAudioPlayerManager manager, AudioReference reference) {
    final AudioItem audioItem = handleReference(reference);

    // new Mp3AudioTrack() Должны вернуть для трека
    // new BasicAudioPlaylist() Для плейлиста/артиста
    // Mp3AudioTrack();

    return audioItem;
  }

  private AudioItem handleReference(AudioReference reference) {
    final YandexId id = urlParser.defineUrlType(reference.identifier);

    if (id == null) {
      return null;
    }

    JSONObject infoJson = null;
    AudioItem item = null;
    switch (id.getType()) {
      case TRACK:
        infoJson = apiService.getTrackInfoById(id.id);
        infoJson = dataReader.findTrackData(infoJson);

      case ALBUM:
      case PLAYLIST:
        infoJson = apiService.getPlayListInfoById(id.id);
    }

    return item;
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
    System.out.println("KEK");
    return null;
  }

  @Override
  public void shutdown() {

  }
}
