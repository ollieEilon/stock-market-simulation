package nl.rug.aoop.command;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import java.util.HashMap;
import java.util.Map;

/**
 * Handles the commands.
 */
@Slf4j
public class CommandHandler {
    /**
     * The map of commands.
     */
    @Getter
    private final Map<String, Command> commandMap;

    /**
     * Initializes a map of commands.
     */
    public CommandHandler() {
        this.commandMap = new HashMap<>();
    }
    /**
     * Register a new command.
     * @param command the new command.
     * @param commandClass the type of command.
     */

    public void registerCommand(String command, Command commandClass) {
        if (command == null || command.isEmpty()) {
            log.error("Command cannot be null or empty");
            throw new IllegalArgumentException("Command cannot be null or empty");
        }
        if (commandClass == null) {
            log.error("Command class cannot be null");
            throw new IllegalArgumentException("Command class cannot be null");
        }
        this.commandMap.put(command, commandClass);
    }

    /**
     * Executes a command.
     * @param commandName the name of the command.
     * @param params the parameters of the command.
     */
    public void executeCommand(String commandName, Map<String, Object> params) {
        if (commandName == null || commandName.isEmpty()) {
            log.error("Executing command cannot be null or empty");
            throw new IllegalArgumentException("Command cannot be null or empty");
        }
        if (params == null) {
            log.error("Params cannot be null");
            throw new IllegalArgumentException("Params cannot be null");
        }
        Command command = commandMap.get(commandName);
        if (command == null) {
            log.error("Command {} not found", commandName);
            throw new IllegalArgumentException("Command " + commandName + " not found");
        }
        command.execute(params);
    }
}