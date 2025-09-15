package LeetCode.p236_lowestCommonAncestor;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

public class Main {

    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) {val = x;}
    }

    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) {
            return root;
        }

        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        //不要一开始陷入细节
        if (left != null && right != null) {
            return root;
        }

        if (left == null) return right;
        if (right == null) return left;

        return null;
    }

    public static void main(String[] args) {
        Integer[] level = {3,5,1,6,2,0,8,null,null,7,4};

        TreeNode root = buildTree(level);

        // 通过值在树中找到“同一引用”
        TreeNode p = find(root, 5);
        TreeNode q = find(root, 1);

        TreeNode res = lowestCommonAncestor(root, p, q);

        System.out.println(res.val);

    }

    private static TreeNode buildTree(Integer[] level) {
        if (level == null || level.length == 0) {
            return null;
        }
        TreeNode root = new TreeNode(level[0]);

        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.addLast(root);
        int i = 1;

        while (!queue.isEmpty() && i < level.length) {
            TreeNode node = queue.removeFirst();

            //左
            if (i < level.length && level[i] != null) {
                node.left = new TreeNode(level[i]);
                queue.addLast(node.left);
            }
            ++i;

            //右
            if (i < level.length && level[i] != null) {
                node.right = new TreeNode(level[i]);
                queue.addLast(node.right);
            }
            ++i;
        }

        return root; //至此树已经构建完成
    }

    // 值唯一时可用；若可能重复值，需改成别的定位方式
    private static TreeNode find(TreeNode root, int target) {
        if (root == null) return null;
        if (root.val == target) return root;
        TreeNode l = find(root.left, target);
        return l != null ? l : find(root.right, target);
    }
}
