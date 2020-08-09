package org.bot.yabot;

import io.github.cdimascio.dotenv.Dotenv;
import org.bot.yabot.bot.factory.BotFactory;

import org.bot.yabot.consts.Consts;

public final class YaBotApplication {
  static final Dotenv dotenv = Dotenv.load();

  public static void main(String[] args) {
    try {
      var bot = BotFactory.getInstance(dotenv.get(Consts.BOT_TOKEN));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

