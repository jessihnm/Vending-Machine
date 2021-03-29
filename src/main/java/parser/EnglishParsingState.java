package parser;

public enum EnglishParsingState implements ParserState {
    ITEM_ID,
    ITEM_NAME,
    MORE_ITEMS,
    END,
}
