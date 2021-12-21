package java_base.leetcode.list;

/**
 * @author Kled
 * @date 2021/12/13 2:14 下午
 * 判断链表是否有环，并返回第一个入环的节点
 */
public class CycleList2 {
    public ListNode detectCycle(ListNode head) {
        if (head == null || head.next == null || head.next.next == null) {
            return null;
        }
        boolean isCycle = false;
        ListNode slow = head.next;
        ListNode fast = head.next.next;
        while (slow != null && fast != null && fast.next != null) {
            if (slow == fast) {
                isCycle = true;
                break;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        if(isCycle){
            //如果有环，从快慢指针相遇的节点起，以及再次从head节点起的慢指针相遇点，即为环入口点
            ListNode slow2 = head;
            while (slow2 != slow){
                slow2 = slow2.next;
                slow = slow.next;
            }
            return slow;
        }
        return null;
    }
}
