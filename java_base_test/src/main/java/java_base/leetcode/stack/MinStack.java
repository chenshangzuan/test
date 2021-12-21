package java_base.leetcode.stack;

import java_base.leetcode.list.ListNode;

/**
 * @author Kled
 * @date 2021/12/16 11:54 AM
 */
public class MinStack {
    ListNode head, tail = null;
    int min = Integer.MAX_VALUE;
    int size = 0;

    public MinStack() {
    }

    public void push(int val) {
        ListNode listNode = new ListNode(val);
        if (size == 0) {
            head = listNode;
            tail = listNode;
        } else {
            listNode.next = head;
            head = listNode;
        }
        size++;
        min = Math.min(val, min);
    }

    public void pop() {
        if (size == 0) {
            return;
        }
        if (head.val == min) {
            calculateMin();
        }
        head = head.next;
        size--;
    }

    public int top() {
        if (size == 0) {
            return -1;
        }
        return head.val;
    }

    public int getMin() {
        return min;
    }

    private void calculateMin() {
        min = Integer.MAX_VALUE;
        ListNode next = head.next;
        while (next != null) {
            min = Math.min(min, next.val);
            next = next.next;
        }
    }

    public static void main(String[] args) {
        MinStack minStack = new MinStack();
        minStack.push(-2);
        minStack.push(0);
        minStack.push(-3);
        System.out.println(minStack.getMin());
        minStack.pop();
        minStack.top();
        System.out.println( minStack.getMin());
    }
}
