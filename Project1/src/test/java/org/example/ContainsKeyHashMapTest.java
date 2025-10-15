package org.example;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ContainsKeyHashMapTest {
    @Test
    public void testEmptyHashMapSize(){
        Map<Integer,String> map=new MyHashMapImp<>();
        boolean result=map.containsKey(1);
        assertEquals(result,false);
    }

    @Test
    public void testFullHashMapSize(){
        Map<Integer,String> map=new MyHashMapImp<>();
        map.put(1,"a");
        map.put(2,"b");
        map.put(3,"c");
        boolean result1=map.containsKey(1);
        assertEquals(result1,true);
        boolean result2=map.containsKey(5);
        assertEquals(result2,false);
    }

    @Test
    public void testDuplicateHashMapSize(){
        Map<Integer,String> map=new MyHashMapImp<>();
        map.put(1,"a");
        map.put(2,"b");
        map.put(2,"c");
        boolean result1=map.containsKey(2);
        assertEquals(result1,true);
    }
    @Test
    public void testHashMapWithNullKey(){
        Map<Integer,String> map=new MyHashMapImp<>();
        map.put(1,"a");
        map.put(null,"b");
        map.put(2,"c");
        boolean result1=map.containsKey(null);
        assertEquals(result1,true);
    }
}
