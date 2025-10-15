package org.example;

import org.junit.Test;
import java.util.Map;
import static org.junit.Assert.*;

public class PutHashMapTest {

    @Test
    public void testEmptyHashMapSize(){
        Map<Integer,String> map=new MyHashMapImp<>();
        assertNotNull(map);
        assertEquals(0,map.size());
    }

    @Test
    public void testFullHashMapSize(){
        Map<Integer,String> map=new MyHashMapImp<>();
        assertNotNull(map);
        String a=map.put(1,"a");
        String b=map.put(2,"b");
        String c=map.put(3,"c");
        assertNotNull(map);
        assertEquals(3,map.size());
        assertEquals(a,null);
        assertEquals(b,null);
        assertEquals(b,null);
    }

    @Test
    public void testDuplicateHashMapSize(){
        Map<Integer,String> map=new MyHashMapImp<>();
        assertNotNull(map);
        String a=map.put(1,"a");
        String b=map.put(1,"b");
        String c=map.put(2,"c");
        assertNotNull(map);
        assertEquals(2,map.size());
        assertEquals(a,null);
        assertEquals(b,"a");
        assertEquals(c,null);
    }
}
