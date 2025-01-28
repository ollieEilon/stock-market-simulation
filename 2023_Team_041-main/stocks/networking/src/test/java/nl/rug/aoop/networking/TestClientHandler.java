package nl.rug.aoop.networking;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static nl.rug.aoop.networking.Client.TIMEOUT;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the ClientHandler class.
 */
public class TestClientHandler {
    /**
     * The port number used for tests.
     */
    public static final int PORT_NUMBER = 0;
    /**
     * The client handler used for tests.
     */
    ClientHandler clientHandler;

    /**
     * The thread number.
     */
    public static final int THREAD_NUMBER = 0;

    /**
     * An invalid thread number.
     */
    public static final int INVALID_THREAD_NUMBER = -1;

    /**
     * The server socket and client socket used for tests.
     */
    private ServerSocket serverSocket;
    /**
     * The client socket used for tests.
     */
    private Socket clientSocket;
    /**
     * The message handler used for tests.
     */
    private MessageHandler mockHandler;

    /**
     * Set up the client handler.
     * @throws IOException if an I/O error occurs when opening the socket.
     */
    @BeforeEach
    public void setUp() throws IOException {
        serverSocket = new ServerSocket(PORT_NUMBER);
        clientSocket = new Socket("localhost", serverSocket.getLocalPort());
        this.mockHandler = Mockito.mock(MessageHandler.class);
        clientHandler = new ClientHandler(clientSocket, THREAD_NUMBER, mockHandler);
    }

    /**
     * Tear down the client handler.
     * @throws IOException if an I/O error occurs when opening the socket.
     */
    @AfterEach
    public void tearDown() throws IOException {
        clientSocket.close();
        serverSocket.close();
    }


    /**
     * Test the methods of the ClientHandler class.
     */
    @Test
    void testMessageMethods() {
        List<String> names = new ArrayList<>();
        List<Method> methods = List.of(clientHandler.getClass().getDeclaredMethods());
        for (Method method : methods) {
            names.add(method.getName());
        }
        assertTrue(names.contains("run"));
        assertTrue(names.contains("terminate"));
        assertTrue(names.contains("close"));
        assertTrue(names.contains("isRunning"));
    }

    /**
     * Test the constructor of the ClientHandler class with a valid input.
     */
    @Test
    void testClientHandlerConstructor() {
        assertNotNull(clientHandler);
    }

    /**
     * Test the constructor with a null socket.
     */
    @Test
    void testClientHandlerConstructorNullSocket() {
        assertThrows(IllegalArgumentException.class, () -> new ClientHandler(null, THREAD_NUMBER, mockHandler));
    }

    /**
     * Test the constructor with a null command handler.
     */
    @Test
    void testClientHandlerConstructorNullCommandHandler() {
        assertThrows(IllegalArgumentException.class, () -> new ClientHandler(clientSocket, THREAD_NUMBER, null));
    }

    /**
     * Test the constructor with an invalid thread number.
     */
    @Test
    void testClientHandlerConstructorNegativeThreadNumber() {
        assertThrows(IllegalArgumentException.class, () -> new ClientHandler(clientSocket, INVALID_THREAD_NUMBER, mockHandler));
    }

    /**
     * Test the constructor with a null socket, command handler and an invalid thread number.
     */
    @Test
    void testClientHandlerConstructorNullSocketNullCommandHandlerNegativeThreadNumber() {
        assertThrows(IllegalArgumentException.class, () -> new ClientHandler(null, INVALID_THREAD_NUMBER, null));
    }

    /**
     * Test the constructor with a null socket and command handler.
     */
    @Test
    void testClientHandlerConstructorNullSocketNullCommandHandler() {
        assertThrows(IllegalArgumentException.class, () -> new ClientHandler(null, THREAD_NUMBER, null));
    }

    /**
     * Test the constructor with a null socket and an invalid thread number.
     */
    @Test
    void testClientHandlerConstructorNullSocketNegativeThreadNumber() {
        assertThrows(IllegalArgumentException.class, () -> new ClientHandler(null, INVALID_THREAD_NUMBER, mockHandler));
    }

    /**
     * Test the constructor with a null command handler and an invalid thread number.
     */
    @Test
    void testClientHandlerConstructorNullCommandHandlerNegativeThreadNumber() {
        assertThrows(IllegalArgumentException.class, () -> new ClientHandler(clientSocket, INVALID_THREAD_NUMBER, null));
    }

    /**
     * Test the terminate method of the ClientHandler class.
     */
    @Test
    void testClientHandlerTerminate() {
        clientHandler.terminate();
        assertFalse(clientHandler.isRunning());
    }

    /**
     * Test the isRunning method of the ClientHandler class.
     */
    @Test
    void testClientHandlerIsRunning() {
        assertFalse(clientHandler.isRunning());
    }

    /**
     * Test the run method of the ClientHandler class.
     */
    @Test
    void testClientHandlerRun() {
        Thread thread = new Thread(clientHandler);
        thread.start();
        await().atMost(Duration.ofSeconds(TIMEOUT)).until(() -> clientHandler.isRunning());
    }

}
