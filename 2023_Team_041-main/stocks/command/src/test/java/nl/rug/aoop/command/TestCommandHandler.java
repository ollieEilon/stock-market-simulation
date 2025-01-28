package nl.rug.aoop.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 * Test class for CommandHandler.
 */
public class TestCommandHandler {
    /**
     * The command map.
     */
    private Map<String, Command> commandMap;
    /**
     * The command handler.
     */
    private CommandHandler commandHandler;

    /**
     * Set up the test environment.
     */
    @BeforeEach
    void setUp() {
        commandMap = new HashMap<>();
        commandHandler = new CommandHandler();
    }

    /**
     * Test the constructor of CommandHandler.
     */
    @Test
    void testCommandHandlerConstructor() {
        assertEquals(commandMap, commandHandler.getCommandMap());
    }

    /**
     * Test the constructor of CommandHandler with a null command map.
     */
    @Test
    void testCommandHandlerMethods() {
        List<String> names = new ArrayList<>();
        List<Method> methods = List.of(commandHandler.getClass().getDeclaredMethods());
        for (Method method : methods) {
            names.add(method.getName());
        }
        assertTrue(names.contains("registerCommand"));
        assertTrue(names.contains("executeCommand"));
    }

    /**
     * Test for a null command in the command map.
     */
    @Test
    void testRegisterNullCommand() {
        Command mockCommand = mock(Command.class);
        assertThrows(IllegalArgumentException.class, () -> commandHandler.registerCommand(null, mockCommand));
    }

    /**
     * Test for an empty command in the command map.
     */
    @Test
    void testRegisterEmptyCommand() {
        Command mockCommand = mock(Command.class);
        assertThrows(IllegalArgumentException.class, () -> commandHandler.registerCommand("", mockCommand));
    }

    /**
     * Test for a null command class in the command map.
     */
    @Test
    void testRegisterNullCommandClass() {
        assertThrows(IllegalArgumentException.class, () -> commandHandler.registerCommand("command", null));
    }

    /**
     * Test for a null command class and a null command in the command map.
     */
    @Test
    void testRegisterNullCommandClassNullCommandClass() {
        assertThrows(IllegalArgumentException.class, () -> commandHandler.registerCommand(null, null));
    }

    /**
     * Test for a null command class and an empty command in the command map.
     */
    @Test
    void testExecuteNullCommandNullParams() {
        assertThrows(IllegalArgumentException.class, () -> commandHandler.executeCommand(null, null));
    }

    /**
     * Test for a null command class and an empty command in the command map.
     */
    @Test
    void testExecuteEmptyCommandNullParams() {
        assertThrows(IllegalArgumentException.class, () -> commandHandler.executeCommand("", null));
    }

    /**
     * Test for a null command class and an empty command in the command map.
     */
    @Test
    void testExecuteNullParams() {
        assertThrows(IllegalArgumentException.class, () -> commandHandler.executeCommand("command", null));
    }

    /**
     * Test for a null command class and an empty command in the command map.
     */
    @Test
    void testCommandNotInMap() {
        Command mockCommand = mock(Command.class);
        commandMap.put("commandInMap", mockCommand);
        assertEquals(1, commandMap.size());
        assertNull(commandMap.get("commandNotInMap"));
    }

    /**
     * Test for a null command class and an empty command in the command map.
     */
    @Test
    void testCommandInMap() {
        Command mockCommand = mock(Command.class);
        commandMap.put("command", mockCommand);
        assertEquals(1, commandMap.size());
        assertEquals(mockCommand, commandMap.get("command"));
    }

    /**
     * Tests the executeCommand method of CommandHandler.
     */
    @Test
    public void testExecuteCommand() {
        CommandHandler commandHandler = new CommandHandler();
        Map<String, Object> params = new HashMap<>();
        params.put("param1", "value1");
        params.put("param2", "value2");
        Command command = mock(Command.class);
        commandHandler.registerCommand("testCommand", command);
        commandHandler.executeCommand("testCommand", params);
        verify(command, times(1)).execute(params);
    }
}
