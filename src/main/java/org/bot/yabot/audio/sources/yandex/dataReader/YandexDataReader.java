package org.bot.yabot.audio.sources.yandex.dataReader;

import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import org.json.JSONObject;

public interface YandexDataReader {
    AudioTrackInfo createTrackInfo(JSONObject rootData);
}
