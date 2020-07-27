package bot.command;

import lombok.Getter;

public enum Command {
    SKIP_SONG("!yam.skip", "!yam.skip", 1),
    PLAY_SONG("!yam.play", "!yam.skip <url>", 2);

    @Getter
    private final String command;
    @Getter
    private final String pattern;
    @Getter
    private final int length;

    Command(String command, String pattern, int length) {
        this.command = command;
        this.pattern = pattern;
        this.length = length;
    }

    public boolean isCorrectArgsAmount(String commandStr) {
        return commandStr.split(" ").length == this.length;
    }

    public boolean isCorrectCommand(String commandStr) {
        return commandStr.startsWith(command);
    }
};