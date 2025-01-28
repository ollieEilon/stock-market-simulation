package nl.rug.aoop.messagequeue.queues;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.messagequeue.message.Message;

import java.util.concurrent.PriorityBlockingQueue;

/**
 * The network queue class.
 */
@Slf4j
public class NetworkQueue implements MessageQueue{

    /**
     * The queue.
     */
    private final PriorityBlockingQueue<Message> queue;

    /**
     * Constructor.
     */
    public NetworkQueue() {
        this.queue = new PriorityBlockingQueue<>();
    }

    @Override
    public void enqueue(Message message) {
        if (message == null ) {
            log.error("message cannot be null");
            throw new IllegalArgumentException("message cannot be null");
        }
        queue.add(message);
    }

    @Override
    public Message dequeue() {
        return queue.poll();
    }

    /**
     * Method to get the size of the queue.
     * @return the size of the queue.
     */
    public int getSize() {
        return queue.size();
    }
}
