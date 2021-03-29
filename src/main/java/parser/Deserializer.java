package parser;

import java.util.Map;

public interface Deserializer {
    /**
     * @param input - a string to be parsed
     * @return a HashMap parse of items by name
     */
    public Map<String, Integer> deserialize(String input) throws EnglishDeserializationError;

    /**
     * @param character - feed state machine with character
     */
    public void parseNext(String character) throws EnglishDeserializationError;

    /**
     * @return a ParserState with the current state of the machine (used in unit tests)
     */
    public ParserState getCurrentState();
}
