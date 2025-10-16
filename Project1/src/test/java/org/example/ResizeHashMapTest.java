package org.example;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ResizeHashMapTest {
    @Test
    public void testResizePlusHashMap(){
        MyHashMapImp<Integer,String> map=new MyHashMapImp<>();
        for(int i=1;i<=20;i++){
            Integer key=i;
            String value="value_"+i;
            map.put(key,value);
        }
        assertEquals(20,map.size());
        boolean result=map.containsValue("value_19");
        assertEquals(result,true);
        int tableSize=map.getTableSize();
        assertEquals(36,tableSize);
    }
}
