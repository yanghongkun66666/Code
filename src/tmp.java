import LeetCode.p143_reorderList.ListNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class tmp {
    static class ListNode{
        int val;
        ListNode next;

        ListNode() {}
        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }

    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        String[] parts = line.trim().split(",");
        int n = parts.length;

        int[] nums = new int[n];

        for (int i = 0; i < n; ++i) {
            nums[i] = Integer.parseInt(parts[i]);
        }

        ListNode dummy = new ListNode(-1), cur = dummy;
        for (int i = 0; i < n; i++) {
            cur.next = new ListNode(nums[i]);
            cur = cur.next;
        }
        Solution solution = new Solution();

        solution.reorderList(dummy.next);

        cur = dummy.next;
        while (cur != null) {
            System.out.print(cur.val + " ");
        }
    }

    static class Solution {
        public void reorderList(ListNode head) {
            ListNode mid = findMiddle(head);

            ListNode head2 = reverseList(mid);

            ListNode p = head, q = head2;
            while (q.next != null) {
                ListNode tmp1 = p.next;
                ListNode tmp2 = q.next;
                p.next = q;
                q.next = tmp1;
                p = tmp1;
                q = tmp2;
            }
        }


        private ListNode findMiddle(ListNode head) {
            ListNode slow = head, fast = head;
            while (fast != null && fast.next != null) {
                fast = fast.next.next;
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
}
