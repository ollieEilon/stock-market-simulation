package nl.rug.aoop.messagequeue.producerTests;

import nl.rug.aoop.messagequeue.message.Message;
import nl.rug.aoop.messagequeue.producer.NetworkProducer;
import nl.rug.aoop.messagequeue.queues.MessageQueue;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestProducerOrdered {
    /**
     * The queue to test.
     */
    private MessageQueue queue;
    /**
     * The producer to test.
     */
    private NetworkProducer producer;
//    /**
//     * Set up the test environment.
//     */
//    @BeforeEach
//    void setUp() throws IOException {
//        Client client = new Client();
//        queue = new OrderedQueue();
//        producer = new NetworkProducer(queue);
//    }

    /**
     * Test the constructor of the Producer class with an ordered queue.
     */
    @Disabled
    void testProducerConstructorOrdered() {
        assertNotNull(producer);
    }

    /**
     * Test from the Producer class's methods ordered queue.
     */
    @Disabled
    void testProducerMethodsOrdered() {
        List<String> names = new ArrayList<>();
        List<Method> methods = List.of(producer.getClass().getDeclaredMethods());
        for (Method method : methods) {
            names.add(method.getName());
        }
        assertTrue(names.contains("put"));
    }

    /**
     * Test the put method of the Producer class with an ordered queue using the size.
     */
    @Disabled
    void testProducerPutOrdered() {
        Message message = new Message("header", "body");
        producer.put(message);
        assertEquals(1, queue.getSize());
    }

    /**
     * Test the put method of the Producer class with an ordered queue using dequeue.
     */
    @Disabled
    void testProducerPutOrderedDequeue() {
        Message message = new Message("header", "body");
        producer.put(message);
        assertEquals(message, queue.dequeue());
    }

    /**
     * Test that the putting and polling work for the same message.
     */
    @Disabled
    void pushSameMessage() {
        Message message = new Message("header", "body");
        for (int i = 0; i < 10; i++) {
            producer.put(message);
        }
        for (int i = 0; i < 10; i++) {
            assertEquals(message, queue.dequeue());
        }
    }

    /**
     * Test for the message is empty.
     */
    @Disabled
    void pushEmptyMessage() {
        assertThrows(IllegalArgumentException.class, () -> producer.put(null));
    }
}