package nl.rug.aoop.networking;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Server class.
 */
@Slf4j
public class Server implements Runnable {
    /**
     * The server socket.
     */
    private final ServerSocket serverSocket;
    /**
     * The command handler.
     */
    private final MessageHandler messageHandler;
    /**
     * The running state of the server.
     */
    @Getter
    private boolean running = false;
    /**
     * The id number of the client.
     */
    @Getter
    private int threadNr;

    /**
     * The port number.
     */
    @Getter
    private final int portNumber;

    /**
     * The executor service.
     */
    private final ExecutorService executorService;

    /**
     * Method to start the server.
     * @param portNumber the port number.
     * @param messageHandler the command handler.
     * @throws IOException if an I/O error occurs when opening the socket.
     */

    public Server(int portNumber, MessageHandler messageHandler) throws IOException {
        try {
            if (portNumber < 0 || portNumber > 65535) {
                log.error("Port number must be between 0 and 65535");
                throw new IllegalArgumentException("Port number must be between 0 and 65535");
            }
            if (messageHandler == null) {
                log.error("Command handler cannot be null");
                throw new IllegalArgumentException("Command handler cannot be null");
            }
            this.threadNr = 0;
            this.messageHandler = messageHandler;
            serverSocket = new ServerSocket(portNumber);
            this.portNumber = serverSocket.getLocalPort();
            executorService = Executors.newCachedThreadPool();
            log.info("Server started on port " + portNumber);
        } catch (IOException e) {
            log.error("Error initializing the server: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Method to run the server.
     */
    //todo: fix the run method because it doesnt have a way to break out of it
    @Override
    public void run() {
        running = true;
        while (running) {
            try {
                Socket socket = serverSocket.accept();
                ClientHandler handler = new ClientHandler(socket, threadNr, messageHandler);
                log.info("Accepted connection from client " + threadNr);
                executorService.submit(handler);
                threadNr++;
            } catch (IOException e) {
                log.error("Accept issues " + e.getMessage());
            }
        }
    }

    /**
     * Method to terminate the server.
     */
    public void terminate() throws IOException {
        running = false;
        this.executorService.shutdown();
        serverSocket.close();
    }
}

