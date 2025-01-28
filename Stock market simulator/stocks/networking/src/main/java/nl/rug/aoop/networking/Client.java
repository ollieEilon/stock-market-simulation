package nl.rug.aoop.networking;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Client class.
 */
@Slf4j
public class Client implements Runnable {
    /**
     * The timeout.
     */
    public static final int TIMEOUT = 1000;
    /**
     * The address.
     */
    private final InetSocketAddress address;
    /**
     * The message to be sent.
     */
    @Setter
    private String fromServer;
    /**
     * The socket.
     */
    @Getter
    private Socket socket;
    /**
     * If a client is actively listening for messages.
     */
    @Getter
    private boolean running = false;
    /**
     * If client is connected to a socket.
     */
    @Getter
    private boolean connected = false;
    /**
     * The input and output streams.
     */
    @Getter
    private PrintWriter out;
    /**
     * The input stream.
     */
    private BufferedReader in;
    /**
     * The message handler.
     */
    @Getter
    private final MessageHandler messageHandler;

    /**
     * Constructor.
     *
     * @param address        the address.
     * @param messageHandler the message handler.
     * @throws IOException if an I/O error occurs when opening the socket.
     */

    public Client(InetSocketAddress address, MessageHandler messageHandler) throws IOException {
        if (address == null) {
            log.error("Address cannot be null");
            throw new IllegalArgumentException("Address cannot be null");
        }
        if (messageHandler == null) {
            log.error("Message handler cannot be null");
            throw new IllegalArgumentException("Message handler cannot be null");
        }
        this.address = address;
        initSocket();
        this.messageHandler = messageHandler;
        connected = true;
    }

    /**
     * Method to send a message.
     *
     * @throws IOException if an I/O error occurs when opening the socket.
     */
    private void initSocket() throws IOException {
        this.socket = new Socket();
        socket.connect(address, TIMEOUT);
        if (!this.socket.isConnected()) {
            log.error("Socket could not connect at port " + address.getPort());
            throw new IOException("Socket could not connect");
        }
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    /**
     * Method to send a message.
     */
    public void run() {
        running = true;
        while (running) {
            try {
                sendMessage(fromServer); //TODO: fix the thing so it takes in a real message
                if (in.ready()) {
                    //log.info("Server sent " + fromServer);
                    messageHandler.handleMessage(fromServer);
                }
            } catch (IOException e) {
                log.error("Could not read line from server", e);
            }
        }
    }

    /**
     * Method to terminate the client.
     */
    public void terminate() {
        log.info("Attempting to terminate client.");
        running = false;
        try {
            close();
        } catch (IOException e) {
            log.error("Cannot close socket");
        }
        connected = false;
    }

    /**
     * Sends a message to server. In handle message, need a reporter to send prompt the server to send
     * a message back.
     *
     * @param message the message to be sent.
     */
    public void sendMessage(String message) {
        if (message == null || message.isEmpty()) {
            log.error("Attempting to send an invalid message");
            throw new IllegalArgumentException("Attempting to send an invalid message");
        }
        out.println(message);
    }

    /**
     * Closes the input and output streams and the socket.
     *
     * @throws IOException if an I/O error occurs when opening the socket.
     */
    public void close() throws IOException {
        in.close();
        out.close();
        socket.close();
    }
}
