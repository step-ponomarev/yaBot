package org.bot.yabot.audio.sources.yandex.dataReader;

import com.sedmelluq.discord.lavaplayer.source.soundcloud.SoundCloudTrackFormat;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultYandexDataReader implements YandexDataReader {
  @Override
  public JSONObject findTrackData(JSONObject rootData) {
    final Map<String, String> trackInfoMap = new HashMap();

    var track = rootData.optJSONObject("track");
    trackInfoMap.put("id", track.getString("id"));
    trackInfoMap.put("title", track.getString("title"));
    trackInfoMap.put("storageDir", track.getString("storageDir"));

    return createJSONObj(trackInfoMap);
  }

  @Override
  public String readTrackId(JSONObject trackData) {
    return null;
  }

  @Override
  public boolean isTrackBlocked(JSONObject trackData) {
    return false;
  }

  @Override
  public AudioTrackInfo readTrackInfo(JSONObject trackData, String identifier) {
    return null;
  }

  @Override
  public List<SoundCloudTrackFormat> readTrackFormats(JSONObject trackData) {
    return null;
  }

  @Override
  public JSONObject findPlaylistData(JSONObject rootData) {
    return null;
  }

  @Override
  public String readPlaylistName(JSONObject playlistData) {
    return null;
  }

  @Override
  public String readPlaylistIdentifier(JSONObject playlistData) {
    return null;
  }

  @Override
  public List<JSONObject> readPlaylistTracks(JSONObject playlistData) {
    return null;
  }

  private JSONObject createJSONObj(Map<String, String> mapInfo) {
    return new JSONObject(mapInfo);
  }
}
