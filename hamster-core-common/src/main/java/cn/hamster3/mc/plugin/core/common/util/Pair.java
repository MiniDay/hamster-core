package cn.hamster3.mc.plugin.core.common.util;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Objects;

/**
 * <p>A convenience class to represent name-value pairs.</p>
 */
@SuppressWarnings({"unused", "ClassCanBeRecord"})
public class Pair<K, V> implements Serializable {
    /**
     * Key of this <code>Pair</code>.
     */
    @NotNull
    private final K key;

    /**
     * Value of this this <code>Pair</code>.
     */
    @NotNull
    private final V value;

    public Pair(@NotNull K key, @NotNull V value) {
        this.key = key;
        this.value = value;
    }

    @NotNull
    public K getKey() {
        return key;
    }

    @NotNull
    public V getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return key.equals(pair.key) && value.equals(pair.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }
}
