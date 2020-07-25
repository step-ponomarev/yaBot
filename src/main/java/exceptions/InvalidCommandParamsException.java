package exceptions;

import bot.commands.Command;
import lombok.Getter;

public class InvalidCommandParamsException extends RuntimeException {
    @Getter
    private Command command;

    public InvalidCommandParamsException(Command command) {
        this.command = command;
    }

    public InvalidCommandParamsException(Command command, String message) {
        super(message);
        this.command = command;
    }
}
