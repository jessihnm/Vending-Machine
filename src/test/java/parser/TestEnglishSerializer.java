package parser;


import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestEnglishSerializer {
    @Test
    public void hashMapWithSingleItemReturnsString() {
        //Given I have a HashMap with one pepsi whose id is 1
        Map<String, Integer> products = new HashMap<>();
        products.put("Pepsi", 1);
        //And an instance of EnglishSerializer
        EnglishSerializer serializer = new EnglishSerializer();
        //When I serialize the HashMap
        String items = serializer.serialize(products);
        //Then it should return a String "1 Pepsi."
        assertEquals("1 Pepsi.", items);
    }

    @Test
    public void hashMapWithSingleItemReturnsString1() {
        //Given I have a HashMap with one pepsi whose id is 3
        Map<String, Integer> products = new HashMap<>();
        products.put("Pepsi", 3);
        //And an instance of EnglishSerializer
        EnglishSerializer serializer = new EnglishSerializer();
        //When I serialize the HashMap
        String items = serializer.serialize(products);
        //Then it should return a String "3 Pepsi."
        assertEquals("3 Pepsi.", items);
    }

    @Test
    public void hashMapWithSingleItemReturnsString2() {
        //Given I have a HashMap with one sprite whose id is 4
        Map<String, Integer> products = new HashMap<>();
        products.put("Sprite", 4);
        //And an instance of EnglishSerializer
        EnglishSerializer serializer = new EnglishSerializer();
        //When I serialize the HashMap
        String items = serializer.serialize(products);
        //Then it should return a String "4 Sprite."
        assertEquals("4 Sprite.", items);
    }

    @Test
    public void hashMapWithTwoItemaReturnsString() {
        //Given I have a HashMap with one sprite whose id is 4
        Map<String, Integer> products = new HashMap<String, Integer>() {{
            put("Sprite", 4);
            put("Coke", 6);
        }};
        //And an instance of EnglishSerializer
        EnglishSerializer serializer = new EnglishSerializer();
        //When I serialize the HashMap
        String items = serializer.serialize(products);
        //Then it should return a String "4 Sprite, 6 Coke."
        assertEquals("4 Sprite, 6 Coke.", items);
    }

    @Test
    public void hashMapWithThreeItemaReturnsString() {
        //Given I have a HashMap with one sprite whose id is 4 and a coke whose id is 6 and Coke Zero whose id is 2
        Map<String, Integer> products = new HashMap<String, Integer>() {{
            put("Sprite", 4);
            put("Coke", 6);
            put("Coke Zero", 2);
        }};
        //And an instance of EnglishSerializer
        EnglishSerializer serializer = new EnglishSerializer();
        //When I serialize the HashMap
        String items = serializer.serialize(products);
        //Then it should return a String "4 Sprite, 6 Coke, 2 Coke Zero."
        assertEquals("4 Sprite, 6 Coke, 2 Coke Zero.", items);
    }

}
