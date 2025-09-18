package LeetCode.p21_mergeTwoLists;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //l1 = [1,2,4], l2 = [1,3,4]
        ListNode dummy1 = new ListNode(-1);
        ListNode dummy2 = new ListNode(-1);
        Scanner sc = new Scanner(System.in);
        String[] arr1 = sc.nextLine().split(",");
        ListNode cur = dummy1;
        for (int i = 0; i < arr1.length; ++i) {
            cur.next = new ListNode(Integer.parseInt(arr1[i]));
            cur = cur.next;
        }

        String[] arr2 = sc.nextLine().split(",");
        cur = dummy2;
        for (int i = 0; i < arr1.length; ++i) {
            cur.next = new ListNode(Integer.parseInt(arr2[i]));
            cur = cur.next;
        }
        //此时构建好了两个链表

        Solution solution = new Solution();
        ListNode res = solution.mergeTwoLists(dummy1.next, dummy2.next);

        cur = res;
        while (cur != null) {
            System.out.print(cur.val + " ");
            cur = cur.next;
        }
    }
}
