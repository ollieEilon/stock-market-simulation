package nl.rug.aoop.messagequeue.producerTests;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.messagequeue.message.Message;
import nl.rug.aoop.messagequeue.message.NetworkMessage;
import nl.rug.aoop.messagequeue.producer.NetworkProducer;
import nl.rug.aoop.networking.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for the NetworkProducer class.
 */
@Slf4j
public class TestNetworkProducer {
    /**
     * The client used for tests.
     */
    private Client mockClient;
    /**
     * The network producer used for tests.
     */
    private NetworkProducer networkProducer;
    /**
     * The message used for tests.
     */
    Message message;
    /**
     * Set up the test environment.
     */
    @BeforeEach

    void setUp(){
        mockClient = Mockito.mock(Client.class);
        networkProducer = new NetworkProducer(mockClient);
        message = new Message("Test Header", "Test Body"); // Initialize the instance field
    }


    /**
     * Test the constructor of the NetworkProducer class.
     */
    @Test
    void testConstructor() {
        assertNotNull(networkProducer);
    }

    /**
     * Test the constructor with a null client.
     */
    @Test
    void testConstructorNullClient() {
        assertThrows(NullPointerException.class, () -> new NetworkProducer(null));
    }

    /**
     * Test the methods of the NetworkProducer class.
     */
    @Test
    void testMessageMethods() {
        List<String> names = new ArrayList<>();
        List<Method> methods = List.of(networkProducer.getClass().getDeclaredMethods());
        for (Method method : methods) {
            names.add(method.getName());
        }
        assertTrue(names.contains("put"));
        assertTrue(names.contains("changeToSpecial"));
    }

    /**
     * Test the put method of the NetworkProducer class.
     */
    @Test
    void testPut() {
        NetworkMessage expectedNetworkMessage = new NetworkMessage("MQput", message.toString());
        Mockito.doNothing().when(mockClient).sendMessage(expectedNetworkMessage.toString());
        networkProducer.put(message);
        Mockito.verify(mockClient, Mockito.times(1)).sendMessage(expectedNetworkMessage.toString());
    }

    /**
     * Test the put method of the NetworkProducer class with a null message.
     */
    @Test
    void testPutNullMessage() {
        assertThrows(NullPointerException.class, () -> networkProducer.put(null));
    }

    /**
     * Test changeToSpecial method of the NetworkProducer class.
     */
    @Test
    void testChangeToSpecial() {
        assertNotNull(networkProducer.changeToSpecial(message));
    }

    /**
     * Test changeToSpecial method of the NetworkProducer class with a null message.
     */
    @Test
    void testChangeToSpecialNullMessage() {
        assertThrows(NullPointerException.class, () -> networkProducer.changeToSpecial(null));
    }
}
