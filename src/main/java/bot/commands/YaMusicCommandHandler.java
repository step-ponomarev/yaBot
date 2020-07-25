package bot.commands;

import exceptions.InvalidCommandParamsException;

import java.util.Arrays;

import static bot.commands.Command.*;

public class YaMusicCommandHandler implements ICommandHandler {
    @Override
    public Command handleCommand(String command) {
        final var splitedCommand = command.split(" ");
        final var cmd = splitedCommand[0];

        final var commandEnum = Arrays.stream(Command.values())
                .filter(c -> c.getCommand().equals(cmd))
                .findAny()
                .orElse(null);

        if (commandEnum == null) {
            return NOT_COMMAND;
        }

        if (commandEnum.getLength() != splitedCommand.length) {
            throw new InvalidCommandParamsException(commandEnum);
        }

        return commandEnum;
    }
}
