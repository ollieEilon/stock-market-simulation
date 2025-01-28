package nl.rug.aoop.networking;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;

/**
 * ThreadEchoHandler class.
 */
@Slf4j
public class ClientHandler implements Runnable, Closeable {
    /**
     * The socket to handle.
     */
    private final Socket socket;
    /**
     * The thread number.
     */
    @Getter
    private final int threadNr;
    /**
     * The input and output streams.
     */
    private final BufferedReader in;
    /**
     * The output stream.
     */
    private final PrintWriter out;
    /**
     * The command handler.
     */
    private final MessageHandler messageHandler;
    /**
     * The running state of the handler.
     */
    @Getter
    private boolean running = false;

    /**
     * Constructor.
     * @param socket the socket to handle.
     * @param threadNr the thread number.
     * @param messageHandler the command handler.
     * @throws IOException if an I/O error occurs when opening the socket.
     */
    public ClientHandler(Socket socket, int threadNr, MessageHandler messageHandler) throws IOException {
        if (socket == null) {
            log.error("Socket cannot be null");
            throw new IllegalArgumentException("Socket cannot be null");
        }
        if (threadNr < 0) {
            log.error("Thread number cannot be negative");
            throw new IllegalArgumentException("Thread number cannot be negative");
        }
        if (messageHandler == null) {
            log.error("Command handler cannot be null");
            throw new IllegalArgumentException("Command handler cannot be null");
        }
        this.socket = socket;
        this.threadNr = threadNr;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            log.info("Handler initialized");
        } catch (IllegalArgumentException | IOException e) {
            log.error("Error initializing the handler: " + e.getMessage());
            throw e;
        }
        this.messageHandler = messageHandler;
    }

    /**
     * Method to start the handler.
     */
    @Override
    public void run() {
        running = true;
        while (running){
            try {
                String fromClient = in.readLine();
                //returning a message back to let the client know that the server has received the message.
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                out.println("Received message: " + fromClient);
                out.flush();
                messageHandler.handleMessage(fromClient);
                out.println(fromClient);
                log.info("Received string: " + fromClient);
            } catch (IOException e) {
                log.error("Error reading string " + e.getMessage());
                terminate();
            }
        }
    }

    /**
     * Method to terminate the handler.
     */
    public void terminate() {
        running = false;
        close();
    }

    /**
     * Close the resources associated with the handler.
     */
    @Override
    public void close() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            log.error("Error while closing resources: " + e.getMessage());
        }
    }
}
