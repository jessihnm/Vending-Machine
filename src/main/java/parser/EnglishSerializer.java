package parser;

import java.util.Map;

public class EnglishSerializer implements Serializer {
    @Override
    public String serialize(Map<String, Integer> data) {
        String products = "";
        int counter = 0;

        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            counter++;
            String end = ", ";
            if (counter == data.size()) {
                end = ".";
            }
            products += entry.getValue() + " " + entry.getKey() + end;
        }

        return products;
    }
}
