package serialization;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JSONDeserializerTest {

    private final JSONDeserializer deserializer = new JSONDeserializer();
    @Test
    void deserialize() throws DeserializationError {
        // Given a valid JSON string
        String input = "{\"foo\": 1}";

        // When I deserialize it
        Map<String, Integer> result = deserializer.deserialize(input);

        // Then it should match my hashmap
        assertEquals(new HashMap<String, Integer>() {{
            put("foo", 1);
        }}, result);
    }
}