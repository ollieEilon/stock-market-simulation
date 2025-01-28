package nl.rug.aoop.messagequeue.queueTests;

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
 * Test class for the UnorderedQueue class.
 */
public class TestUnorderedQueue {
    /**
     * The queue to test.
     */
    private MessageQueue queue;

    /**
     * Set up the test environment.
     */
    @BeforeEach
    void setUp() {
        queue = new UnorderedQueue();
    }

    /**
     * Test the constructor of the UnorderedQueue class.
     */
    @Test
    void testQueueConstructor() {
        assertNotNull(queue);
        assertEquals(0, queue.getSize());
    }

    /**
     * Test the methods of the UnorderedQueue class.
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
     * Test the dequeue method of the UnorderedQueue class with no values.
     */
    @Test
    void testDequeueEmpty() {
        assertNull(queue.dequeue());
    }

    /**
     * Test the enqueue method of the UnorderedQueue class.
     */
    @Test
    void testGetSize() {
        Message message1 = new Message("header", "body");
        Message message2 = new Message("header", "body");
        Message message3 = new Message("header", "body");

        assertEquals(0, queue.getSize());

        queue.enqueue(message3);
        queue.enqueue(message1);
        queue.enqueue(message2);

        assertEquals(3, queue.getSize());
        queue.dequeue();
        assertEquals(2, queue.getSize());
        queue.dequeue();
        assertEquals(1, queue.getSize());
        queue.dequeue();
        assertEquals(0, queue.getSize());
    }

    /**
     * Test the enqueue method of the UnorderedQueue class.
     */
    @Test
    void testQueueOrdering() {
        Message message1 = new Message("header", "body");
        Message message2 = new Message("header", "body");
        Message message3 = new Message("header", "body");

        queue.enqueue(message3);
        queue.enqueue(message1);
        queue.enqueue(message2);

        assertEquals(message3, queue.dequeue());
        assertEquals(message1, queue.dequeue());
        assertEquals(message2, queue.dequeue());
    }

    /**
     * Test tries to push a null message into the queue.
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
