package java_base.leetcode.list;

import java.util.Stack;

/**
 * @author Kled
 * @date 2021/12/13 7:17 下午
 */
public class ReverseList {
    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode pre = null;
        ListNode next = null;
        while (head != null) {
            next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
        return pre;
    }

    public ListNode reverseList2(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode newHead = reverseList2(head.next);
        head.next.next = head;
        head.next = null;
        return newHead;
    }

    public ListNode reverseList3(ListNode head) {
        if (head == null) {
            return null;
        }
        Stack<ListNode> stack = new Stack<>();
        stack.push(head);
        ListNode next = head.next;
        while (next != null) {
            stack.add(next);
            next = next.next;
        }

        ListNode newHead = stack.pop();
        ListNode pre = newHead;
        while (!stack.isEmpty()) {
            ListNode node = stack.pop();
            node.next = null;
            pre.next = node;
            pre = node;
        }
        return newHead;
    }

    public static void main(String[] args) {
        ListNode listNode = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));

        System.out.println(new ReverseList().reverseList3(listNode));
    }
}
