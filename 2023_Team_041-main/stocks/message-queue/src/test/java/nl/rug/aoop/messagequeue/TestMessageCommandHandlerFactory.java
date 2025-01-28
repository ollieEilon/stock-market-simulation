package nl.rug.aoop.messagequeue;

import nl.rug.aoop.command.Command;
import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.messagequeue.factories.MessageCommandHandlerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Tests for message command handler factory.
 */
public class TestMessageCommandHandlerFactory {
    private CommandHandler ch;
    @BeforeEach
    void setUp() {
        MessageCommandHandlerFactory factory = new MessageCommandHandlerFactory();
        this.ch = factory.createCommandHandler();
    }

    /**
     * Checks whether a factory is successfully created.
     */
    @Test
    void testCreateCommandHandlerNotNull() {
        assertNotNull(ch);
    }

    /**
     * Checks whether the MqPut command was added as a command to the command handler.
     */
    @Test
    void testMqPutInCommandHandler() {
        Command mqPut = ch.getCommandMap().get("MqPut");
        assertNotNull(mqPut);
    }
}
