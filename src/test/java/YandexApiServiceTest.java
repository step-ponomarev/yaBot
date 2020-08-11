import org.bot.yabot.audio.sources.yandex.service.DefaultYandexApiService;
import org.junit.Assert;
import org.junit.Test;

import java.net.http.HttpClient;

public class YandexApiServiceTest {
  private final DefaultYandexApiService yandexApiService;

  private final String mockedTrackUrl = "https://s13vla.storage.yandex.net/get-mp3/4e8b16960fd3116b61757ccc52f1806a/0005ac9fca0b7f37/rmusic/U2FsdGVkX19jCkNXy600ltmIXNF0cmzLFQsMswG6sbXhaRODNvd3szkmVwzNb9RSrMy2D_kEnX5ZWvI6eIRUmBLrNdd8rJeTRHUw4skY1og/d6909c7e9152be081cf0d7ea7751e9a86c98a09eaf30f9fe0b9fe03d09a59c18";
  private final String trackUrl = "https://music.yandex.ru/album/8391900/track/56578959";
  private final String id = "56578959";

  public YandexApiServiceTest() {
    this.yandexApiService = new DefaultYandexApiService(HttpClient.newHttpClient());
  }

  @Test
  public void getTrackUrlByIdTest() {
    final String url = yandexApiService.getTrackUrlById(id);

    Assert.assertNotNull(url);
  }
}
