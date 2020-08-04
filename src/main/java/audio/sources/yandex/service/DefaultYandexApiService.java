package audio.sources.yandex.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.text.StringSubstitutor;
import org.json.JSONObject;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@RequiredArgsConstructor
public class DefaultYandexApiService implements YandexApiService {
  private final HttpClient httpClient;

  private final String GET_TRACK_INFO_API_TEMPLATE = "https://music.yandex.ru/handlers/track.jsx?track=${trackID}";
  private final String GET_STORAGE_INFO_API_TEMPLATE = "https://storage.mds.yandex.net/download-info/${storageDir}/2?format=json";
  private final String TRACK_LINK_TEMPLATE = "https://${host}/get-mp3/${hashedUrl}/${ts}${path}";
  private final String SECRET_KEY = "XGRlBW9FXlekgbPrRHuSiA";

  @Override
  public JSONObject getTrackInfoById(String id) {
    final HttpRequest request = createGetTrackInfoRequest(id);

    return GET(request);
  }

  private HttpRequest createGetTrackInfoRequest(String id) {
    final String url = createGetTrackInfoUrl(id);

    return HttpRequest.newBuilder().uri(URI.create(url)).build();
  }

  private String createGetTrackInfoUrl(String id) {
    final String url = StringSubstitutor.replace(GET_TRACK_INFO_API_TEMPLATE, Map.of("trackID", id), "${", "}");

    return url;
  }

  @Override
  public JSONObject getPlayListInfoById(String id) {
    return null;
  }

  @Override
  public InputStream getTrackInputStreamById(String id) throws IOException, UnsupportedAudioFileException, URISyntaxException {
    final String downloadLink = getTrackUrlById(id);

    System.out.println(downloadLink);

    return getInputStreamByUrl(downloadLink);
  }

  @Override
  public String getTrackUrlById(String id) throws URISyntaxException {
    final JSONObject trackInfo = getTrackInfoById(id);
    final String storageDir = getStorageDir(trackInfo);
    final JSONObject storageInfo = getStoreInfoByDir(storageDir);

    final String host = storageInfo.getString("host");
    final String path = storageInfo.getString("path");
    final String s = storageInfo.getString("s");
    final String ts = storageInfo.getString("ts");

    final String hash = DigestUtils.md5Hex(SECRET_KEY + path.substring(1) + s);

    final String downloadLink = StringSubstitutor.replace(
        TRACK_LINK_TEMPLATE,
        Map.of("host", host, "hashedUrl", hash, "ts", ts, "path", path),
        "${", "}");

    return downloadLink;
  }

  private String getStorageDir(JSONObject trackInfo) {
    return trackInfo.optJSONObject("track").getString("storageDir");
  }

  private JSONObject getStoreInfoByDir(String dir) {
    final HttpRequest request = createGetStorageInfoRequest(dir);

    return GET(request);
  }

  private HttpRequest createGetStorageInfoRequest(String dir) {
    final String url = createGetStoreInfoUrl(dir);

    return HttpRequest.newBuilder().uri(URI.create(url)).build();
  }

  private String createGetStoreInfoUrl(String dir) {
    final String url = StringSubstitutor.replace(GET_STORAGE_INFO_API_TEMPLATE, Map.of("storageDir", dir), "${", "}");

    return url;
  }

  private InputStream getInputStreamByUrl(String url) throws IOException, UnsupportedAudioFileException {
    HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();

    return con.getInputStream();
  }

  private JSONObject GET(HttpRequest request) {
    AtomicReference<JSONObject> json = new AtomicReference<>(null);

    httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
        .thenApply(HttpResponse::body)
        .thenAccept(response -> json.set(new JSONObject(response)))
        .join();

    return json.get();
  }
}
