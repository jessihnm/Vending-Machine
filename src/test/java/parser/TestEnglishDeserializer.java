package parser;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;


public class TestEnglishDeserializer {
    @Test
    public void testInitialState() {
        //Given an instance of EnglishParser
        EnglishDeserializer parser = new EnglishDeserializer();
        //When I check the current state
        EnglishParsingState state = parser.getCurrentState();
        //Then the initial state should be ITEM_ID
        assertEquals(state, EnglishParsingState.ITEM_ID);
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
        assertEquals(EnglishParsingState.END, parser.getCurrentState());
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
        assertEquals(1, items.size());
        //And it should have an Pepsi
        assertEquals(items, new HashMap<String, Integer>()
        {{
            // https://stackoverflow.com/questions/8261075/adding-multiple-entries-to-a-hashmap-at-once-in-one-statement
            put("Pepsi", 3);
        }});
        //Then it should be in the state END
        assertEquals(EnglishParsingState.END, parser.getCurrentState());
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
        assertEquals(1, items.size());
        //And it should have a Sprite
        assertEquals(items, new HashMap<String, Integer>()
        {{
            // https://stackoverflow.com/questions/8261075/adding-multiple-entries-to-a-hashmap-at-once-in-one-statement
            put("Sprite", 2);
        }});
        //Then it should be in the state END
        assertEquals(EnglishParsingState.END, parser.getCurrentState());
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
        assertEquals(2, items.size());
        //And the pepsi id should be 2 and sprite id should be 4
        assertEquals(items, new HashMap<String, Integer>()
        {{
            // https://stackoverflow.com/questions/8261075/adding-multiple-entries-to-a-hashmap-at-once-in-one-statement
            put("Pepsi", 2);
            put("Sprite", 4);
        }});
        //Then it should be in the state END
        assertEquals(EnglishParsingState.END, parser.getCurrentState());
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
        assertEquals(3, items.size());
        //And their ids should match the ones from the hashtable below
        assertEquals(items, new HashMap<String, Integer>()
        {{
            // https://stackoverflow.com/questions/8261075/adding-multiple-entries-to-a-hashmap-at-once-in-one-statement
            put("Pepsi", 2);
            put("Sprite", 4);
            put("Fanta", 3);
        }});
        //Then it should be in the state END
        assertEquals(EnglishParsingState.END, parser.getCurrentState());
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
        assertEquals(3, items.size());
        //And their ids should match the ones from the hashtable below
        assertEquals(items, new HashMap<String, Integer>()
        {{
            // https://stackoverflow.com/questions/8261075/adding-multiple-entries-to-a-hashmap-at-once-in-one-statement
            put("Pepsi", 2);
            put("Sprite", 4);
            put("Coke Zero", 3);
        }});
        //Then it should be in the state END
        assertEquals(EnglishParsingState.END, parser.getCurrentState());
    }
    @Test(expected = EnglishDeserializationError.class)
    public void testInvalidDeserialization() throws EnglishDeserializationError {
        String data = ".,";
        //And and instance of EnglishItemsParser
        EnglishDeserializer parser = new EnglishDeserializer();
        //When I call parse
        parser.deserialize(data);
        // Then it should throw an exception
    }

}
