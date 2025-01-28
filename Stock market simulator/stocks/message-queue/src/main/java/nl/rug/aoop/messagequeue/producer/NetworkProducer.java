package nl.rug.aoop.messagequeue.producer;

import nl.rug.aoop.messagequeue.message.Message;
import nl.rug.aoop.messagequeue.message.NetworkMessage;
import nl.rug.aoop.networking.Client;

/**
 * A producer that sends messages over the network.
 */
public class NetworkProducer implements MQProducer  {
    /**
     * The client to send the message with.
     */
    private final Client client;

    /**
     * Constructor.
     * @param client the client to send the message with.
     */
    public NetworkProducer(Client client) {
        if (client == null) {
            throw new NullPointerException("Client cannot be null");
        }
        this.client = client;
    }

    /**
     * Change the message to a special message.
     * @param message the message to change.
     * @return the changed message.
     */
    public NetworkMessage changeToSpecial(Message message) {
        return new NetworkMessage("MQput", message.toString());
    }

    /**
     * Put a message in the queue.
     * @param message the message to put in the queue.
     */
    @Override
    public void put(Message message) {
        NetworkMessage message1 = changeToSpecial(message);
        client.sendMessage(message1.toString());
    }
}
