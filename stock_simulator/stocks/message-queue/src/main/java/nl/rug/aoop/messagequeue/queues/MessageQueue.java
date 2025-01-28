package nl.rug.aoop.messagequeue.queues;

import nl.rug.aoop.messagequeue.message.Message;

/**
 * MessageQueue interface.
 */
public interface MessageQueue {
    /**
     *  Enqueue a message.
     * @param message the message
     */
    void enqueue(Message message);

    /**
     *  Dequeue a message.
     * @return the message.
     */
    Message dequeue();

    /**
     *  Get the size of the queue.
     * @return the size of the queue.
     */
    int getSize();
}
