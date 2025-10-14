package org.example;

import java.util.*;

public class MyHashMapImp<K,V> implements Map<K,V> {
    private static final int DEFAULT_CAPACITY=16;
    private int capacity;
    private double loadFactor=0.8;
    private int size;
    private Node [] table;

    public MyHashMapImp(){
        table=new Node[DEFAULT_CAPACITY];
        size=0;
        capacity=DEFAULT_CAPACITY;
    }

    private static class Node<V>{
        V value;
        Node next;

        Node(V value){
            this.value=value;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return (size==0);
    }

    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public V get(Object key) {
        return null;
    }

    @Override
    public V put(K key, V value) {
        int hash=getHashCode(key);
        int index=getIndex(hash);
        return null;
    }

    @Override
    public V remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<K> keySet() {
        return Set.of();
    }

    @Override
    public Collection<V> values() {
        return List.of();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return Set.of();
    }

    //дополнительные утильные методы для работы мапы

    private <K> int getHashCode(K key){
        return Objects.hashCode(key);
    }

    private int getIndex(int hash){
        return hash & (capacity-1);
    }
}
