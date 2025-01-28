package nl.rug.aoop.networking;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static nl.rug.aoop.networking.TestServer.PORT_NUMBER;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class TestClient {
    /**
     * The port number used for tests.
     */
    InetSocketAddress address;

    private Server server;
    /**
     * If the server is started.
     */
    private boolean serverStarted;
    private MessageHandler clientMessageHandler;
    /**
     * The port number used for tests.
     */
    private int serverPort;
    /**
     * The client used for tests.
     */
    Client client;
    /**
     * The timeout.
     */
    public static final int TIMEOUT = 110;
    /**
     * ONe second timeout.
     */
    public static final int ONE_SECOND_TIMEOUT = 1;
    /**
     * Set up the client.
     * @throws IOException if an I/O error occurs when opening the socket.
     */
    @BeforeEach
    void setUp() throws IOException {
        startTempServer();
        this.address = new InetSocketAddress("localhost", serverPort);
        this.clientMessageHandler = Mockito.mock(MessageHandler.class);
        client = new Client(address, clientMessageHandler);
    }

    /**
     * Terminate the client.
     */
    @AfterEach
    void endTest() throws IOException {
        if (client != null && client.isConnected()) {
            client.terminate();
        }
        server.terminate();
    }

    /**
     * Start a temporary server.
     */
    private void startTempServer() {
        new Thread( () -> {
            try {
                MessageHandler serverMessageHandler = Mockito.mock(MessageHandler.class);
                this.server = new Server(PORT_NUMBER, serverMessageHandler);
                serverPort = server.getPortNumber();
                serverStarted = true;
                server.run();
            } catch (IOException e) {
                log.error("Could not start server", e);
            }
        }).start();

        await().atMost(Duration.ofSeconds(ONE_SECOND_TIMEOUT)).until(() -> serverStarted);
    }

    private void startClientWithMessage(String message) {
        client.setFromServer(message);
        Thread thread = new Thread(client);
        thread.start();
        await().atMost(Duration.ofMillis(TIMEOUT)).until(() -> client.isRunning());
    }

    /**
     * Test the constructor of the Client class with a valid input.
     */
    @Test
    void testClientConstructor() {
        assertNotNull(client);
    }

    /**
     * Test the constructor of the Client class with a null address.
     */
    @Test
    void testClientConstructorNullAddress() {
        assertThrows(IllegalArgumentException.class, () -> new Client(null, clientMessageHandler));
    }

    /**
     * Test the constructor of the Client class with a null message handler.
     */
    @Test
    void testClientConstructorNullMessageHandler() {
        assertThrows(IllegalArgumentException.class, () -> new Client(address, null));
    }

    /**
     * Test the constructor of the Client class with a null address and message handler.
     */
    @Test
    void testClientConstructorNullAddressAndMessageHandler() {
        assertThrows(IllegalArgumentException.class, () -> new Client(null, null));
    }

    /**
     * Test the methods of the Client class.
     */
    @Test
    void testClientMethods() {
        List<String> names = new ArrayList<>();
        List<Method> methods = List.of(client.getClass().getDeclaredMethods());
        for (Method method : methods) {
            names.add(method.getName());
        }

        assertTrue(names.contains("initSocket"));
        assertTrue(names.contains("run"));
        assertTrue(names.contains("terminate"));
    }

    /**
     * Test the constructor of the Client class with a running server.
     * @throws IOException if an I/O error occurs when opening the socket.
     */
    @Test
    void testConstructorWithRunningServer() {
        assertTrue(client.isConnected());
    }

    @Test
    void testInitSocket() {
        assertTrue(client.getSocket().isConnected());
    }

    /**
     * Test the run method of the Client class.
     */
    @Test
    void testClientRun() {
        assertTrue(client.isConnected());
    }

    /**
     * Test the sendMessage method of the Client class with a null message.
     */
    @Test
    void testClientSendNullMessage() {
        assertThrows(IllegalArgumentException.class, () -> startClientWithMessage(null));
    }

    /**
     * Test the sendMessage method of the Client class with an empty message.
     */
    @Test
    void testClientSendEmptyMessage() {
        assertThrows(IllegalArgumentException.class, () -> startClientWithMessage(""));
    }

    /**
     * Test the run method of the Client class with a single message.
     */
    @Disabled
    void testClientRunReadSingleMessage() {
        String message = "HELLO";
        startClientWithMessage(message);
        MessageHandler mockHandler = client.getMessageHandler();

        client.getOut().println(message);
        assertTrue(client.isRunning());
        assertTrue(client.isConnected());

        Mockito.verify(mockHandler).handleMessage(message);
    }

    /**
     * Test the terminate method of the Client class.
     */
    @Test
    void testClientTerminate() {
        client.setFromServer("HELLO");
        Thread thread = new Thread(client);
        thread.start();
        await().atMost(Duration.ofMillis(TIMEOUT)).until(() -> client.isRunning());
        assertTrue(client.isRunning());
        client.terminate();
        assertFalse(client.isRunning());
        assertFalse(client.isConnected());
    }
}
