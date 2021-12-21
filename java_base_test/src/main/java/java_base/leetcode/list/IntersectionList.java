package java_base.leetcode.list;

/**
 * @author Kled
 * @date 2021/12/14 10:27 上午
 * 相交链表，
 * 1.根据最后一个节点是否相同判断是否相交
 * 2.根据链表长度差值，长链表先遍历，然后同时遍历第一个相同节点即为相交的节点
 */
public class IntersectionList {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }
        int lenA = 1;
        ListNode nextA = headA.next;
        ListNode lastNodeA = headA;
        while (nextA != null) {
            lenA++;
            if (nextA.next == null) {
                lastNodeA = nextA;
            }
            nextA = nextA.next;
        }

        int lenB = 1;
        ListNode nextB = headB.next;
        ListNode lastNodeB = headB;
        while (nextB != null) {
            lenB++;
            if (nextB.next == null) {
                lastNodeB = nextB;
            }
            nextB = nextB.next;
        }

        if (lastNodeA != lastNodeB) {
            return null;
        }

        return lenA >= lenB ? findIntersectionNode(headA, headB, lenA - lenB) : findIntersectionNode(headB, headA, lenB - lenA);
    }

    public ListNode findIntersectionNode(ListNode headA, ListNode headB, int subLen) {
        while (subLen > 0) {
            headA = headA.next;
            subLen--;
        }
        while (headA != headB) {
            headA = headA.next;
            headB = headB.next;
        }
        return headA;
    }
}
