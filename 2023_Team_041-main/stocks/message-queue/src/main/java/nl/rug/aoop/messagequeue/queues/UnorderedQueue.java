package nl.rug.aoop.messagequeue.queues;

import nl.rug.aoop.messagequeue.message.Message;

import java.util.LinkedList;
import java.util.Queue;

/**
 * A message queue that does not guarantee any order.
 */
public class UnorderedQueue implements MessageQueue {
    /**
     * The queue that holds the messages.
     */
    private final Queue<Message> queue;

    /**
     * Constructor.
     */
    public UnorderedQueue() {
        this.queue = new LinkedList<>();
    }


    /**
     * Adds a message to the queue.
     * @param message the message to add.
     */
    @Override
    public void enqueue(Message message) { 
        if (message == null) {
            throw new IllegalArgumentException("message cannot be null");
        }
        queue.add(message);
    }

    /**
     * removes the first message in the queue.
     * @return the first message in the queue.
     */
    @Override
    public Message dequeue() {
        return queue.poll();
    }

    @Override
    public int getSize() {
        return queue.size();
    }
}
