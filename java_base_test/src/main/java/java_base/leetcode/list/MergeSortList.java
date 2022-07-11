package java_base.leetcode.list;

/**
 * @author Kled
 * @date 2021/12/23 8:21 PM
 * 链表归并排序
 */
public class MergeSortList {
    public ListNode sortList(ListNode head) {
        if(head == null){
            return null;
        }
        ListNode midNode = getMidNode(head);
        if(midNode == null || midNode.next == null){
            return head;
        }
        ListNode midNextNode = midNode.next;
        midNode.next = null;
        ListNode p1 = sortList(head);
        ListNode p2 = sortList(midNextNode);
        return merge(p1, p2);
    }

    private ListNode merge(ListNode p1, ListNode p2) {
        ListNode head = null;
        ListNode next = null;
        while (p1 != null && p2 != null){
            ListNode tmp;
            if(p1.val < p2.val){
                tmp = p1;
                p1 = p1.next;
            }else {
                tmp = p2;
                p2 = p2.next;
            }
            if(head == null){
                next = tmp;
                head = next;
            }else {
                next.next = tmp;
                next = next.next;
            }
        }

        if(p1 != null){
            next.next = p1;
        }

        if(p2 != null){
            next.next = p2;
        }
        return head;
    }

    public ListNode getMidNode(ListNode head){
        ListNode slow = head, fast = slow;
        while (fast.next != null && fast.next.next != null){
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }
}
