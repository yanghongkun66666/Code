package LeetCode.p75sortColors;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        String[] parts = line.trim().split(",");
        int[] nums = new int[parts.length];

        for (int i = 0; i < parts.length; ++i) {
            nums[i] = Integer.parseInt(parts[i]);
        }

        Solution solution = new Solution();
        solution.sortColors(nums);
        for (int num : nums) {
            System.out.print(num + " ");
        }
    }
}
