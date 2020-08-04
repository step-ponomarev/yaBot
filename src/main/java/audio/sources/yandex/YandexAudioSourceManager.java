package audio.sources.yandex;

import audio.sources.yandex.dataReader.DefaultYandexDataReader;
import audio.sources.yandex.dataReader.YandexDataReader;
import audio.sources.yandex.parser.DefaultYandexUrlParser;
import audio.sources.yandex.parser.YandexUrlParser;
import audio.sources.yandex.service.DefaultYandexApiService;
import audio.sources.yandex.service.YandexApiService;
import audio.sources.yandex.structures.YandexId;
import com.sedmelluq.discord.lavaplayer.container.flac.FlacAudioTrack;
import com.sedmelluq.discord.lavaplayer.container.matroska.MatroskaStreamingFile;
import com.sedmelluq.discord.lavaplayer.container.mp3.Mp3AudioTrack;
import com.sedmelluq.discord.lavaplayer.format.AudioPlayerInputStream;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManager;
import com.sedmelluq.discord.lavaplayer.tools.io.*;
import com.sedmelluq.discord.lavaplayer.track.AudioItem;
import com.sedmelluq.discord.lavaplayer.track.AudioReference;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import com.sedmelluq.discord.lavaplayer.track.info.AudioTrackInfoBuilder;
import lombok.RequiredArgsConstructor;

import javax.sound.midi.Track;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpClient;

import static com.sedmelluq.discord.lavaplayer.tools.Units.DURATION_MS_UNKNOWN;

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

  private AudioItem handleReference(AudioReference reference) throws IOException, UnsupportedAudioFileException, URISyntaxException {
    final YandexId id = urlParser.defineUrlType(reference.identifier);

    if (id == null) {
      return null;
    }

    var buffer = apiService.getTrackInputStreamById(id.id);
    var trackInfo = new AudioTrackInfo(
        "TEST",
        "TEST",
        DURATION_MS_UNKNOWN,
        "SECRET",
        false,
        apiService.getTrackUrlById(id.id)
    );

//    return new Mp3AudioTrack(trackInfo,
//        new PersistentHttpStream(
//            httpInterfaceManager.getInterface(),
//            apiService.getTrackUriById(id.id),
//            null)
//    );
    return new Mp3AudioTrack(trackInfo, new NonSeekableInputStream(buffer));


//    JSONObject infoJson = null;
//    AudioItem item = null;
//    switch (id.getType()) {
//      case TRACK:
//        infoJson = apiService.getTrackInfoById(id.id);
//        infoJson = dataReader.findTrackData(infoJson);
//
//      case ALBUM:
//      case PLAYLIST:
//        infoJson = apiService.getPlayListInfoById(id.id);
//    }

    //return item;
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
