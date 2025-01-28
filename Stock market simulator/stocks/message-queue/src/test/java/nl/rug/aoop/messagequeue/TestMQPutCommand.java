package nl.rug.aoop.messagequeue;

import nl.rug.aoop.messagequeue.commands.MQPutCommand;
import nl.rug.aoop.messagequeue.message.Message;
import nl.rug.aoop.messagequeue.queues.MessageQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test the CommandMessageHandler class.
 */
public class TestMQPutCommand {
    /**
     * The parameters.
     */
    Map<String, Object> params;
    /**
     * The command.
     */
    MQPutCommand command;
    /**
     * Set up the test.
     */
    @BeforeEach
    void setup() {
        params = new HashMap<>();
        command = new MQPutCommand();
    }
    /**
     * Test the constructor of CommandMessageHandler.
     */
    @Test
    void testMessageConstructor() {
        assertNotNull(params);
    }

    /**
     * Test the constructor of CommandMessageHandler with a null command handler.
     */
    @Test
    void testNoQueue() {
        Object m = new Message("header", "body");
        params.put("message", m);
        assertThrows(IllegalArgumentException.class, () -> command.execute(params));
    }

    /**
     * Test the constructor of CommandMessageHandler with a null message queue.
     */
    @Test
    void testNoMessage() {
        MessageQueue mq = Mockito.mock(MessageQueue.class);
        params.put("queue", mq);
        assertThrows(IllegalArgumentException.class, () -> command.execute(params));
    }

    /**
     *  Test the constructor of CommandMessageHandler with a null command handler and a null message queue.
     */
    @Test
    void testParamsWithMessageAndQueue() {
        MessageQueue mq = Mockito.mock(MessageQueue.class);
        params.put("queue", mq);
        Object m = new Message("header", "body");
        params.put("message", m);
        assertNotNull(params.get("queue"));
        assertNotNull(params.get("message"));
    }
}
