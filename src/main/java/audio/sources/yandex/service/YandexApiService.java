package audio.sources.yandex.service;

import com.sedmelluq.discord.lavaplayer.tools.JsonBrowser;
import org.json.JSONObject;

public interface YandexApiService {
    JSONObject getTrackInfoById(String id);
    JSONObject getPlayListInfoById(String id);
}
