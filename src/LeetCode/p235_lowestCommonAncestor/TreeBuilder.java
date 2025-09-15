package LeetCode.p235_lowestCommonAncestor;

import java.util.ArrayDeque;
import java.util.Deque;

public class TreeBuilder {
    public static TreeNode buildTree(Integer[] level) {
        if (level == null || level.length == 0) {
            return null;
        }

        TreeNode root = new TreeNode(level[0]);
        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.addLast(root);

        int i = 1;
        while (queue.isEmpty() == false && i < level.length) {
            TreeNode cur = queue.removeFirst();

            if (i < level.length && level[i] != null) {
                cur.left = new TreeNode(level[i]);
                queue.addLast(cur.left);
            }
            ++i;

            if (i < level.length && level[i] != null) {
                cur.right = new TreeNode(level[i]);
                queue.addLast(cur.right);
            }
            ++i;
        }

        return root;
    }

    public static TreeNode find(TreeNode root ,int target) {
        if (root == null) {
            return root;
        }

        if (root.val == target) return root;
        TreeNode left = find(root.left, target);
        return left == null ? find(root.right, target) : left;
    }
}
