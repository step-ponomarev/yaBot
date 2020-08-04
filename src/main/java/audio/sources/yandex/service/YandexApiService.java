package audio.sources.yandex.service;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public interface YandexApiService {
    JSONObject getTrackInfoById(String id);
    JSONObject getPlayListInfoById(String id);
    InputStream getTrackInputStreamById(String id) throws IOException;
}
