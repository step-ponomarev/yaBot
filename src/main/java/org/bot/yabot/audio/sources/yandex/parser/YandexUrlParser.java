package org.bot.yabot.audio.sources.yandex.parser;

import org.bot.yabot.audio.sources.yandex.structures.YandexId;

public interface YandexUrlParser {
  public YandexId defineUrlType(String url);
}
