package nl.rug.aoop.messagequeue;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.messagequeue.message.Message;
import nl.rug.aoop.messagequeue.queues.MessageQueue;
import nl.rug.aoop.networking.MessageHandler;
import java.util.HashMap;
import java.util.Map;

/**
 * Changes JSON string to message and passes it over to the command handler.
 */
@Slf4j
public class CommandMessageHandler implements MessageHandler {
    /**
     * The message system.
     */
    @Getter

    private final Map<String, Object> messageSystem;

    /**
     * The command handler.
     */
    private final CommandHandler commandHandler;

    /**
     * Constructor.
     * @param commandHandler the command handler.
     * @param messageQueue  the message queue.
     */
    public CommandMessageHandler(CommandHandler commandHandler, MessageQueue messageQueue) {
        if (commandHandler == null || messageQueue == null) {
            log.error("CommandMessageHandler constructor called with null argument(s)");
            throw new NullPointerException();
        }
        this.commandHandler = commandHandler;
        messageSystem = new HashMap<>();
        messageSystem.put("messageQueue", messageQueue);
    }

    /**
     * Create an object our of a JSON string.
     * @param message the JSON string.
     */
    @Override
    public void handleMessage(String message) {
        if (message == null) {
            log.error("CommandMessageHandler handleMessage called with null argument");
            throw new NullPointerException();
        }
        Message message1 = Message.fromJSON(message);
        Message message2 = Message.fromJSON(message1.getBody());
        messageSystem.put("message", message2);
        commandHandler.executeCommand(message1.getHeader(), messageSystem);
    }
}
