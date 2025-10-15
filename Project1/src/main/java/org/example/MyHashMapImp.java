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

    private static class Node<K,V>{
        int hash;
        K key;
        V value;
        Node next;

        Node(int hash,K key,V value, Node next){
            this.hash=hash;
            this.key=key;
            this.value=value;
            this.next=next;
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
        V resVal=null;

        if(table[index]==null){
            Node <K,V>newNode=new Node(hash,key,value,null);
            table[index]=newNode;
        }
        else {
            resVal=addNode(index,hash,key,value);
        }
        return resVal;
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

    private V addNode(int index,int hash,K key,V value){
        V resVal=null;
        Node<K, V> currentNode = table[index];
        Node<K, V> lastNode = null;

        while (currentNode != null) {
            if (currentNode.hash == hash &&
                    (currentNode.key == key || (key != null && key.equals(currentNode.key)))) {
                resVal = currentNode.value;
                currentNode.value = value;
                break;
            }
            lastNode = currentNode;
            currentNode = currentNode.next;
        }
        if(lastNode.next==null && resVal==null) {
            lastNode.next = new Node<>(hash, key, value, null);
        }
        return resVal;
    }
}
