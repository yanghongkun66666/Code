package LeetCode.p143_reorderList;

public class Solution {
    public void reorderList(ListNode head) {
        //先找到中间节点，分成两个链表1 2 3 4
        //反转第二个链表  1 2   4 3
        // 1 2 3
        // 4 3
        //合并两个链表 1 4 2 3

        //如果奇数个元素 1 2 3 4 5 -> 1 2 3    4 5
        // 1 5 2 4 3  合并的时候判断第一个链表有没有结束就好，让第二个链表多一个元素
        //不断开链表，共享3这个尾巴节点，
        //1 2 3
        //5 4 3
        ListNode mid = findMiddle(head);
        ListNode head2 = reverseList(mid);

        ListNode p = head, q = head2;
        while (q.next != null) {  //注意这里为什么是q.next != null呢 上面注释有解释 q指向共享尾巴节点就停止
            //1 2 3
            //5 4 3  这个例子走一遍逻辑就知道为什么终止条件是q.next了
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
        ListNode pre = null, cur = head;
        while (cur != null) {
            ListNode nxt = cur.next;
            cur.next = pre;
            pre = cur;
            cur = nxt;
        }

        return pre;
    }
}
