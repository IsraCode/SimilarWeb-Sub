package cache;

import java.util.HashMap;
import java.util.Map;

public class CacheImp<K, V> implements Cache<K, V> {

    private final Map<K, V> cache = new HashMap<>();

    @Override
    public V computeIfAbsent(K key, java.util.function.Function<? super K, ? extends V> mappingFunction) {
        V value = cache.get(key);
        if (value == null) {
            value = mappingFunction.apply(key);
            cache.put(key, value);
        }
        return value;
    }

    @Override
    public V get(K key) {
        return cache.get(key);
    }

    @Override
    public void put(K key, V value) {
        cache.put(key, value);
    }

}

