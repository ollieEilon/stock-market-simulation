package nl.rug.aoop.messagequeue.producerTests;
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
 * Test class for the OrderedQueue class.
 */
public class TestProducerUnordered {
//    /**
//     * The queue to test.
//     */
//    private MessageQueue queue;
//
//
//    /**
//     * Set up the test environment.
//     */
////    @BeforeEach
////    void setUp() {
////        queue = new UnorderedQueue();
////        producer = new Producer(queue);
////    }
//
//    /**
//     * Test the constructor of the Producer class with an unordered queue.
//     */
//    @Test
//    void testProducerConstructorUnordered() {
//        assertNotNull(producer);
//    }
//
//    /**
//     * Test from the Producer class's methods unordered queue.
//     */
//    @Test
//    void testProducerMethodsUnordered() {
//        Producer producer = new Producer(new UnorderedQueue());
//        List<String> names = new ArrayList<>();
//        List<Method> methods = List.of(producer.getClass().getDeclaredMethods());
//        for (Method method : methods) {
//            names.add(method.getName());
//        }
//        assertTrue(names.contains("put"));
//    }
//
//    /**
//     * Test the put method of the Producer class with an unordered queue.
//     */
//    @Test
//    void testProducerPutUnordered() {
//        Message message = new Message("header", "body");
//        producer.put(message);
//        assertEquals(1, queue.getSize());
//    }
//
//    /**
//     * Test the put method of the Producer class with an unordered queue using dequeue.
//     */
//    @Test
//    void testProducerPutUnorderedDequeue() {
//        Message message = new Message("header", "body");
//        producer.put(message);
//        assertEquals(message, queue.dequeue());
//    }
//
//    /**
//     * Test that the putting and polling work for the same message.
//     */
//    @Test
//    void pushSameMessage() {
//        Message message = new Message("header", "body");
//        for (int i = 0; i < 10; i++) {
//            producer.put(message);
//        }
//        for (int i = 0; i < 10; i++) {
//            assertEquals(message, queue.dequeue());
//        }
//    }
//
//    /**
//     * Test for the message is empty.
//     */
//    @Test
//    void pushEmptyMessage() {
//        assertThrows(IllegalArgumentException.class, () -> producer.put(null));
//    }
}
