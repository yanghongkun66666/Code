package Leetcode.p238_productExceptSelf;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        String[] parts = line.trim().split(",");
        int n = parts.length;
        int[] nums = new int[n];

        for (int i = 0; i < n; ++i) {
            nums[i] = Integer.parseInt(parts[i]);
        }

        Solution solution = new Solution();
        int[] res = solution.productExceptSelf(nums);
        for (int num : res) {
            System.out.print(num + " ");
        }
    }
}
