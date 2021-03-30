package serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class JSONDeserializer implements Deserializer {
    private final ObjectMapper mapper;
    TypeReference<HashMap<String, Integer>> hashmapOfIntegerByString
            = new TypeReference<HashMap<String, Integer>>() {
    };

    public JSONDeserializer() {
        this.mapper = new ObjectMapper();
    }

    @Override
    public Map<String, Integer> deserialize(String data) throws DeserializationError {
        try {
            return mapper.readValue(data, hashmapOfIntegerByString);
        } catch (JsonProcessingException e) {
            throw new DeserializationError(e.toString());
        }
    }
}

