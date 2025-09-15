package LeetCode.p235_lowestCommonAncestor;

public class Main {
    public static void main(String[] args) {
        Integer[] level = {3,5,1,6,2,0,8,null,null,7,4};

        TreeNode root = TreeBuilder.buildTree(level);

        TreeNode p = TreeBuilder.find(root, 5);
        TreeNode q = TreeBuilder.find(root, 1);

        Solution solution = new Solution();
        TreeNode res = solution.lowestCommonAncestor(root, p, q);
        System.out.println(res.val);
    }
}
