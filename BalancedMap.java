package edu.sdsu.cs.datastructures;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class BalancedMap<K extends Comparable<K>, V> implements IMap<K, V> {
    TreeMap<K, V> tMap = new TreeMap<>();

    public BalancedMap() {

    }

    public BalancedMap(IMap source) {
        for (Object key : source.keyset()) {
            tMap.put((K) key, (V) source.getValue((K) key));
        }
    }

    @Override
    public boolean contains(K key) {
        return tMap.containsKey(key);
    }

    @Override
    public boolean add(K key, V value) {
        if (tMap.containsKey(key))
            return false;

        tMap.put(key, value);
        return true;
    }

    @Override
    public V delete(K key) {
        return tMap.remove(key);
    }

    @Override
    public V getValue(K key) {
        return tMap.get(key);
    }

    /**
     * Returns a key in the map associated with the provided value.
     *
     * @param value The value to find within the map.
     * @return The first key found associated with the indicated value.
     */
    @Override
    public K getKey(V value) {
        for (K aKey : tMap.keySet()) {
            if (tMap.get(aKey).equals(value))
                return aKey;
        }
        return null;
    }


    /**
     * Returns all keys associated with the indicated value contained within the
     * map.
     *
     * @param value The value to locate within the map.
     * @return An iterable object containing all keys associated with the
     * provided value.
     */

    @Override
    public Iterable<K> getKeys(V value) {
        List<K> listOfMatchedKeys = new ArrayList<>();
        for (K aKey : tMap.keySet()) {
            if (tMap.get(aKey).equals(value))
                listOfMatchedKeys.add(aKey);
        }
        return listOfMatchedKeys;
    }

    @Override
    public int size() {
        return tMap.size();
    }

    @Override
    public boolean isEmpty() {
        return tMap.isEmpty();
    }

    @Override
    public void clear() {
        tMap.clear();
        return;
    }

    @Override
    public Iterable<K> keyset() {
        return tMap.keySet();
    }

    @Override
    public Iterable<V> values() {
        return tMap.values();
    }
}
