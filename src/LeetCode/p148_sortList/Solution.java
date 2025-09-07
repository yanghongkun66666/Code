package LeetCode.p148_sortList;

public class Solution {
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) {
            return head; //这里不能直接写return null，这样所有的节点都是null了，归并排序最后如果是一个节点，那岂不是直接全是null了,归并了一堆null
        }
        ListNode mid = findMiddle(head);
        ListNode head1 = sortList(head);
        ListNode head2 = sortList(mid);

        return mergeList(head1, head2);
    }

    private ListNode findMiddle(ListNode head) {
        ListNode slow = head, fast = head, pre = null;
        while (fast != null && fast.next != null) {
            pre = slow;//先记录下，方便后面断链
            fast = fast.next.next;
            slow = slow.next;
        }

        pre.next = null;
        return slow;
    }

    private ListNode mergeList(ListNode head1, ListNode head2) {
        ListNode dummy = new ListNode(-1), cur = dummy;
        while (head1 != null && head2 != null) {
            if (head1.val <= head2.val) {
                cur.next = new ListNode(head1.val);
                head1 = head1.next;
            } else {
                cur.next = new ListNode(head2.val);
                head2 = head2.next;
            }
            cur = cur.next;
        }

        cur.next = head1 == null ? head2 : head1;
        return dummy.next;
    }
}
