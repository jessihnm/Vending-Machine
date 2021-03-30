package serialization;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class JSONSerializerTest {
    private final JSONSerializer serializer = new JSONSerializer();

    @Test
    void serialize() throws SerializationError {
        // Given a HashMap
        HashMap<String, Integer> input = new HashMap<String, Integer>() {{
            // https://stackoverflow.com/questions/8261075/adding-multiple-entries-to-a-hashmap-at-once-in-one-statement
            put("Pepsi", 2);
            put("Sprite", 4);
            put("Coke Zero", 3);
        }};

        // When I serialize it
        String result = serializer.serialize(input);

        // Then it should look like a JSON
        assertThat(result, is("{\"Sprite\":4,\"Pepsi\":2,\"Coke Zero\":3}"));
    }
}