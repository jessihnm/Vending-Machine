package serialization;

import java.util.Map;

public interface Deserializer {
    /**
     * @param input - a string to be parsed
     * @return a HashMap parse of items by name
     */
    Map<String, Integer> deserialize(String input) throws DeserializationError;
}
