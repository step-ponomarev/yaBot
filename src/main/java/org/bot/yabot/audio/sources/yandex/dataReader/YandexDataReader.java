package org.bot.yabot.audio.sources.yandex.dataReader;

import com.sedmelluq.discord.lavaplayer.source.soundcloud.SoundCloudTrackFormat;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import org.json.JSONObject;

import java.util.List;

// TODO: Реализовать getAudioTrackInfo
public interface YandexDataReader {
    JSONObject findTrackData(JSONObject rootData);

    String readTrackId(JSONObject trackData);

    boolean isTrackBlocked(JSONObject trackData);

    AudioTrackInfo readTrackInfo(JSONObject trackData, String identifier);

    List<SoundCloudTrackFormat> readTrackFormats(JSONObject trackData);

    JSONObject findPlaylistData(JSONObject rootData);

    String readPlaylistName(JSONObject playlistData);

    String readPlaylistIdentifier(JSONObject playlistData);

    List<JSONObject> readPlaylistTracks(JSONObject playlistData);
}
