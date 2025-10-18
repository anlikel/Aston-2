package org.example;

import org.junit.Test;
import java.util.Map;
import java.util.Set;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class EntrySetHashMapTest {
    @Test
    public void testEmptyHashMapSize(){
        Map<Integer,String> map=new MyHashMapImp<>();
        Set<Map.Entry<Integer,String>> entries=map.entrySet();
        assertNotNull(map);
        assertEquals(0,entries.size());
    }

    @Test
    public void testFullHashMapSize(){
        Map<Integer,String> map=new MyHashMapImp<>();
        map.put(1,"a");
        map.put(2,"b");
        map.put(3,"c");
        Set<Map.Entry<Integer,String>> entries=map.entrySet();
        assertEquals(3,entries.size());
    }

    @Test
    public void testDuplicateHashMapSize(){
        Map<Integer,String> map=new MyHashMapImp<>();
        map.put(1,"a");
        map.put(2,"b");
        map.put(3,"b");
        Set<Map.Entry<Integer,String>> entries=map.entrySet();
        assertEquals(3,entries.size());
    }
}
