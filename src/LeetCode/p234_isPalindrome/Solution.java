package LeetCode.p234_isPalindrome;

public class Solution {
    public boolean isPalindrome(ListNode head) {
        ListNode mid = findMiddle(head);
        ListNode head2 = reverseList(mid); //获取反转后的头结点

        while (head2 != null) {
            if (head.val != head2.val) {
                return false;
            }

            head = head.next;
            head2 = head2.next;
        }

        return true;
    }

    private ListNode findMiddle(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next; //因为fast要走两步，所以while条件用fast来判断
            slow = slow.next;
        }

        return slow;
    }

    private ListNode reverseList(ListNode head) {
        ListNode pre = null;
        ListNode cur = head;
        while (cur != null) {
            ListNode nxt = cur.next;
            cur.next = pre;
            pre = cur;
            cur = nxt;
        }

        return pre;
    }
}
