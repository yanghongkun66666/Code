package LeetCode.p25_kReverse;

import java.util.Scanner;

/**
 * A simple main class to test the Solution for reversing a linked list in k-groups.
 */
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Read input from the console.
        System.out.println("Enter numbers for the linked list, separated by spaces:");
        String[] arr = sc.nextLine().split(" ");
        System.out.println("Enter the group size k:");
        int k = sc.nextInt();

        // Build the linked list from the input array.
        ListNode dummy = new ListNode(-1);
        ListNode cur = dummy;
        for (String s : arr) {
            cur.next = new ListNode(Integer.parseInt(s));
            cur = cur.next;
        }

        // Reverse the list in k-groups.
        Solution sol = new Solution();
        ListNode head = sol.reverseKGroup(dummy.next, k);

        // Print the result.
        System.out.println("Resulting list after k-group reversal:");
        while (head != null) {
            System.out.print(head.val + " ");
            head = head.next;
        }
        System.out.println();
    }
}