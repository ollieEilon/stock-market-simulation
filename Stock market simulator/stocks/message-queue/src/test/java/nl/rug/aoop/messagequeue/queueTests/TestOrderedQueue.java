package nl.rug.aoop.messagequeue.queueTests;

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
 *  Test class for the OrderedQueue class.
 */
public class TestOrderedQueue {
    /**
     * The queue to test.
     */
    private MessageQueue queue;

    /**
     * Set up the test environment.
     */
    @BeforeEach
    void setUp() {
        queue = new OrderedQueue();
    }

    /**
     * Test the constructor of the OrderedQueue class.
     */
    @Test
    void testQueueConstructor() {
        assertNotNull(queue);
    }

    /**
     *  Test the methods of the OrderedQueue class.
     */
    @Test
    void testQueueMethods() {
        List<String> names = new ArrayList<>();
        List<Method> methods = List.of(queue.getClass().getDeclaredMethods());
        for (Method method : methods) {
            names.add(method.getName());
        }
        assertTrue(names.contains("enqueue"));
        assertTrue(names.contains("dequeue"));
        assertTrue(names.contains("getSize"));
    }

    /**
     *  Test the enqueue and dequeue methods of the OrderedQueue class.
     */
    @Test
    void testQueueEnqueue() {
        Message message1 = new Message("header", "body1");
        Message message2 = new Message("header", "body2");
        Message message3 = new Message("header", "body3");

        queue.enqueue(message2);
        queue.enqueue(message1);
        queue.enqueue(message3);

        assertEquals(message1, queue.dequeue());
        assertEquals(message2, queue.dequeue());
        assertEquals(message3, queue.dequeue());
    }
    /**
     * Test the dequeue method of the OrderedQueue class.
     */
    @Test
    void testGetSize() {
        Message message1 = new Message("header", "body");
        Message message2 = new Message("header", "body");
        Message message3 = new Message("header", "body");

        queue.enqueue(message3);
        assertEquals(1, queue.getSize());
        queue.enqueue(message1);
        assertEquals(2, queue.getSize());
        queue.enqueue(message2);
        assertEquals(3, queue.getSize());
    }

    /**
     * Test the dequeue method when the queue is empty.
     */
    @Test
    void testDequeueEmpty() {assertNull(queue.dequeue());}

    /**
     * Test the enqueue a null message.
     */
    @Test
    void testEnqueueNull() {
        assertThrows(IllegalArgumentException.class, () -> queue.enqueue(null));
        assertEquals(0, queue.getSize());
    }

    /**
     * Test that the putting and polling work for the same message.
     */
    @Test
    void pushSameMessage() {
        Message message = new Message("header", "body");
        for (int i = 0; i < 10; i++) {
            queue.enqueue(message);
        }
        for (int i = 0; i < 10; i++) {
            assertEquals(message, queue.dequeue());
        }
    }
}

