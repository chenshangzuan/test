package java_base.leetcode.list;

/**
 * @author Kled
 * @date 2021/11/9 8:15 下午
 * 将两个升序链表合并为一个新的 升序 链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
 */
public class MergeTwoLists {
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
//        ListNode head = new ListNode();
//        ListNode tail = null;
//        while(l1 != null || l2 != null){
//            boolean nextNodeL1;
//            if(l1 == null){
//                nextNodeL1 = false;
//            }else if(l2 == null){
//                nextNodeL1 = true;
//            }else {
//                nextNodeL1 = l1.val <= l2.val;
//            }
//            int val = nextNodeL1 ? l1.val : l2.val;
//            if(nextNodeL1){
//                l1 = l1.next;
//            }else {
//                l2 = l2.next;
//            }
//            if(tail == null){
//                tail = new ListNode(val);
//                head.next = tail;
//            }else {
//                tail.next = new ListNode(val);
//                tail = tail.next;
//            }
//        }

        ListNode head = new ListNode();
        ListNode tail = null;
        while(l1 != null || l2 != null){
            if(l1 == null){
                if(tail == null){
                    head.next = l2;
                }else {
                    tail.next = l2;
                }
                break;
            }else if(l2 == null){
                if(tail == null){
                    head.next = l1;
                }else {
                    tail.next = l1;
                }
                break;
            }else {
                int val;
                if(l1.val <= l2.val){
                    val = l1.val;
                    l1 = l1.next;
                }else {
                    val = l2.val;
                    l2 = l2.next;
                }
                if(tail == null){
                    tail = new ListNode(val);
                    head.next = tail;
                }else {
                    tail.next = new ListNode(val);
                    tail = tail.next;
                }
            }
        }
        return head.next;
    }
}
