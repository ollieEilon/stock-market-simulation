package nl.rug.aoop.messagequeue.producer;

import nl.rug.aoop.messagequeue.message.Message;

/**
 * Interface for a message queue producer.
 */
public interface MQProducer {
    /**
     * Put a message in the queue.
     * @param message the message to put in the queue.
     */
    void put(Message message);
}
