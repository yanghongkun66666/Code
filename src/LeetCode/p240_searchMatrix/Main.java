package LeetCode.p240_searchMatrix;

import java.util.Scanner;

/**
 * https://leetcode.cn/problems/search-a-2d-matrix-ii/description/
 */

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //matrix = [[1,4,7,11,15],[2,5,8,12,19],[3,6,9,16,22],[10,13,14,17,24],[18,21,23,26,30]], target = 5
//        int m = sc.nextInt();
//        int n = sc.nextInt();
//        int[][] matrix = new int[m][n];
//
//        for (int i = 0; i < m; ++i) {
//            for (int j = 0; j < n; ++j) {
//                matrix[i][j] = sc.nextInt();
//            }
//        }
        int[][] matrix = {
                {1,4,7,11,15},
                {2,5,8,12,19},
                {3,6,9,16,22},
                {10,13,14,17,24},
                {18,21,23,26,30}
        };

        int target = 5;

        Solution solution = new Solution();
        System.out.println(solution.searchMatrix(matrix, target));
    }
}
