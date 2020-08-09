import org.bot.yabot.audio.sources.yandex.parser.DefaultYandexUrlParser;
import org.bot.yabot.audio.sources.yandex.parser.YandexUrlParser;
import org.bot.yabot.audio.sources.yandex.structures.YandexId;
import org.bot.yabot.audio.sources.yandex.structures.YandexIdType;
import org.junit.Assert;
import org.junit.Test;

public class YandexUrlParserTestClass {
  private final YandexUrlParser yandexUrlParser;

  private final String trackUrl = "https://music.yandex.ru/album/8391900/track/56578959";
  private final String trackId = "56578959";

  private final String albumUrl = "https://music.yandex.ru/album/11330121";
  private final String albumId = "11330121";

  private final String playListUrl = "https://music.yandex.ru/users/yamusic-daily/playlists/114683554";
  private final String playListId = "114683554";

  public YandexUrlParserTestClass() {
    yandexUrlParser = new DefaultYandexUrlParser();
  }

  @Test
  public void trackUrlParsingTest() {
    YandexId trackId = yandexUrlParser.defineUrlType(trackUrl);

    Assert.assertNotNull(trackId);
    Assert.assertEquals(trackId.id, this.trackId);
    Assert.assertEquals(trackId.type, YandexIdType.TRACK);
  }

  @Test
  public void albumUrlParsingTest() {
    YandexId albumId = yandexUrlParser.defineUrlType(albumUrl);

    Assert.assertNotNull(albumId);
    Assert.assertEquals(albumId.id, this.albumId);
    Assert.assertEquals(albumId.type, YandexIdType.ALBUM);
  }

  @Test
  public void playListUrlParsingTest() {
    YandexId playListId = yandexUrlParser.defineUrlType(playListUrl);

    Assert.assertNotNull(playListId);
    Assert.assertEquals(playListId.id, this.playListId);
    Assert.assertEquals(playListId.type, YandexIdType.PLAYLIST);
  }
}
