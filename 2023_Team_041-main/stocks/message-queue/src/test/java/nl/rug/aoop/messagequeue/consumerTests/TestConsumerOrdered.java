package nl.rug.aoop.messagequeue.consumerTests;

import nl.rug.aoop.messagequeue.consumer.Consumer;
import nl.rug.aoop.messagequeue.message.Message;
import nl.rug.aoop.messagequeue.queues.MessageQueue;
import nl.rug.aoop.messagequeue.queues.OrderedQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the Consumer class.
 */
public class TestConsumerOrdered {
    /**
     * The queue to test.
     */
    private MessageQueue queue;
    /**
     * The consumer to test.
     */
    private Consumer consumer;

    /**
     * Set up the test environment.
     */
    @BeforeEach
    void setUp() {
        queue = new OrderedQueue();
        consumer = new Consumer(queue);
    }

    /**
     * Test from the Consumer class's constructor Ordered.
     */
    @Test
    void testConsumerConstructorOrdered() {
        assertNotNull(consumer);
    }

    /**
     * Test from the Consumer class's methods ordered queue.
     */
    @Test
    void testConsumerMethodsOrdered() {
        List<String> names = new ArrayList<>();
        List<Method> methods = List.of(consumer.getClass().getDeclaredMethods());
        for (Method method : methods) {
            names.add(method.getName());
        }
        assertTrue(names.contains("poll"));
    }

    /**
     * Test from the Consumer class's poll method when null ordered.
     */
    @Test
    void testConsumerPollWhenNullOrdered() {
        assertNull(consumer.poll());
    }

    /**
     * Test from the Consumer class's poll method ordered.
     */
    @Test
    void testConsumerPollOrdered() {
        Message message = new Message("header", "body");
        queue.enqueue(message);
        assertEquals(message, consumer.poll());
    }
}
