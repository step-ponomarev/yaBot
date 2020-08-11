package org.bot.yabot.audio.sources.yandex.dataReader;

import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.stream.Collectors;

public class DefaultYandexDataReader implements YandexDataReader {

  @Override
  public AudioTrackInfo createTrackInfo(JSONObject rootData) {
    var track = rootData.optJSONObject("track");
    var author = Arrays.asList(rootData.optJSONObject("artists"));


    final String title = track.getString("title");
    final String authorName = author.size() != 1 ?
        author.stream()
            .map(athor -> athor.getString("name"))
            .collect(Collectors.joining(" & ")) :
        author.get(0).getString("name");
    final long duration = track.getLong("durationMs");

    return new AudioTrackInfo(
        title,
        authorName,
        duration,
        "yandex",
        false,
        null
    );
  }
}
