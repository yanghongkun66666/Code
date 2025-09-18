package LeetCode.p62_uniquePaths;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int m = sc.nextInt();
        int n = sc.nextInt();
//        Solution solution = new Solution(); //暴力dfs
//        Solution2 solution = new Solution2();//记忆化搜索
        Solution3 solution = new Solution3(); //动态规划，记忆化搜索归的过程
        System.out.println(solution.uniquePaths(m, n));
    }
}
