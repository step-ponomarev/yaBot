import org.bot.yabot.audio.sources.yandex.service.DefaultYandexApiService;
import org.junit.Assert;
import org.junit.Test;

import java.net.http.HttpClient;

public class YandexApiServiceTest {
  private final DefaultYandexApiService yandexApiService;

  private final String mockedTrackUrl = "";
  private final String trackUrl = "https://music.yandex.ru/album/8391900/track/56578959";
  private final String id = "56578959";

  public YandexApiServiceTest() {
    this.yandexApiService = new DefaultYandexApiService(HttpClient.newHttpClient());
  }

  @Test
  public void getTrackUrlByIdTest() {
    final String url = yandexApiService.getTrackUrlById(id);

    System.out.println(url);
    Assert.assertNotNull(url);
  }
}
