package serialization;


import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestEnglishDeserializer {
    @Test
    public void testInitialState() {
        //Given an instance of EnglishParser
        EnglishDeserializer parser = new EnglishDeserializer();
        //When I check the current state
        ParserState state = parser.getCurrentState();
        //Then the initial state should be ITEM_ID
        assertThat(state, is(EnglishParsingState.ITEM_ID));
    }

    @Test
    public void parseSingleItemChangesState() throws EnglishDeserializationError {
        //Given the String "1 Pepsi."
        String data = "1 Pepsi.";
        //And and instance of EnglishItemsParser
        EnglishDeserializer parser = new EnglishDeserializer();
        //When I call parse
        parser.deserialize(data);
        //Then it should be in the state END
        assertThat(EnglishParsingState.END, is(parser.getCurrentState()));
    }

    @Test
    public void parseSingleItemReturnsHashMapWithItem() throws EnglishDeserializationError {
        //Given the String "3 Pepsi."
        String data = "3 Pepsi.";
        //And and instance of EnglishItemsParser
        EnglishDeserializer parser = new EnglishDeserializer();
        //When I call parse
        Map<String, Integer> items = parser.deserialize(data);
        //Then it should have one item
        assertThat(1, is(items.size()));
        //And it should have an Pepsi
        assertThat(new HashMap<String, Integer>() {{
            // https://stackoverflow.com/questions/8261075/adding-multiple-entries-to-a-hashmap-at-once-in-one-statement
            put("Pepsi", 3);
        }}, is(items));
        //Then it should be in the state END
        assertThat(EnglishParsingState.END, is(parser.getCurrentState()));
    }

    @Test
    public void parseSingleItemVariation2() throws EnglishDeserializationError {
        //Given the String "2 Sprite."
        String data = "2 Sprite.";
        //And and instance of EnglishItemsParser
        EnglishDeserializer parser = new EnglishDeserializer();
        //When I call parse
        Map<String, Integer> items = parser.deserialize(data);
        //Then it should have one item
        assertThat(items.size(), is(1));
        //And it should have a Sprite
        assertThat(new HashMap<String, Integer>() {{
            // https://stackoverflow.com/questions/8261075/adding-multiple-entries-to-a-hashmap-at-once-in-one-statement
            put("Sprite", 2);
        }}, is(items));
        //Then it should be in the state END
        assertThat(parser.getCurrentState(), is(EnglishParsingState.END));
    }

    @Test
    public void parseTwoItemsReturnHashmapWithThem() throws EnglishDeserializationError {
        //Given a string with 2 items (comma separated) "2 Pepsi, 4 Sprite."
        String data = "2 Pepsi, 4 Sprite.";
        //And and instance of EnglishItemsParser
        EnglishDeserializer parser = new EnglishDeserializer();
        //When I call parse
        Map<String, Integer> items = parser.deserialize(data);
        //Then it should have two items
        assertThat(items.size(), is(2));
        //And the pepsi id should be 2 and sprite id should be 4
        assertThat(new HashMap<String, Integer>() {{
            // https://stackoverflow.com/questions/8261075/adding-multiple-entries-to-a-hashmap-at-once-in-one-statement
            put("Pepsi", 2);
            put("Sprite", 4);
        }}, is(items));
        //Then it should be in the state END
        assertThat(parser.getCurrentState(), is(EnglishParsingState.END));
    }

    @Test
    public void parseThreeItemsReturnHashmapWithThem() throws EnglishDeserializationError {
        //Given a string with 3 items "2 Pepsi, 4 Sprite, 3 Fanta."
        String data = "2 Pepsi, 4 Sprite, 3 Fanta.";
        //And and instance of EnglishItemsParser
        EnglishDeserializer parser = new EnglishDeserializer();
        //When I call parse
        Map<String, Integer> items = parser.deserialize(data);
        //Then it should have three items
        assertThat(items.size(), is(3));
        //And their ids should match the ones from the hashtable below
        assertThat(new HashMap<String, Integer>() {{
            // https://stackoverflow.com/questions/8261075/adding-multiple-entries-to-a-hashmap-at-once-in-one-statement
            put("Pepsi", 2);
            put("Sprite", 4);
            put("Fanta", 3);
        }}, is(items));
        //Then it should be in the state END
        assertThat(parser.getCurrentState(), is(EnglishParsingState.END));
    }

    @Test
    public void parseItemWithSpaceInTheNameShouldWork() throws EnglishDeserializationError {
        //Given a string with 3 items "2 Pepsi, 4 Sprite, 3 Fanta."
        String data = "2 Pepsi, 4 Sprite, 3 Coke Zero.";
        //And and instance of EnglishItemsParser
        EnglishDeserializer parser = new EnglishDeserializer();
        //When I call parse
        Map<String, Integer> items = parser.deserialize(data);
        //Then it should have three items
        assertThat(items.size(), is(3));
        //And their ids should match the ones from the hashtable below
        assertThat(new HashMap<String, Integer>() {{
            // https://stackoverflow.com/questions/8261075/adding-multiple-entries-to-a-hashmap-at-once-in-one-statement
            put("Pepsi", 2);
            put("Sprite", 4);
            put("Coke Zero", 3);
        }}, is(items));
        //Then it should be in the state END
        assertThat(parser.getCurrentState(), is(EnglishParsingState.END));
    }

    @Test
    public void testInvalidDeserialization() {
        String data = ".,";
        //And and instance of EnglishItemsParser
        EnglishDeserializer parser = new EnglishDeserializer();
        //When I call parse the it should throw an exception
        assertThrows(EnglishDeserializationError.class, () -> parser.deserialize(data));
    }

}
