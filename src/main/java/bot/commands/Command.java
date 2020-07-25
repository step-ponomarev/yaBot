package bot.commands;

import lombok.Getter;

public enum Command {
    HELP("@yam.help", "@yam.help", 1),
    SKIP_SONG("@yam.skip", "@yam.skip", 1),
    PLAY_SONG("@yam.play", "@yam.play <url>", 2),
    NOT_COMMAND("", "", 0);

    @Getter
    private final String command;
    @Getter
    private final int length;
    @Getter
    private final String pattern;

    Command(String command, String pattern, int length) {
        this.command = command;
        this.pattern = pattern;
        this.length = length;
    }
}