package java_base.leetcode.list;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author Kled
 * @date 2021/12/14 9:27 PM
 */
public class PalindromeList {
    //栈
    public boolean isPalindrome(ListNode head) {
        if (head == null) {
            return true;
        }

        int size = 0;
        Stack<Integer> stack = new Stack<>();
        ListNode next = head;
        while (next != null) {
            size++;
            stack.push(next.val);
            next = next.next;
        }

        for (int i = 1; i <= size / 2; i++) {
            Integer val = stack.pop();
            if(head.val != val){
                return false;
            }
            head = head.next;
        }
        return true;
    }

    //反转后半链表
    public boolean isPalindrome2(ListNode head) {
        if (head == null) {
            return true;
        }

        // 找到前半部分链表的尾节点并反转后半部分链表
        ListNode firstHalfEnd = endOfFirstHalf(head);
        ListNode secondHalfStart = reverseList(firstHalfEnd.next);

        // 判断是否回文
        ListNode p1 = head;
        ListNode p2 = secondHalfStart;
        boolean result = true;
        while (result && p2 != null) {
            if (p1.val != p2.val) {
                result = false;
            }
            p1 = p1.next;
            p2 = p2.next;
        }

        // 还原链表并返回结果
        firstHalfEnd.next = reverseList(secondHalfStart);
        return result;
    }

    private ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        while (curr != null) {
            ListNode nextTemp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextTemp;
        }
        return prev;
    }

    private ListNode endOfFirstHalf(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }

    //利用数组
    public boolean isPalindrome3(ListNode head) {
        List<Integer> vals = new ArrayList<Integer>();

        // 将链表的值复制到数组中
        ListNode currentNode = head;
        while (currentNode != null) {
            vals.add(currentNode.val);
            currentNode = currentNode.next;
        }

        // 使用双指针判断是否回文
        int front = 0;
        int back = vals.size() - 1;
        while (front < back) {
            if (!vals.get(front).equals(vals.get(back))) {
                return false;
            }
            front++;
            back--;
        }
        return true;
    }
}
