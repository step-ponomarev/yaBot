package org.bot.yabot.audio.sources.yandex.dataReader;

import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import org.json.JSONObject;

import java.util.stream.Collectors;

public class DefaultYandexDataReader implements YandexDataReader {

  @Override
  public AudioTrackInfo createTrackInfo(JSONObject rootData) {
    var track = rootData.optJSONObject("track");
    var author = rootData.getJSONArray("artists").toList();

    final String title = track.getString("title");
    final String authorName = author.size() != 1 ?
        author.stream()
            .map(athor -> ((JSONObject) athor).getString("name"))
            .collect(Collectors.joining(" & ")) :
        ((JSONObject) author).getString("name");
    final long duration = track.getLong("durationMs");

    System.out.println(authorName);

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
