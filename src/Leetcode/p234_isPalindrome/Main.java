package Leetcode.p234_isPalindrome;

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

        ListNode dummy = new ListNode(-1);
        ListNode cur = dummy;
        for (int i = 0; i < n; ++i) {
            ListNode node = new ListNode(nums[i]);
            cur.next = node;
            cur = cur.next;
        }

        Solution solution = new Solution();
        System.out.println(solution.isPalindrome(dummy.next));
    }
}
