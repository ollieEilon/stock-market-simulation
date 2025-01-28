package nl.rug.aoop.messagequeue.factories;

import nl.rug.aoop.command.CommandHandler;

/**
 * Used to make commands.
 */
public interface AbstractCommandHandlerFactory {
    /**
     * Creates a command handler.
     * @return the command handler.
     */
    CommandHandler createCommandHandler();
}
