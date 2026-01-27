package LeetCode.p416_minus;

import java.util.Scanner;

/**
 * https://leetcode.cn/problems/search-a-2d-matrix-ii/description/
 */

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String str1 = sc.next();
        String str2 = sc.next();

        Solution solution = new Solution();
        System.out.println(solution.minus(str1, str2));
    }
}
