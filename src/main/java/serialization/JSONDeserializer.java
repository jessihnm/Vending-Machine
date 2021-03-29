package serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class JSONDeserializer implements Deserializer {
    private final ObjectMapper mapper;

    public JSONDeserializer() {
        this.mapper = new ObjectMapper();
    }

    @Override
    public Map<String, Integer> deserialize(String data) throws DeserializationError {
        TypeReference<HashMap<String, Integer>> typeRef
                = new TypeReference<HashMap<String, Integer>>() {
        };
        try {
            return mapper.readValue(data, typeRef);
        } catch (JsonProcessingException e) {
            throw new DeserializationError(e.toString());
        }
    }

    @Override
    public void parseNext(String character) {

    }

    @Override
    public ParserState getCurrentState() {
        return JSONParserState.NOOP;
    }
}

