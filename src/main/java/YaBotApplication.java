import audio.sources.yandex.structures.YandexIdType;
import bot.factory.BotFactory;
import com.sedmelluq.discord.lavaplayer.tools.JsonBrowser;
import config.ApplicationProperties;
import consts.Consts;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public final class YaBotApplication {
  static final ApplicationProperties properties = new ApplicationProperties();

  public static void main(String[] args) {
    try {

//      final String FILE_URL = "https://s244man.storage.yandex.net/get-mp3/b55d5aca056b1adf69a9447922b0868e97aabf4c178cc976f159f70f8c840485/0005ac0f8807ac88/rmusic/U2FsdGVkX19TZx1CzYvEJX1pixtvBcM5ihCsNwTTu5Tg_eSX7xt4cNEYFolkFrPK7Fe8Kg2z2McUlHoDhxDte4UXG0vHzdsrOTi70HR40tE/b55d5aca056b1adf69a9447922b0868e97aabf4c178cc976f159f70f8c840485?track-id=96079&play=false";
//      try (BufferedInputStream in = new BufferedInputStream(new URL(FILE_URL).openStream())) {
//        byte dataBuffer[] = new byte[1024];
//        int bytesRead;
//        while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
//          System.out.println(bytesRead);
//        }
//      } catch (IOException e) {
//        System.err.println("ERROR");
//      }

//      var str = DigestUtils.md5Hex("XGRlBW9FXlekgbPrRHuSiA");
//      System.out.println(str);

      //TODO: Правильно ли так читать проперти? Мб подключить спринг?? Не слишком ли тяжело будет?
      properties.init(getResourceAsStream(Consts.PROPERTIES_FILE_NAME));

      var bot = BotFactory.getInstance(properties.getProperty(Consts.BOT_TOKEN));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static InputStream getResourceAsStream(final String filename) {
    return YaBotApplication.class.getResourceAsStream(filename);
  }
}
