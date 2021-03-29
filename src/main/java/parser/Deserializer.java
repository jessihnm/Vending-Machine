package parser;

import java.util.Map;

public interface Deserializer {
    /**
     *
     * @return a HashMap parse of items by name
     */
    public Map<String, Integer> deserialize(String data);

    /**
     *
     * @param character
     */
    public void parseNext(String character);

    public ParserState getCurrentState();
}
