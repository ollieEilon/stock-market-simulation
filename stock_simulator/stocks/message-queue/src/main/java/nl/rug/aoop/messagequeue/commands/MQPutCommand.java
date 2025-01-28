package nl.rug.aoop.messagequeue.commands;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.command.Command;
import nl.rug.aoop.messagequeue.message.Message;
import nl.rug.aoop.messagequeue.queues.MessageQueue;

import java.util.Map;

/**
 * Changes JSON string to message and passes it over to the command handler.
 */
@Slf4j
public class MQPutCommand implements Command {
    @Override
    public void execute(Map<String, Object> params) {
        final String QUEUE_KEY = "queue";

        MessageQueue messageQueue = retrieveMessageQueue(params);
        Message message = retrieveMessage(params);

        messageQueue.enqueue(message);

        params.put(QUEUE_KEY, messageQueue);
    }

    /**
     * Retrieves a MessageQueue from the params map.
     *
     * @param params the map of parameters.
     * @return the MessageQueue.
     */
    private MessageQueue retrieveMessageQueue(Map<String, Object> params) {
        Object object = params.get("queue");

        if (object instanceof MessageQueue) {
            params.remove("queue"); // Remove the object from the map if found
            log.info("MessageQueue found for key: queue");
            return (MessageQueue) object;
        } else {
            log.error("Expected MessageQueue for key: queue");
            throw new IllegalArgumentException("Expected MessageQueue for key: queue");
        }
    }

    /**
     * Retrieves a message from a map.
     * @param params the map of parameters.
     * @return the Message.
     */
    private Message retrieveMessage(Map<String, Object> params) {
        Object object = params.get("message");

        if (object instanceof Message) {
            params.remove("message"); // Remove the object from the map if found
            log.info("Message found for key: message");
            return (Message) object;
        } else {
            log.error("Expected Message for key: message");
            throw new IllegalArgumentException("Expected Message for key: message");
        }
    }
}
