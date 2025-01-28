package nl.rug.aoop.messagequeue.messageTests;

import nl.rug.aoop.messagequeue.message.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the Message class.
 */
public class TestMessage {
    /**
     * The message to test.
     */
    private Message message;
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
        message = new Message(messageHeader, messageBody);
    }

    /**
     * Test the constructor of the Message class.
     */
    @Test
    void testMessageConstructor() {
        assertEquals(messageHeader, message.getHeader());
        assertEquals(messageBody, message.getBody());
        assertNotNull(message.getTimestamp());
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
        assertTrue(names.contains("compareTo"));
        assertTrue(names.contains("toString"));
        assertTrue(names.contains("fromJSON"));
        assertTrue(names.contains("getHeader"));
        assertTrue(names.contains("getBody"));
        assertTrue(names.contains("getTimestamp"));
    }

    /**
     * Test the constructor with a valid input.
     */
    @Test
    void testConstructorWithValidInput() {
        assertEquals(messageHeader, message.getHeader());
        assertEquals(messageBody, message.getBody());
        assertNotNull(message.getTimestamp());
    }


    /**
     * Test the constructor with a null header.
     */
    @Test
    void testConstructorWithNullHeader() {
        assertThrows(IllegalArgumentException.class, () -> new Message(null, messageBody));
    }

    /**
     * Test the constructor with a null body.
     */
    @Test
    void testConstructorWithNullBody() {
        assertThrows(IllegalArgumentException.class, () -> new Message(messageHeader, null));
    }

    /**
     * Test the constructor with a null header and body.
     */
    @Test
    void testConstructorWithNullHeaderAndBody() {
        assertThrows(IllegalArgumentException.class, () -> new Message(null, null));
    }


    /**
     * Test the constructor with an empty header.
     */
    @Test
    void testConstructorWithEmptyHeader() {
        assertThrows(IllegalArgumentException.class, () -> new Message("", messageBody));
    }

    /**
     * Test the constructor with an empty body.
     */
    @Test
    void testConstructorWithEmptyBody() {
       assertThrows(IllegalArgumentException.class, () -> new Message(messageHeader, ""));
    }

    /**
     * Test the constructor with an empty header and body.
     */
    @Test
    void testConstructorWithEmptyHeaderAndBody() {
        assertThrows(IllegalArgumentException.class, () -> new Message("", ""));
    }

    /**
     * Test the toString method.
     */
    @Test
    void testToString() {
        String expected =  "{\"header\":\"" + message.getHeader() + "\",\"body\":\"" + message.getBody() + "\",\"timestamp\":\"" + message.getTimestamp() + "\"}";
        assertEquals(expected, message.toString());
    }


    /**
     * Test the fromJson method.
     */
    @Test
    public void testMessageFromJSON() {
        String json = "{\"header\":\"Sample Header\",\"body\":\"Sample Body\",\"timestamp\":\"2023-10-10T12:34:56.789\"}";
        Message message = Message.fromJSON(json);

        assertEquals("Sample Header", message.getHeader());
        assertEquals("Sample Body", message.getBody());
    }

    /**
     * Test the fromJson method with no header in the JSON.
     */
    @Test
    void testMessageFromJSONWithInvalidJSON() {
        String json = "{\"body\":\"Sample Body\",\"timestamp\":\"2023-10-10T12:34:56.789\"}";
        assertThrows(IllegalArgumentException.class, () -> Message.fromJSON(json));
    }

    /**
     * Test the fromJson method with no body in the JSON.
     */
    @Test
    void testMessageFromJSONWithNoBody() {
        String json = "{\"header\":\"Sample Header\",\"timestamp\":\"2023-10-10T12:34:56.789\"}";
        assertThrows(IllegalArgumentException.class, () -> Message.fromJSON(json));
    }

    /**
     * Test the fromJson method with no timestamp in the JSON.
     */
    @Test
    void testMessageFromJSONWithNoTimestamp() {
        String json = "{\"header\":\"Sample Header\",\"body\":\"Sample Body\"}";
        assertThrows(IllegalArgumentException.class, () -> Message.fromJSON(json));
    }

    /**
     * Test the fromJson method with an empty JSON.
     */
    @Test
    void testMessageFromJSONWithEmptyJSON() {
        assertThrows(IllegalArgumentException.class, () -> Message.fromJSON(""));
    }

    /**
     * Tets the fromJson method with a null JSON.
     */
    @Test
    void testMessageFromJSONWithNullJSON() {
        assertThrows(IllegalArgumentException.class, () -> Message.fromJSON(null));
    }

    /**
     * Test the fromJson method with an invalid JSON.
     */
    @Test
    void testMessageFromJSONWithInvalidJSON2() {
        assertThrows(IllegalArgumentException.class, () -> Message.fromJSON("invalid"));
    }
}
