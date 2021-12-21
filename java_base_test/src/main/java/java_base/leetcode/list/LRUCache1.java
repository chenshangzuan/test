package java_base.leetcode.list;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author Kled
 * @date 2021/12/15 3:14 PM
 */
public class LRUCache1 {
    LinkedList<Integer> lru = new LinkedList<>();
    Map<Integer, Integer> cache = new HashMap<>();
    int capacity;

    public LRUCache1(int capacity) {
        this.capacity = capacity;
    }

    public int get(int key) {
        Integer val = cache.get(key);
        if (val == null) {
            return -1;
        } else {
            lru.remove(Integer.valueOf(key));
            lru.addFirst(key);
            return val;
        }
    }

    public void put(int key, int value) {
        int val = get(key);
        if (val == -1) {
            if (lru.size() == capacity) {
                Integer eliminatedKey = lru.removeLast();
                cache.remove(eliminatedKey);
            }
            lru.addFirst(key);
        }
        //更新缓存值
        cache.put(key, value);
    }

    public static void main(String[] args) {
        LRUCache1 lruCache1 = new LRUCache1(2);
        lruCache1.put(1, 1);
        lruCache1.put(2, 2);
        lruCache1.get(1);
        lruCache1.put(3, 3);
        lruCache1.get(2);
    }
}
