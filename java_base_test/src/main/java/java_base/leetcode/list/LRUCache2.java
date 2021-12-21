package java_base.leetcode.list;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author Kled
 * @date 2021/12/15 3:14 PM
 */
public class LRUCache2 {
    BiDirectListNode head = null;
    BiDirectListNode tail = null;
    Map<Integer, BiDirectListNode> cache = new HashMap<>();
    int capacity;

    public LRUCache2(int capacity) {
        this.capacity = capacity;
    }

    public int get(int key) {
        BiDirectListNode node = cache.get(key);
        if (node == null) {
            return -1;
        } else {
            if (head == node) {
                return node.val;
            }
            if (tail == node) {
                tail = node.pre;
            }
            addFirst(node);
            return node.val;
        }
    }

    private void addFirst(BiDirectListNode node) {
        if(node.pre != null){
            node.pre.next = node.next;
        }
        if(node.next != null){
            node.next.pre = node.pre;
        }
        if(head != null){
            head.pre = node;
        }
        node.next = head;
        node.pre = null;
        head = node;
    }

    public void put(int key, int value) {
        BiDirectListNode node = cache.get(key);
        if (node == null) {
            if (cache.size() == capacity) {
                cache.remove(tail.key);
                tail = tail.pre;
            }
            node = new BiDirectListNode(key, value, null, null);
            addFirst(node);
            if(tail == null){
                tail = node;
            }
        } else {
            node.val = value;
            if (head != node) {
                if (tail == node) {
                    tail = node.pre;
                }
                addFirst(node);
            }
        }
        cache.put(key, node);
    }

    public static void main(String[] args) {
        LRUCache2 lruCache1 = new LRUCache2(2);
        lruCache1.put(1, 0);
        lruCache1.put(2, 2);
        System.out.println(lruCache1.get(1));
        lruCache1.put(3, 3);
        System.out.println(lruCache1.get(2));
        lruCache1.put(4, 4);
        System.out.println(lruCache1.get(1));
        System.out.println(lruCache1.get(3));
        System.out.println(lruCache1.get(4));
    }
}
