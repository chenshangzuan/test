package java_base.leetcode.recursion;

/**
 * @author Kled
 * @date 2021/12/11 11:46 下午
 */
public class LinkReverse {
    public static Node reverseList2(Node head) {
        // 1.递归结束条件
        if (head == null || head.next == null) {
            return head;
        }
        // 递归反转 ⼦子链表
        Node newList = reverseList2(head.next); // 改变 1，2节点的指向。
        // 通过 head.next获取节点2
        Node t1 = head.next;
        // 让 2 的 next 指向 2
        t1.next = head;
         // 1 的 next 指向 null.
        head.next = null;
        // 把调整之后的链表返回。 return newList;
        return newList;
    }

    static class Node {
        int date;
        Node next;
        public Node(int date, Node next) {
            this.date = date;
            this.next = next;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "date=" + date +
                    '}';
        }
    }

    public static void main(String[] args) {
        Node node = new Node(1, new Node(2, null));
        Node reverseNode = LinkReverse.reverseList2(node);
        System.out.println(reverseNode);
    }
}
