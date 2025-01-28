package nl.rug.aoop.messagequeue.queues;

import nl.rug.aoop.messagequeue.message.Message;

import java.time.LocalDateTime;
import java.util.*;

/**
 *  Ordered queue based on time stamp.
 */
public class OrderedQueue implements MessageQueue {
    /**
     * the sorted map.
     */
    private final SortedMap<LocalDateTime, List<Message>> queue;

    /**
     * Constructor LocalDateTime.
     */
    public OrderedQueue() {
        this.queue = new TreeMap<>();
    }

    /**
     * Adds a message to the queue.
     * @param message the message.
     */
    @Override
    public void enqueue(Message message) {
        if (message == null) {
            throw new IllegalArgumentException("message cannot be null");
        }
        if(!queue.containsKey(message.getTimestamp())) {
            List<Message> set = new ArrayList<>();
            queue.put(message.getTimestamp(), set);
        }
        queue.get(message.getTimestamp()).add(message);
    }

    /**
     * removes the first message in the queue.
     * @return the first message in the queue.
     */
    @Override
    public Message dequeue() {
        if (queue.isEmpty()) {
            return null;
        }
        int size = queue.get(queue.firstKey()).size();
        if (size > 1) {
            return queue.get(queue.firstKey()).remove(0);
        } else {
            return queue.remove(queue.firstKey()).remove(0);
        }
    }

    /**
     * returns the size of the queue.
     * @return the size of the queue.
     */
    @Override
    public int getSize() {
        int size=0;
        for (Map.Entry<LocalDateTime, List<Message>> entry : queue.entrySet()) {
            size+=entry.getValue().size();
        }
        return size;
    }
}
