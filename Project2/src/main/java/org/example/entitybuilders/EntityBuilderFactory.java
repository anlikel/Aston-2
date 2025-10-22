package org.example.entitybuilders;

import org.example.commands.*;

import java.util.HashMap;

public class EntityBuilderFactory {
    private static HashMap<ClassTag, EntityBuilder> map;

    private EntityBuilderFactory(){}

    public static EntityBuilder getBuilder(ClassTag tag) {
        if(map==null)
        {
            initBuilderMap();
        }
        return map.get(tag);
    }
    private static void initBuilderMap(){
        map=new HashMap<>();
        map.put(ClassTag.USER,new UserBuilder());
    }
}
