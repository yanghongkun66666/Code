package Leetcode.p148_sortList;

import java.util.List;
import java.util.Scanner;

public class Main {
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

        for (int i = 0; i < n; ++i) {
            cur.next = new ListNode(nums[i]);
            cur = cur.next;
        }


        Solution solution = new Solution();
        ListNode res = solution.sortList(dummy.next);

        cur = res;

        while (cur != null) {
            System.out.print(cur.val + " ");
            cur = cur.next;
        }

    }
}
