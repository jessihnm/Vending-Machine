package parser;

import java.util.Map;

public interface ItemsParser {
    /**
     *
     * @return a HashMap parse of items by name
     */
    public Map<String, Integer> parse(String data);

    /**
     *
     * @param character
     */
    public void parseNext(String character);

    public ParserState getCurrentState();
}
