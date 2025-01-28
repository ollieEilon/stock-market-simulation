package nl.rug.aoop.networking;

import org.junit.jupiter.api.Disabled;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.Duration;

import static org.awaitility.Awaitility.await;

public class TestClientServerIntegration {
    @Disabled
    public void testIntegration() throws IOException {
        MessageHandler mockHandler = Mockito.mock(MessageHandler.class);
        Server server = new Server(0, mockHandler);
        new Thread(server).start();
        await().atMost(Duration.ofSeconds(5)).until(server::isRunning);

        MessageHandler messageHandler = Mockito.mock(MessageHandler.class);
        Client client = new Client(new InetSocketAddress("local host", server.getPortNumber()), messageHandler);
        new Thread(client).start();
        await().atMost(Duration.ofSeconds(5)).until(client::isRunning);

        //verify with mockito ??
    }
}
