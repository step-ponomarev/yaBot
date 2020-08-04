package audio.sources.yandex.parser;

import audio.sources.yandex.structures.YandexId;

public interface YandexUrlParser {
  public YandexId defineUrlType(String url);
}
