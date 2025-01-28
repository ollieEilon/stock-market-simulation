package nl.rug.aoop.messagequeue;

import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.messagequeue.message.Message;
import nl.rug.aoop.messagequeue.queues.MessageQueue;
import nl.rug.aoop.messagequeue.queues.NetworkQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;


import java .util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestCommandMessageHandler {
    private CommandHandler mockCommandHandler;
    private MessageQueue mockMessageQueue;
    private CommandMessageHandler commandMessageHandler;

    @BeforeEach
    void setUp() {
        mockCommandHandler = mock(CommandHandler.class);
        mockMessageQueue = mock(NetworkQueue.class);
        commandMessageHandler = new CommandMessageHandler(mockCommandHandler, mockMessageQueue);
    }

    /**
     * Test the constructor of CommandMessageHandler.
     */
    @Test
    void testCommandMessageHandlerConstructor() {
        assertNotNull(commandMessageHandler);
    }

    /**
     * Test the constructor of CommandMessageHandler with a null command handler.
     */
    @Test
    void testCommandMessageHandlerConstructorNullCommandHandler() {
        assertThrows(NullPointerException.class, () -> new CommandMessageHandler(null, mockMessageQueue));
    }

    /**
     * Test the constructor of CommandMessageHandler with a null message queue.
     */
    @Test
    void testCommandMessageHandlerConstructorNullMessageQueue() {
        assertThrows(NullPointerException.class, () -> new CommandMessageHandler(mockCommandHandler, null));
    }

    /**
     * Test the constructor of CommandMessageHandler with a null command handler and a null message queue.
     */
    @Test
    void testCommandMessageHandlerConstructorNullCommandHandlerNullMessageQueue() {
        assertThrows(NullPointerException.class, () -> new CommandMessageHandler(null, null));
    }


    /**
     * Tests the handleMessage method of CommandMessageHandler with a null message.
     */
    @Test
    void testHandleMessageNullMessage() {
        assertThrows(NullPointerException.class, () -> commandMessageHandler.handleMessage(null));
    }

    /**
     * Tests the handleMessage method of CommandMessageHandler with a valid message.
     */
    @Disabled
    public void testHandleMessageValidMessage() {
        String message = "{\"header\":\"test\",\"body\":\"test\",\"timestamp\":\"2021-03-31T15:00:00.000\"}";
        System.out.println(message);
        Message message1 = new Message("test", "test");
        //System.out.println(message1.toString());
        commandMessageHandler.handleMessage(message1.toString());
        Map<String, Object> messageSystem = new HashMap<>();
        messageSystem.put("test", mockMessageQueue);
        //verify(mockCommandHandler).executeCommand("test", messageSystem);
    }
}