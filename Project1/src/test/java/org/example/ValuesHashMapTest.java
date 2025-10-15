package org.example;

import org.junit.Test;

import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ValuesHashMapTest {
    @Test
    public void testEmptyHashMapSize(){
        Map<Integer,String> map=new MyHashMapImp<>();
        Set<Integer> values=(Set)map.values();
        assertNotNull(values);
        assertEquals(0,values.size());
    }

    @Test
    public void testFullHashMapSize(){
        Map<Integer,String> map=new MyHashMapImp<>();
        map.put(1,"a");
        map.put(2,"b");
        map.put(3,"c");
        Set<Integer> values=(Set)map.values();
        assertNotNull(values);
        assertEquals(3,values.size());

    }

    @Test
    public void testDuplicateHashMapSize(){
        Map<Integer,String> map=new MyHashMapImp<>();
        map.put(1,"a");
        map.put(2,"b");
        map.put(3,"b");
        Set<Integer> values=(Set)map.values();
        assertNotNull(values);
        assertEquals(2,values.size());
    }
}
