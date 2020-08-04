package audio.sources.yandex.parser;

import audio.sources.yandex.structures.YandexId;
import audio.sources.yandex.structures.YandexIdType;

import java.util.regex.Pattern;

public class DefaultYandexUrlParser implements YandexUrlParser {
  private final String YANDEX_URL = "https://music.yandex.ru";
  private final String TRACK_URL_REGEXP = "^(https://music.yandex.ru/album/)[0-9]+/track/[0-9]+$";
  private final String ALBUM_URL_REGEXP = "^(https://music.yandex.ru/album/)[0-9]+$";
  private final String PLAYLIST_URL_REGEXP = "^(https://music.yandex.ru/users/)[\\S&&[^.,]]+/playlists/[0-9]+$";

  private final Pattern TRACK_URL_PATTERN = Pattern.compile(TRACK_URL_REGEXP);
  private final Pattern ALBUM_URL_PATTERN = Pattern.compile(ALBUM_URL_REGEXP);
  private final Pattern PLAYLIST_URL_PATTERN = Pattern.compile(PLAYLIST_URL_REGEXP);

  @Override
  public YandexId defineUrlType(String url) {
    if (!url.startsWith(YANDEX_URL)) {
      return null;
    }

    final boolean IS_TRACK_URL = TRACK_URL_PATTERN.matcher(url).find();
    final boolean IS_ALBUM_URL = ALBUM_URL_PATTERN.matcher(url).find();
    final boolean IS_PLAYLIST_URL = PLAYLIST_URL_PATTERN.matcher(url).find();

    final String id = getIdFromUrl(url);

    YandexId yandexId = null;
    if (IS_TRACK_URL) {
      yandexId = new YandexId(id, YandexIdType.TRACK);
    }

    if (IS_ALBUM_URL) {
      yandexId = new YandexId(id, YandexIdType.ALBUM);
    }

    if (IS_PLAYLIST_URL) {
      yandexId = new YandexId(id, YandexIdType.PLAYLIST);
    }

    return yandexId;
  }

  private String getIdFromUrl(String url) {
    return url.substring(url.lastIndexOf("/") + 1);
  }
}
