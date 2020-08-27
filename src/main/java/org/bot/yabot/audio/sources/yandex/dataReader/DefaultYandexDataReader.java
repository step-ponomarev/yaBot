package org.bot.yabot.audio.sources.yandex.dataReader;

import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultYandexDataReader implements YandexDataReader {
  @Override
  public List<String> getAlbumTrackIdList(JSONObject albumInfo) {
    JSONArray tracks = albumInfo.getJSONArray("volumes");

    final List<String> idList = new ArrayList<>();
    for (int i = 0; i < tracks.length(); ++i) {
      idList.add(((JSONObject) tracks.opt(i)).getString("id"));
    }

    System.out.println(idList);

    return idList;
  }

  @Override
  public AudioTrackInfo createTrackInfo(JSONObject rootData) {
    var track = rootData.optJSONObject("track");
    var author = rootData.getJSONArray("artists");

    final List<String> authorNames = new ArrayList<>();
    for (int i = 0; i < author.length(); ++i) {
      authorNames.add(((JSONObject) author.opt(i)).getString("name"));
    }

    final String title = track.getString("title");
    final String authorName = authorNames.stream().collect(Collectors.joining(" & "));
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
