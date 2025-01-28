package nl.rug.aoop.messagequeue.consumerTests;

import nl.rug.aoop.messagequeue.consumer.Consumer;
import nl.rug.aoop.messagequeue.message.Message;
import nl.rug.aoop.messagequeue.queues.MessageQueue;
import nl.rug.aoop.messagequeue.queues.UnorderedQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 *  Test class for the Consumer class with an ordered queue.
 */
public class TestConsumerUnordered {
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
        queue = new UnorderedQueue();
        consumer = new Consumer(queue);
    }

    /**
     * Test from the Consumer class's constructor Unordered.
     */
    @Test
    void testConsumerConstructorUnordered() {
        assertNotNull(consumer);
    }

    /**
     * Test from the Consumer class's methods unordered queue.
     */
    @Test
    void testConsumerMethodsUnordered() {
        List<String> names = new ArrayList<>();
        List<Method> methods = List.of(consumer.getClass().getDeclaredMethods());
        for (Method method : methods) {
            names.add(method.getName());
        }
        assertTrue(names.contains("poll"));
    }

    /**
     * Test from the Consumer class's poll method when null unordered.
     */
    @Test
    void testConsumerPollWhenNullUnordered() {
        assertNull(consumer.poll());
    }

    /**
     * Test from the Consumer class's poll method unordered.
     */
    @Test
    void testConsumerPollUnordered() {
        Message message = new Message("header", "body");
        queue.enqueue(message);
        assertEquals(message, consumer.poll());
    }
}
