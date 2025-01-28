package nl.rug.aoop.messagequeue.messageTests;

import nl.rug.aoop.messagequeue.message.Message;
import nl.rug.aoop.messagequeue.message.NetworkMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestNetworkMessage {
    /**
     * The message to test.
     */
    private NetworkMessage message;
    /**
     * The header and body of the message.
     */
    private String messageHeader;
    /**
     * The header and body of the message.
     */
    private String messageBody;

    /**
     * Set up the test environment.
     */
    @BeforeEach
    void setUp() {
        messageHeader = "header";
        messageBody = "body";
        message = new NetworkMessage(messageHeader, messageBody);
    }

    /**
     * Test the constructor of the Message class.
     */
    @Test
    void testMessageConstructor() {
        assertEquals(messageHeader, message.getHeader());
        assertEquals(messageBody, message.getBody());
    }

    /**
     * Check if the message is immutable.
     */
    @Test
    void testMessageImmutable() {
        List<Field> fields = List.of(Message.class.getDeclaredFields());
        fields.forEach(field -> assertTrue(Modifier.isFinal(field.getModifiers()), field.getName() + " is not final"));
    }

    /**
     * Test the methods of the Message class.
     */
    @Test
    void testMessageMethods() {
        List<String> names = new ArrayList<>();
        List<Method> methods = List.of(message.getClass().getDeclaredMethods());
        for (Method method : methods) {
            names.add(method.getName());
        }
        assertTrue(names.contains("toString"));
        assertTrue(names.contains("fromJSON"));
        assertTrue(names.contains("getBody"));
        assertTrue(names.contains("getHeader"));
    }

    /**
     * Test the constructor with a valid input.
     */
    @Test
    void testConstructorWithValidInput() {
        assertEquals(messageHeader, message.getHeader());
        assertEquals(messageBody, message.getBody());
    }


    /**
     * Test the constructor with a null header.
     */
    @Test
    void testConstructorWithNullHeader() {
        assertThrows(IllegalArgumentException.class, () -> new NetworkMessage(null, messageBody));
    }

    /**
     * Test the constructor with a null body.
     */
    @Test
    void testConstructorWithNullBody() {
        assertThrows(IllegalArgumentException.class, () -> new NetworkMessage(messageHeader, null));
    }

    /**
     * Test the constructor with a null header and body.
     */
    @Test
    void testConstructorWithNullHeaderAndBody() {
        assertThrows(IllegalArgumentException.class, () -> new NetworkMessage(null, null));
    }


    /**
     * Test the constructor with an empty header.
     */
    @Test
    void testConstructorWithEmptyHeader() {
        assertThrows(IllegalArgumentException.class, () -> new NetworkMessage("", messageBody));
    }

    /**
     * Test the constructor with an empty body.
     */
    @Test
    void testConstructorWithEmptyBody() {
        assertThrows(IllegalArgumentException.class, () -> new NetworkMessage(messageHeader, ""));
    }

    /**
     * Test the constructor with an empty header and body.
     */
    @Test
    void testConstructorWithEmptyHeaderAndBody() {
        assertThrows(IllegalArgumentException.class, () -> new NetworkMessage("", ""));
    }

    /**
     * Test the toString method.
     */
    @Test
    void testToString() {
        String expected =  "{\"header\": \"" + message.getHeader() + "\", \"body\": \"" + message.getBody() + "\"}";
        assertEquals(expected, message.toString());
    }

    /**
     * Test the fromJson method.
     */
    @Test
    public void testMessageFromJSON() {
        String json = "{\"header\":\"Sample Header\",\"body\":\"Sample Body\"}";
        NetworkMessage message = NetworkMessage.fromJSON(json);

        assertEquals("Sample Header", message.getHeader());
        assertEquals("Sample Body", message.getBody());
    }

    /**
     * Test the fromJson method with a null input.
     */
    @Test
    public void testMessageFromJSONWithNullInput() {
        assertThrows(IllegalArgumentException.class, () -> NetworkMessage.fromJSON(null));
    }

    /**
     * Test the fromJson method with an empty input.
     */
    @Test
    public void testMessageFromJSONWithEmptyInput() {
        assertThrows(IllegalArgumentException.class, () -> NetworkMessage.fromJSON(""));
    }

    /**
     * Test the fromJson method with an invalid input.
     */
    @Test
public void testMessageFromJSONWithInvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> NetworkMessage.fromJSON("invalid"));
    }

    /**
     * Test the fromJson method with no header.
     */
    @Test
    void testMessageFromJSONWithNoHeader() {
        String json = "{\"body\":\"Sample Body\"}";
        assertThrows(IllegalArgumentException.class, () -> NetworkMessage.fromJSON(json));
    }

    /**
     * Test the fromJson method with no body.
     */
    @Test
    void testMessageFromJSONWithNoBody() {
        String json = "{\"header\":\"Sample Header\"}";
        assertThrows(IllegalArgumentException.class, () -> NetworkMessage.fromJSON(json));
    }

    /**
     * Test the fromJson method with no header and body.
     */
    @Test
    void testMessageFromJSONWithNoHeaderAndBody() {
        String json = "{}";
        assertThrows(IllegalArgumentException.class, () -> NetworkMessage.fromJSON(json));
    }
}
