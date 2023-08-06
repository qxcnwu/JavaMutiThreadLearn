package twophaseterminal;

/**
 * @author 邱星晨
 */
@FunctionalInterface
public interface CacheLoader<K, V> {
    V load(K k);
}
