package nl.rug.aoop.messagequeue.queueTests;

import nl.rug.aoop.messagequeue.message.Message;
import nl.rug.aoop.messagequeue.queues.NetworkQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the NetworkQueue class.
 */
public class TestNetworkQueue {
    /**
     * The NetworkQueue object to be tested.
     */
    NetworkQueue networkQueue;
    /**
     * The Message object to be used in the tests.
     */
    Message message;

    /**
     *  Set up the NetworkQueue object and the Message object.
     */
    @BeforeEach
    void setUp() {
        networkQueue = new NetworkQueue();
        this.message = new Message("header", "body");
    }

    /**
     * Test the constructor of the NetworkQueue class.
     */
    @Test
    void testNetworkQueueConstructor() {
        assertNotNull(networkQueue);
    }


    /**
     * Test the add method of the NetworkQueue class.
     */
    @Test
    void testNetworkQueueAdd() {
        Message message = new Message("header", "body");
        networkQueue.enqueue(message);
        assertEquals(1, networkQueue.getSize());
    }

    /**
     * Test the poll method of the NetworkQueue class.
     */
    @Test
    void testNetworkQueuePoll() {
        Message message = new Message("header", "body");
        networkQueue.enqueue(message);
        assertEquals(message, networkQueue.dequeue());
    }

    /**
     * Test the getSize method of the NetworkQueue class.
     */
    @Test
    void testNetworkQueueGetSize() {
        NetworkQueue networkQueue = new NetworkQueue();
        Message message = new Message("header", "body");
        networkQueue.enqueue(message);
        assertEquals(1, networkQueue.getSize());
    }

    /**
     * Test the methods of the NetworkQueue class.
     */
    @Test
    void testNetworkQueueMethods() {
        List<String> names = new ArrayList<>();
        List<Method> methods = List.of(networkQueue.getClass().getDeclaredMethods());
        for (Method method : methods) {
            names.add(method.getName());
        }
        assertTrue(names.contains("enqueue"));
        assertTrue(names.contains("dequeue"));
        assertTrue(names.contains("getSize"));
    }

    /**
     * Test the ordering of messages;
     */
    @Test
    void testOrder() throws InterruptedException {
        Message message1 = new Message("1", "body");
        TimeUnit.MILLISECONDS.sleep(1);
        Message message2 = new Message("2", "body");
        TimeUnit.MILLISECONDS.sleep(1);
        Message message3 = new Message("3", "body");
        TimeUnit.MILLISECONDS.sleep(1);
        Message message4 = new Message("4", "body");
        TimeUnit.MILLISECONDS.sleep(1);
        Message message5 = new Message("5", "body");

        networkQueue.enqueue(message4);
        networkQueue.enqueue(message2);
        networkQueue.enqueue(message3);
        networkQueue.enqueue(message5);
        networkQueue.enqueue(message1);

        assertEquals(message1, networkQueue.dequeue());
        assertEquals(message2, networkQueue.dequeue());
        assertEquals(message3, networkQueue.dequeue());
        assertEquals(message4, networkQueue.dequeue());
        assertEquals(message5, networkQueue.dequeue());
    }
}
