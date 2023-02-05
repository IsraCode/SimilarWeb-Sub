package cache;

interface Cache<K, V> {
    V computeIfAbsent(K key, java.util.function.Function<? super K, ? extends V> mappingFunction);

    V get(K key);

    void put(K key, V value);
}
