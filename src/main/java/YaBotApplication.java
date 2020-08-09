import bot.factory.BotFactory;
import config.ApplicationProperties;
import consts.Consts;

import java.io.InputStream;


public final class YaBotApplication {
  static final ApplicationProperties properties = new ApplicationProperties();


  public static void main(String[] args) {
    try {
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

