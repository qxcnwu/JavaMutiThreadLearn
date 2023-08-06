package twophaseterminal;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class SoftLRUCache<K,V> {
    private final LinkedList<K> linkedList=new LinkedList<>();
    private final Map<K, SoftReference<V>> cache=new HashMap<>();
    private final Integer capacity;
    private final CacheLoader<K,V> cacheLoader;

    public SoftLRUCache(Integer capacity, CacheLoader<K, V> cacheLoader) {
        this.capacity = capacity;
        this.cacheLoader = cacheLoader;
    }

    public void put(K k,V v){
        if(linkedList.size()>=capacity){
            K eldestKey=linkedList.removeFirst();
            cache.remove(eldestKey);
        }
        linkedList.remove(k);
        linkedList.addLast(k);
        cache.put(k,new SoftReference<>(v));
    }

    public V get(K k){
        V value;
        final boolean remove = linkedList.remove(k);
        if(!remove){
            value= cacheLoader.load(k);
            this.put(k,value);
        }else{
            value= cache.get(k).get();
            linkedList.addLast(k);
        }
        return value;
    }

    @Override
    public String toString() {
        return "LRUChanel{" +
                "linkedList=" + linkedList +
                ", cache=" + cache +
                ", capacity=" + capacity +
                ", cacheLoader=" + cacheLoader +
                '}';
    }
}
