package nl.rug.aoop.messagequeue.factories;

import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.messagequeue.commands.MQPutCommand;

/**
 * Used to make commands.
 */
public class MessageCommandHandlerFactory implements AbstractCommandHandlerFactory {
    /**
     * Creates a command handler.
     * @return the command handler.
     */
    @Override
    public CommandHandler createCommandHandler() {
        CommandHandler commandHandler = new CommandHandler();
        commandHandler.registerCommand("MqPut", new MQPutCommand());
        return commandHandler;
    }
}
