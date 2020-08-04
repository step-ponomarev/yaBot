package audio.sources.yandex.service;

import com.sedmelluq.discord.lavaplayer.tools.JsonBrowser;
import lombok.RequiredArgsConstructor;
import org.apache.commons.text.StringSubstitutor;
import org.json.JSONObject;

import javax.annotation.Nullable;
import java.io.IOException;
import java.net.URI;
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

    AtomicReference<JSONObject> json = new AtomicReference<>(null);
    httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
        .thenApply(HttpResponse::body)
        .thenAccept(response -> json.set(new JSONObject(response)))
        .join();

    return json.get();
  }

  private HttpRequest createGetTrackInfoRequest(String id) {
    final String url = createGetTrackInfoUrl(id);

    return HttpRequest.newBuilder().uri(URI.create(url)).build();
  }

  @Override
  public JSONObject getPlayListInfoById(String id) {
    return null;
  }

  private String createGetTrackInfoUrl(String id) {
    final String url = StringSubstitutor.replace(GET_TRACK_INFO_API_TEMPLATE, Map.of("trackID", id), "${", "}");

    return url;
  }
}
