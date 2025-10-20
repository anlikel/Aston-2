package org.example;

import org.junit.Test;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class KeysHashMapTest {
    @Test
    public void testEmptyHashMapSize(){
        Map<Integer,String> map=new MyHashMapImp<>();
        Set<Integer> keys=map.keySet();
        assertNotNull(keys);
        assertEquals(0,keys.size());
    }

    @Test
    public void testFullHashMapSize(){
        Map<Integer,String> map=new MyHashMapImp<>();
        map.put(1,"a");
        map.put(2,"b");
        map.put(3,"c");
        Set<Integer> keys=map.keySet();
        assertNotNull(keys);
        assertEquals(3,keys.size());
    }

    @Test
    public void testDuplicateHashMapSize(){
        Map<Integer,String> map=new MyHashMapImp<>();
        map.put(1,"a");
        map.put(2,"b");
        map.put(2,"c");
        Set<Integer> keys=map.keySet();
        assertNotNull(keys);
        assertEquals(2,keys.size());
    }
}
