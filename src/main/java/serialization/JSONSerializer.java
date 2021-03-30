package serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class JSONSerializer implements Serializer {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String serialize(Map<String, Integer> data) throws SerializationError {
        try {
            String jsonResult = mapper.writeValueAsString(data);
            return jsonResult;
        } catch (JsonProcessingException e) {
            throw new SerializationError("JsonProcessingException: " + e.toString());
        }
    }
}