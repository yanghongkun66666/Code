package LeetCode.p797_allPathsSourceTarget;

import LeetCode.p234_isPalindrome.ListNode;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int[][] graph = {
                {1, 2},
                {3},
                {3},
                {}
        };

        Solution solution = new Solution();
        List<List<Integer>> res = solution.allPathsSourceTarget(graph);
        for (List<Integer> path : res) {
            for (int num : path) {
                System.out.print(num + " ");
            }
            System.out.println();
        }

    }
}
