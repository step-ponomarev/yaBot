package org.bot.yabot;

import io.github.cdimascio.dotenv.Dotenv;
import org.bot.yabot.bot.factory.BotFactory;

import org.bot.yabot.consts.Consts;

public final class YaBotApplication {
  static final Dotenv dotenv = Dotenv.load();
  private static final boolean dev = false;

  public static void main(String[] args) {
    try {
      var TOKEN = dev ?
          dotenv.get(Consts.BOT_TOKEN)
          : System.getenv(Consts.BOT_TOKEN);

      var bot = BotFactory.getInstance(TOKEN);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

