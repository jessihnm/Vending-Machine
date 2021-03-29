package parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnglishDeserializer implements Deserializer {

    Map<String, Integer> items = new HashMap<>();
    List<String> buffer = new ArrayList<String>();
    Integer currentID = null;
    private EnglishParsingState state = EnglishParsingState.ITEM_ID;

    @Override
    public Map<String, Integer> deserialize(String input) throws EnglishDeserializationError {
        // For each character of the input string
        for (int i = 0; i < input.length(); i++) {
            // Run character by character in my state machine
            String character = input.substring(i, i + 1);
            this.parseNext(character);
        }
        return items;
    }

    @Override
    public void parseNext(String character) throws EnglishDeserializationError {
        // main method of my state machine
        switch (this.state) {
            case ITEM_ID:
                parseItemID(character);
                break;
            case ITEM_NAME:
                parseItemName(character);
                break;
            case MORE_ITEMS:
                parseMoreItems(character);
                break;
            default:
                break;
        }
    }

    private void parseMoreItems(String character) {
        switch (character) {
            case ",":
                this.state = EnglishParsingState.MORE_ITEMS;
                break;
            case " ":
                this.state = EnglishParsingState.ITEM_ID;
                break;
            default:
                this.state = EnglishParsingState.END;
                break;
        }
    }

    private void parseItemName(String character) {
        if (character.equals(".")) {
            this.state = EnglishParsingState.END;
            addItemFromBuffer();
            return;
        }
        if (character.matches("[A-Za-z ]")) {
            this.buffer.add(character);
        } else {
            addItemFromBuffer();
            this.state = EnglishParsingState.MORE_ITEMS;
        }
    }

    private void addItemFromBuffer() {
        String itemName = this.getBufferAsString();
        this.items.put(itemName, this.currentID);
        this.currentID = null;
        this.buffer.clear();
    }

    public String getBufferAsString() {
        String temp = "";

        for (String s : this.buffer) {
            temp += s;
        }
        return temp;
    }

    private void parseItemID(String character) throws EnglishDeserializationError {
        if (character.matches("\\d")) {
            this.buffer.add(character);
        } else if (character.matches("\\s")) {
            this.currentID = Integer.parseInt(getBufferAsString());
            this.buffer.clear();
            this.state = EnglishParsingState.ITEM_NAME;
        } else {
            throw new EnglishDeserializationError("Invalid character '" + character + "' while parsing item id");
        }
    }

    public EnglishParsingState getCurrentState() {
        return this.state;
    }
}
