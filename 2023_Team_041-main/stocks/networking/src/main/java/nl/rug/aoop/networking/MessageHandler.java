package nl.rug.aoop.networking;

/**
 * Interface to handle a message.
 */
public interface MessageHandler {
    /**
     * Handles a new message.
     * @param message to be handled.
     */
    void handleMessage(String message);
}