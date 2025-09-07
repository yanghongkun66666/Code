package LeetCode.BM2;

public class Solution {
    /**
     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
     *
     *
     * @param head ListNode类
     * @param m int整型
     * @param n int整型
     * @return ListNode类
     * 1 2 3 4 5   2 4    1 4 3 2 5
     */
    /**
     * 反转单链表的指定区间 [m, n]（1-based）
     */
    public ListNode reverseBetween(ListNode head, int m, int n) {
        if (head == null || m == n) return head;

        // 虚拟头，统一处理 m=1 的情况
        ListNode dummy = new ListNode(-1);
        dummy.next = head;

        // 1) 先“划定边界”：走到 m 的前一个结点
        ListNode pre = dummy;     // 将停在 m-1
        for (int i = 1; i < m; i++) {
            pre = pre.next;
        }

        // 现在 pre 在 m-1，cur 在 m
        ListNode cur = pre.next;
        ListNode firstTail = pre;     // 前半段的尾巴（m-1）
        ListNode reverseTail = cur;   // 反转段未来的尾巴（当前 m 结点）

        // 2) 反转长度为 len = n - m + 1 的子链表
        int len = n - m + 1;
        ListNode prev = null;
        for (int i = 0; i < len; i++) {
            ListNode next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }
        // 反转结束：
        // prev 指向反转后子链表的头（原 n 位置）
        // cur  指向 n+1（区间后半段的头）

        // 3) 重新连接三段
        firstTail.next = prev;      // 前半段尾 -> 反转段头
        reverseTail.next = cur;     // 反转段尾 -> 后半段头（n+1）

        return dummy.next;
    }
}
