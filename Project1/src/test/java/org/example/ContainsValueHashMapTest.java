package org.example;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ContainsValueHashMapTest {
    @Test
    public void testEmptyHashMapSize(){
        Map<Integer,String> map=new MyHashMapImp<>();
        boolean result=map.containsValue("a");
        assertEquals(result,false);
    }

    @Test
    public void testFullHashMapSize(){
        Map<Integer,String> map=new MyHashMapImp<>();
        map.put(1,"a");
        map.put(2,"b");
        map.put(3,"c");
        boolean result1=map.containsValue("a");
        assertEquals(result1,true);
        boolean result2=map.containsValue("asd");
        assertEquals(result2,false);
    }

    @Test
    public void testDuplicateHashMapSize(){
        Map<Integer,String> map=new MyHashMapImp<>();
        map.put(1,"a");
        map.put(2,"b");
        map.put(2,"c");
        boolean result1=map.containsValue("b");
        assertEquals(result1,false);
        boolean result2=map.containsValue("c");
        assertEquals(result2,true);
    }
    @Test
    public void testHashMapWithNullValue(){
        Map<Integer,String> map=new MyHashMapImp<>();
        map.put(1,"a");
        map.put(2,null);
        map.put(3,"c");
        boolean result1=map.containsValue(null);
        assertEquals(result1,true);
    }
}
