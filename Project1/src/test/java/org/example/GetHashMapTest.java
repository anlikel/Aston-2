package org.example;

import org.junit.Test;
import java.util.Map;
import static org.junit.Assert.*;

public class GetHashMapTest {

    @Test
    public void testEmptyHashMapSize(){
        Map<Integer,String> map=new MyHashMapImp<>();
        String a=map.get(44);
        assertEquals(a,null);
    }

    @Test
    public void testFullHashMapSize(){
        Map<Integer,String> map=new MyHashMapImp<>();
        map.put(1,"a");
        map.put(2,"b");
        map.put(3,"c");
        String a=map.get(3);
        assertEquals(a,"c");
    }

    @Test
    public void testDuplicateHashMapSize(){
        Map<Integer,String> map=new MyHashMapImp<>();
        map.put(1,"a");
        map.put(2,"b");
        map.put(2,"c");
        String a=map.get(2);
        assertEquals(a,"c");
    }
}
