import java.util.*;

public class EnglishItemsParser implements ItemsParser {

    private EnglishParsingState state = EnglishParsingState.ITEM_QTY;
    Map<String, Integer> items = new HashMap<>();
    List<String> buffer = new ArrayList<String>();
    Integer currentQty = null;

    @Override
    public Map<String, Integer> parse(String data) {
/*        this.state = EnglishParsingState.EXPECT_FULL_STOP;
        this.items.put("iPhone", 3);*/
        for(int i = 0; i<data.length(); i++) {
            this.parseNext(data.substring(i, i+1));
        }
        return items;
    }

    @Override
    public void parseNext(String character) {

        switch (this.state) {
            case ITEM_QTY:
                parseItemQuantity(character);
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
                this.state = EnglishParsingState.ITEM_QTY;
                break;
            default:
                this.state = EnglishParsingState.END;
                break;
        }
    }

    private void parseItemName(String character) {
        if (this.state != EnglishParsingState.ITEM_NAME) {
            throw new RuntimeException("Cannot parse item quantity because current state is not EXPECT_ITEM_NAME");
        }
        if (character.equals(".")) {
            this.state = EnglishParsingState.END;
            addItemFromBuffer();
            return;
        }
        if (character.matches("[A-Za-z]")){
            this.buffer.add(character);
        } else {
            addItemFromBuffer();
            this.state = EnglishParsingState.MORE_ITEMS;
        }
    }

    private void addItemFromBuffer() {
        String itemName = this.getBufferAsString();
        this.items.put(itemName, this.currentQty);
        this.currentQty = null;
        this.buffer.clear();
    }

    public String getBufferAsString() {
        String temp = "";

        for (String s : this.buffer) {
            temp += s;
        }
        return temp;
    }
    private void parseItemQuantity(String character) {
        if (this.state != EnglishParsingState.ITEM_QTY) {
            throw new RuntimeException("Cannot parse item quantity because current state is not EXPECT_ITEM_QTY");
        }
        if (character.matches("\\d")){
            this.buffer.add(character);
        } else if (character.matches("\\s")) {
            this.currentQty = Integer.parseInt(getBufferAsString());
            this.buffer.clear();
            this.state = EnglishParsingState.ITEM_NAME;
        }
    }

    public EnglishParsingState getCurrentState() {
        return this.state;
    }
}
