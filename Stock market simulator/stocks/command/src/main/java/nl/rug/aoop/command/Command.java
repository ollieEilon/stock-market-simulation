package nl.rug.aoop.command;

import java.util.Map;

/**
 * Executes a command.
 */
public interface Command {
    /**
     * Used to execute a command.
     * @param params a map
     */
    void execute(Map<String, Object> params);
}