package serialization;

import java.util.Map;

public interface StateMachine {
    /**
     * @param character - feed state machine with character
     */
    void parseNext(String character) throws EnglishDeserializationError;

    /**
     * @return a ParserState with the current state of the machine (used in unit tests)
     */
    ParserState getCurrentState();
}
