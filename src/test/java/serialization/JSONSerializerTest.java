package serialization;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class JSONSerializerTest {
    private final JSONSerializer serializer = new JSONSerializer();
    @Test
    void serialize() throws SerializationError {
        // Given a HashMap
        HashMap<String, Integer> input = new HashMap<>() {{
            // https://stackoverflow.com/questions/8261075/adding-multiple-entries-to-a-hashmap-at-once-in-one-statement
            put("Pepsi", 2);
            put("Sprite", 4);
            put("Coke Zero", 3);
        }};

        // When I serialize it
        String result = serializer.serialize(input);

        // Then it should look like a JSON
        assertEquals("{\n" +
                "  \"Sprite\" : 4,\n" +
                "  \"Pepsi\" : 2,\n" +
                "  \"Coke Zero\" : 3\n" +
                "}", result);
    }
}