package java_base.leetcode.list;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Kled
 * @date 2021/12/15 5:04 PM
 */
public class LRUCache3  extends LinkedHashMap<Integer, Integer> {
    private int capacity;

    public LRUCache3(int capacity) {
        super(capacity, 0.75F, true);
        this.capacity = capacity;
    }

    public int get(int key) {
        return super.getOrDefault(key, -1);
    }

    public void put(int key, int value) {
        super.put(key, value);
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
        return size() > capacity;
    }

}
