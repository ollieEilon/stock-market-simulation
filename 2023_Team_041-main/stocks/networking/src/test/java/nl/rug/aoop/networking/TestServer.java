package nl.rug.aoop.networking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static nl.rug.aoop.networking.TestClient.ONE_SECOND_TIMEOUT;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the Server class.
 */
public class TestServer {
    /**
     * The server.
     */
    Server server;
    MessageHandler mockHandler;
    /**
     * The port number used for tests.
     */
    public static final int PORT_NUMBER = 0;
    /**
     * Out of range port number.
     */
    public static final int OUT_OF_RANGE_PORT_NUMBER = 65536;

    /**
     * Set port number
     */
    public static final int SET_PORT_NUMBER = 8080;

    /**
     * Set up the server.
     * @throws IOException if an I/O error occurs when opening the socket.
     */
    @BeforeEach
    public void setUp() throws IOException {
        this.mockHandler = Mockito.mock(MessageHandler.class);
        server = new Server(PORT_NUMBER, mockHandler);
    }

    /**
     * Test the constructor of the server class with valid arguments.
     */
    @Test
    void testServerConstructor() {
        assertNotNull(server);
    }

    /**
     * Test the constructor with an out of range port number.
     */
    @Test
    void testServerConstructorOutOfRangePort() {
        assertThrows(IllegalArgumentException.class, () -> new Server(OUT_OF_RANGE_PORT_NUMBER, mockHandler));
    }

    /**
     * Test the constructor with a null command handler.
     */
    @Test
    void testServerConstructorNullCommandHandler() {
        assertThrows(IllegalArgumentException.class, () -> new Server(PORT_NUMBER, null));
    }

    /**
     * Test the constructor with a null command handler and out of range port number.
     */
    @Test
    void testServerConstructorNullCommandHandlerOutOfRangePort() {
        assertThrows(IllegalArgumentException.class, () -> new Server(OUT_OF_RANGE_PORT_NUMBER, null));
    }

    /**
     * Test the methods of the server class.
     */
    @Test
    void testServerMethods() {
        List<String> names = new ArrayList<>();
        List<Method> methods = List.of(server.getClass().getDeclaredMethods());
        for (Method method : methods) {
            names.add(method.getName());
        }
        assertTrue(names.contains("run"));
        assertTrue(names.contains("terminate"));
        assertTrue(names.contains("isRunning"));
        assertTrue(names.contains("getPortNumber"));
        assertTrue(names.contains("getThreadNr"));
    }

    /**
     * Test the run method of the server class.
     */
    //todo: this is currently working since the run loop is infinite until we give it an end option
    @Test
    void testServerRun() {
        Thread thread = new Thread(server);
        thread.start();
        await().atMost(Duration.ofSeconds(ONE_SECOND_TIMEOUT)).until(() -> server.isRunning());
    }

    /**
     * Test the terminate method of the server class.
     */
    @Test
    void testServerTerminate() throws IOException {
        server.terminate();
        assertFalse(server.isRunning());
    }
    /**
     * Test the isRunning method of the server class.
     */
    @Test
    void testServerIsRunning() {
        assertFalse(server.isRunning());
    }


    /**
     * Test the getPortNumber method of the server class.
     * @throws IOException if an I/O error occurs when opening the socket.
     */
    @Test
    void testServerGetPortNumber() throws IOException {
        MessageHandler mockHandler = Mockito.mock(MessageHandler.class);
        Server server = new Server(SET_PORT_NUMBER, mockHandler);
        assertEquals(SET_PORT_NUMBER, server.getPortNumber());
    }

    /**
     * Test the getThreadNr method of the server class.
     */
    @Test
    void testServerGetThreadNr() {
        assertEquals(0, server.getThreadNr());
    }

}
