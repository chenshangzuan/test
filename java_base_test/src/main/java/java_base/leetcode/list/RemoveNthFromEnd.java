package java_base.leetcode.list;

/**
 * @author Kled
 * @date 2021/12/13 1:45 下午
 */
public class RemoveNthFromEnd {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null) {
            return null;
        }

        int len = 1;
        ListNode next = head.next;
        while (next != null) {
            len++;
            next = next.next;
        }

        if (n > len) {
            return head;
        }

        if (n == len) {
            return head.next;
        }

        //定位需要删除节点的前驱节点
        int step = len - n - 1;
        ListNode target = head;
        while (step > 0) {
            target = target.next;
            step--;
        }

        target.next = target.next.next;
        return head;
    }
}
