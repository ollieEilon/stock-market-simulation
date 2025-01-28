package nl.rug.aoop.messagequeue.consumer;

import nl.rug.aoop.messagequeue.message.Message;

/**
 * Interface for a message queue consumer.
 */
public interface MQConsumer {
    /**
     * Poll a message from the queue.
     * @return the message polled from the queue.
     */
    Message poll();
}
