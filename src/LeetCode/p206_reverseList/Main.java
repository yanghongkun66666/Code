package LeetCode.p206_reverseList;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] parts = sc.nextLine().split(",");

        ListNode dummy = new ListNode(-1);
        ListNode cur = dummy;
        for (String str : parts) {
            cur.next = new ListNode(Integer.parseInt(str));
            cur = cur.next;
        }

//        Solution solution = new Solution();
        Solution2 solution = new Solution2();
        cur = solution.reverseList(dummy.next);

        while (cur != null) {
            System.out.print(cur.val + " ");
            cur = cur.next;
        }
    }
}
