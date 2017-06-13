package org.alienideology.jcord.util;

import com.sun.istack.internal.Nullable;
import org.alienideology.jcord.object.SnowFlake;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.function.Consumer;

/**
 * Cache - Cache objects into a customized list.
 * @author AlienIdeology
 */
public class Cache <T extends SnowFlake> {

    private List<CacheNode> caches = new ArrayList<>();

    public Cache() {
    }

    public T get(int i) {
        return caches.get(i).element;
    }

    @Nullable
    public T get(String id) {
        for (CacheNode t : caches) {
            if (t.element.getId().equals(id))
                return t.element;
        }
        return null;
    }

    public List<T> getAllCached(String id) {
        List<T> allCached = new ArrayList<>();
        for (CacheNode cache : caches) {
            if (cache.element.getId().equals(id))
                allCached.add(cache.element);
        }
        return allCached;
    }

    public Cache<T> cache(T t) {
        caches.add(new CacheNode(t));
        return this;
    }

    public Cache<T> uniqueCache(T t) {
        for (CacheNode cacheNode : caches) {
            if (cacheNode.element.getId().equals(t.getId())) {
                caches.remove(cacheNode);
            }
        }
        caches.add(new CacheNode(t));
        return this;
    }

    public Cache<T> cache(int index, T t) {
        caches.add(index, new CacheNode(t));
        return this;
    }

    public Cache<T> cacheFirst(T t) {
        cache(0, t);
        return this;
    }

    public Cache<T> cacheAll(T... t) {
        for (T e : t) {
            caches.add(new CacheNode(e));
        }
        return this;
    }

    public Cache<T> remove(T t) {
        for (CacheNode cacheNode : caches) {
            if (cacheNode.element.getId().equals(t.getId())) {
                caches.remove(cacheNode);
                break;
            }
        }
        return this;
    }

    public Cache<T> clear() {
        caches.clear();
        return this;
    }

    public boolean contains(T t) {
        return get(t.getId()) != null;
    }

    public boolean isCacheUnique(T t) {
        return caches.stream().filter(cache -> cache.element.getId().equals(t.getId())).count() > 1;
    }

    public List<T> toList() {
        List<T> list = new ArrayList<>();
        for (CacheNode node: caches) {
            list.add(node.element);
        }
        return list;
    }

    public int size() {
        return caches.size();
    }

    public boolean isEmpty() {
        return caches.isEmpty();
    }

    public void forEach(Consumer<? super CacheNode> action) {
        caches.forEach(action);
    }

    public Spliterator<CacheNode> spliterator() {
        return caches.spliterator();
    }

    public Iterator<CacheNode> iterator() {
        return caches.iterator();
    }

    class CacheNode {
        T element;
        OffsetDateTime time;

        CacheNode(T element) {
            this.element = element;
            this.time = OffsetDateTime.now();
        }

        public boolean isOlder(CacheNode node) {
            return time.isAfter(node.time);
        }

    }
}
