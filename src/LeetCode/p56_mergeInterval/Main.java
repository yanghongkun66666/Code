package LeetCode.p56_mergeInterval;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        int[][] intervals = new int[n][2];

        for (int i = 0; i < n; ++i) {
            intervals[i][0] = sc.nextInt();
            intervals[i][1] = sc.nextInt();
        }

        Solution s = new Solution();
        int[][] res = s.merge(intervals);

        for (int[] interval : res) {
            System.out.println(interval[0] + " " + interval[1]);
        }
    }
}
