package serialization;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Map;

public interface Serializer {
    /**
     *
     */
    String serialize(Map<String, Integer> data) throws SerializationError, JsonProcessingException;
}

