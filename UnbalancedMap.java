package edu.sdsu.cs.datastructures;

public class UnbalancedMap<K extends Comparable<K>, V> implements IMap<K, V> {



    @Override
    public boolean contains(K key) {
        return false;
    }

    @Override
    public boolean add(K key, V value) {
        return false;
    }

    @Override
    public V delete(K key) {
        return null;
    }

    @Override
    public V getValue(K key) {
        return null;
    }

    @Override
    public K getKey(V value) {
        return null;
    }

    @Override
    public Iterable<K> getKeys(V value) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public Iterable<K> keyset() {
        return null;
    }

    @Override
    public Iterable<V> values() {
        return null;
    }
}
