package nl.rug.aoop.messagequeue.message;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Message class.
 */
@Getter
@Slf4j
public class Message implements Comparable<Message> {
    /**
     * The header of the message.
     */
    private final String header;
    /**
     * The body of the message.
     */
    private final String body;
    /**
     * The timestamp of the message.
     */
    private final LocalDateTime timestamp;

    /**
     * Constructor.
     *
     * @param messageHeader is the header of the message.
     * @param messageBody   is the body of the message.
     */
    public Message(String messageHeader, String messageBody) {
        if (messageHeader == null || messageHeader.isEmpty()) {
            throw new IllegalArgumentException("messageHeader cannot be null or empty");
        }

        if (messageBody == null || messageBody.isEmpty()) {
            throw new IllegalArgumentException("messageBody cannot be null or empty");
        }

        this.header = messageHeader;
        this.body = messageBody;
        this.timestamp = LocalDateTime.now();
    }

    /**
     * Compare two messages.
     *
     * @param o the object to be compared.
     * @return the result of the comparison.
     */
    @Override
    public int compareTo(Message o) {
        return this.timestamp.compareTo(o.timestamp);
    }

    /**
     * Convert the message to JSON.
     * @return the JSON representation of the message.
     */
    public String toString() {
        if (header == null || header.isEmpty()|| body == null || body.isEmpty()|| timestamp == null) {
            log.error("messageHeader cannot be null or empty");
            throw new IllegalArgumentException("messageHeader cannot be null or empty");
        }
        log.info("{\"header\":\"" + header + "\",\"body\":\"" + body + "\",\"timestamp\":\"" + timestamp + "\"}");
        return "{\"header\":\"" + header + "\",\"body\":\"" + body + "\",\"timestamp\":\"" + timestamp + "\"}";
    }

    /**
     * Convert the message to JSON.
     * @param json the JSON representation of the message.
     * @return the message.
     */
    public static Message fromJSON(String json) {
        if (json == null || json.isEmpty()) {
            log.error("JSON cannot be null or empty");
            throw new IllegalArgumentException("JSON cannot be null or empty");
        }
        // Use regular expressions to extract values from JSON
        Pattern pattern = Pattern.compile("\\{\"header\":\"(.*?)\",\"body\":\"(.*?)\",\"timestamp\":\"(.*?)\"\\}");
        Matcher matcher = pattern.matcher(json);

        if (matcher.find()) {
            String header = matcher.group(1);
            String body = matcher.group(2);
            if (header == null || header.isEmpty()|| body == null || body.isEmpty()) {
                log.error("messageHeader cannot be null or empty");
                throw new IllegalArgumentException("messageHeader cannot be null or empty");
            }
            return new Message(header, body);
        } else {
            throw new IllegalArgumentException("Invalid JSON format for Message");
        }
    }
}
