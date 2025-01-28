package nl.rug.aoop.messagequeue.consumer;

import nl.rug.aoop.messagequeue.message.Message;
import nl.rug.aoop.messagequeue.queues.MessageQueue;

/**
 * Consumer class.
 */
public class Consumer implements MQConsumer {
    /**
     * The queue to consume from.
     */
    private final MessageQueue queue;

    /**
     * Constructor.
     * @param queue the queue to consume from.
     */
    public Consumer(MessageQueue queue) {
        this.queue = queue;
    }

    @Override
    public Message poll() {
        return queue.dequeue();
    }
}
