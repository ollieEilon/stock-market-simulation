package nl.rug.aoop.messagequeue.message;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Message that is sent over the network.
 */
@Getter
@Slf4j
public class NetworkMessage {
    /**
     * Header of the message.
     */
    private final String header;
    /**
     * Body of the message.
     */
    private final String body;

    /**
     * Constructor for the message.
     * @param header header of the message
     * @param body body of the message
     */
    public NetworkMessage(String header, String body) {
        if (header == null || header.isEmpty()) {
            throw new IllegalArgumentException("messageHeader cannot be null or empty");
        }

        if (body == null || body.isEmpty()) {
            throw new IllegalArgumentException("messageBody cannot be null or empty");
        }
        this.header = header;
        this.body = body;
    }

    /**
     * Convert the message to JSON.
     * @return the JSON representation of the message.
     */
    public String toString() {
        return "{\"header\": \"" + header + "\", \"body\": \"" + body + "\"}";
    }

    /**
     * Convert the message to JSON.
     * @param json the JSON representation of the message.
     * @return the message.
     */
    public static NetworkMessage fromJSON(String json) {
        if (json == null || json.isEmpty()) {
            throw new IllegalArgumentException("json cannot be null or empty");
        }
        // Use regular expressions to extract values from JSON
        Pattern pattern = Pattern.compile("\\{\"header\":\"(.*?)\",\"body\":\"(.*?)\"\\}");
        Matcher matcher = pattern.matcher(json);

        if (matcher.find()) {
            String header = matcher.group(1);
            String body = matcher.group(2);
            if (header == null || header.isEmpty()|| body == null || body.isEmpty()){
                log.error("Invalid JSON format for Message");
                throw new IllegalArgumentException("Invalid JSON format for Message");
            }
            return new NetworkMessage(header, body);
        } else {
            throw new IllegalArgumentException("Invalid JSON format for Message");
        }
    }
}
