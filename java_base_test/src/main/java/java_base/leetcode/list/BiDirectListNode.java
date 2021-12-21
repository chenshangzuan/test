package java_base.leetcode.list;

/**
 * @author Kled
 * @date 2021/12/13 1:46 下午
 */
public class BiDirectListNode {
    int key;
    int val;
    BiDirectListNode pre;
    BiDirectListNode next;
    BiDirectListNode() {}
    BiDirectListNode(int key, int val, BiDirectListNode pre, BiDirectListNode next) {
        this.key = key;
        this.val = val;
        this.next = next;
        this.pre = pre;
    }
}
