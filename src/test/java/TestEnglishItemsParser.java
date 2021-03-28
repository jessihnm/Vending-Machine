import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;


public class TestEnglishItemsParser {
    @Test
    public void testInitialState() {
        //Given an instance of EnglishParser
        EnglishItemsParser parser = new EnglishItemsParser();
        //When I check the current state
        EnglishParsingState state = parser.getCurrentState();
        //Then the initial state should be ITEM_QTY
        assertEquals(state, EnglishParsingState.ITEM_QTY);
    }

    @Test
    public void parseSingleItemChangesState() {
        //Given the String "1 iPhone."
        String data = "1 iPhone.";
        //And and instance of EnglishItemsParser
        EnglishItemsParser parser = new EnglishItemsParser();
        //When I call parse
        parser.parse(data);
        //Then it should be in the state END
        assertEquals(EnglishParsingState.END, parser.getCurrentState());
    }

    @Test
    public void parseSingleItemReturnsHashMapWithItem() {
        //Given the String "3 iPhone."
        String data = "3 iPhone.";
        //And and instance of EnglishItemsParser
        EnglishItemsParser parser = new EnglishItemsParser();
        //When I call parse
        Map<String, Integer> items = parser.parse(data);
        //Then it should have one item
        assertEquals(1, items.size());
        //And it should have an iPhone
        assertEquals(items, new HashMap<String, Integer>()
        {{
            // https://stackoverflow.com/questions/8261075/adding-multiple-entries-to-a-hashmap-at-once-in-one-statement
            put("iPhone", 3);
        }});
        //Then it should be in the state END
        assertEquals(EnglishParsingState.END, parser.getCurrentState());
    }
    @Test
    public void parseSingleItemVariation2() {
        //Given the String "2 Samsung."
        String data = "2 Samsung.";
        //And and instance of EnglishItemsParser
        EnglishItemsParser parser = new EnglishItemsParser();
        //When I call parse
        Map<String, Integer> items = parser.parse(data);
        //Then it should have one item
        assertEquals(1, items.size());
        //And it should have a Samsung
        assertEquals(items, new HashMap<String, Integer>()
        {{
            // https://stackoverflow.com/questions/8261075/adding-multiple-entries-to-a-hashmap-at-once-in-one-statement
            put("Samsung", 2);
        }});
        //Then it should be in the state END
        assertEquals(EnglishParsingState.END, parser.getCurrentState());
    }
    @Test
    public void parseTwoItemsReturnHashmapWithThem() {
        //Given a string with 2 items (comma separated) "2 iPhone, 4 Samsung."
        String data = "2 iPhone, 4 Samsung.";
        //And and instance of EnglishItemsParser
        EnglishItemsParser parser = new EnglishItemsParser();
        //When I call parse
        Map<String, Integer> items = parser.parse(data);
        //Then it should have two items
        assertEquals(2, items.size());
        //And it should have 2 iphones and 4 samsungs
        assertEquals(items, new HashMap<String, Integer>()
        {{
            // https://stackoverflow.com/questions/8261075/adding-multiple-entries-to-a-hashmap-at-once-in-one-statement
            put("iPhone", 2);
            put("Samsung", 4);
        }});
        //Then it should be in the state END
        assertEquals(EnglishParsingState.END, parser.getCurrentState());
    }

}
