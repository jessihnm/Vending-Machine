package parser;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class TestEnglishSerializationIntegration {
    @Test
    public void serializationWorks() throws EnglishDeserializationError {
        //Given I have a HashMap with one sprite whose id is 4 and a coke whose id is 6 and Coke Zero whose id is 2
        Map<String, Integer> products = new HashMap<String, Integer>() {{
            put("Sprite", 4);
            put("Coke", 6);
            put("Coke Zero", 2);
        }};

        //And an instance of EnglishSerializer
        EnglishSerializer serializer = new EnglishSerializer();
        //And an instance of EnglishDeserializer
        EnglishDeserializer deserializer = new EnglishDeserializer();

        //When I serialize the HashMap
        String items = serializer.serialize(products);
        //Then it should return a String "4 Sprite, 6 Coke, 2 Coke Zero."
        Assert.assertEquals("4 Sprite, 6 Coke, 2 Coke Zero.", items);
        //And when I deserialize the String
        Map<String, Integer> result = deserializer.deserialize(items);
        //Then it should match the inital HashMap
        Assert.assertEquals(products, result);
    }
}
