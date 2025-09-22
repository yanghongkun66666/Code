package LeetCode.p103_zigzagLevelOrder;

import java.util.*;

public class Solution {
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        Deque<TreeNode> queue = new ArrayDeque<>();

        queue.addLast(root);
        while (queue.isEmpty() == false) {
            int n = queue.size();
            List<Integer> level = new ArrayList<>();
            while (n-- != 0) {
                TreeNode node = queue.removeFirst();
                level.add(node.val);
                if (node.left != null) {
                    queue.addLast(node.left);
                }

                if (node.right != null) {
                    queue.addLast(node.right);
                }
            }

            if (res.size() % 2 == 1) {
                Collections.reverse(level);
            }
            res.add(level);
        }

        return res;
    }
}


