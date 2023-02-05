package dao;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public interface DAO<K, V> {

    V get(K key);

    V put(K key, V value);

    Set<K> getKeys();

    Collection<V> getValues();

    default V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        V v;
        return (((v = get(key)) == null) && (v = mappingFunction.apply(key)) != null) ? put(key, v) : v;
    }

    Set<Map.Entry<K, V>> getEntries();

}
