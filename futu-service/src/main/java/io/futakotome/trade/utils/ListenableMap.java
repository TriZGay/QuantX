package io.futakotome.trade.utils;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class ListenableMap<K, V> implements Map<K, V> {
    private final Map<K, V> map;

    private final Consumer<ListenableMap<K, V>> onModified;

    public ListenableMap(Builder<K, V> builder) {
        map = builder.map;
        onModified = builder.onModified;
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return map.get(key);
    }

    @Override
    public V put(K key, V value) {
        V old = map.put(key, value);
        if (onModified != null) {
            onModified.accept(this);
        }
        return old;
    }

    @Override
    public V remove(Object key) {
        V old = map.remove(key);
        if (onModified != null) {
            onModified.accept(this);
        }
        return old;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        map.putAll(m);
        if (onModified != null) {
            onModified.accept(this);
        }
    }

    @Override
    public void clear() {
        map.clear();
        if (onModified != null) {
            onModified.accept(this);
        }
    }

    @Override
    public Set<K> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<V> values() {
        return map.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return map.entrySet();
    }

    public static final class Builder<K, V> {
        private Map<K, V> map;
        private Consumer<ListenableMap<K, V>> onModified;

        public Builder() {
        }

        public Builder<K, V> map(Map<K, V> val) {
            map = val;
            return this;
        }

        public Builder<K, V> onModified(Consumer<ListenableMap<K, V>> val) {
            onModified = val;
            return this;
        }

        public ListenableMap<K, V> build() {
            return new ListenableMap<>(this);
        }
    }
}
