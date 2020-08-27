package org.bot.yabot.audio.sources.yandex.service;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

public interface YandexApiService {
  JSONObject getTrackInfoById(String id);

  JSONObject getPlayListInfoById(String id);

  JSONObject getAlbumInfoById(String id);

  InputStream getTrackInputStreamById(String id) throws IOException;

  String getTrackUrlById(String id) throws URISyntaxException;
}
