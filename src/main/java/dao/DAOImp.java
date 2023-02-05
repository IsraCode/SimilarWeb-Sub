package dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class DAOImp<K, V> implements DAO<K, V> {
    final private HashMap<K, V> DAO = new HashMap<>();

    public V get(K key) {
        return DAO.get(key);
    }

    public Set<K> getKeys() {
        return DAO.keySet();
    }

    public Collection<V> getValues() {
        return DAO.values();
    }

    public V put(K key, V value) {
        return DAO.put(key, value);
    }

    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        return DAO.computeIfAbsent(key, mappingFunction);
    }

    public Set<Map.Entry<K, V>> getEntries() {
        return DAO.entrySet();
    }
}
