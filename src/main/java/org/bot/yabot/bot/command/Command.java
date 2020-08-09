package org.bot.yabot.bot.command;

import lombok.Getter;

public enum Command {
  SKIP_SONG("!yam.skip", "!yam.skip", "Skips current track", 1),
  PLAY_SONG("!yam.play", "!yam.play <url>", "Plays track by url from YouTube, SoundCloud and Yandex.Music", 2),
  PAUSE_SONG("!yam.pause", "!yam.pause", "Pauses current track", 1),
  RESUME_SONG("!yam.resume", "!yam.resume", "Resumes paused track", 1),
  HELP("!yam.help", "!yam.help", "Shows command list", 1);

  @Getter
  private final String command;
  @Getter
  private final String pattern;
  @Getter
  private final String discription;
  @Getter
  private final int length;

  Command(String command, String pattern, String description, int length) {
    this.command = command;
    this.pattern = pattern;
    this.discription = description;
    this.length = length;
  }

  public boolean isCorrectArgsAmount(String commandStr) {
    return commandStr.split(" ").length == this.length;
  }

  public boolean isCorrectCommand(String commandStr) {
    return commandStr.startsWith(command);
  }
};