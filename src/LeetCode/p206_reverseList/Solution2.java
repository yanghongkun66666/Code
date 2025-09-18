package LeetCode.p206_reverseList;

public class Solution2 {
    public ListNode reverseList(ListNode head) {
        ListNode pre = null;
        return dfs(pre, head);
    }

    private ListNode dfs(ListNode pre, ListNode cur) {
        //递归反转完成后要返回反转后的头结点
        //传递参数肯定要传递head
        if (cur == null) {
            //边界条件
            return pre;
        }

        //单层递归逻辑 翻转一下，递归到下一层
        ListNode nxt = cur.next;
        cur.next = pre;

        return dfs(cur, nxt);
    }
}
