package LeetCode.p169_majorityElement;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        String[] parts = line.trim().split("\\s+");  //按空格split切分
        int[] nums = new int[parts.length];
        for (int i = 0; i < parts.length; ++i) {
            nums[i] = Integer.parseInt(parts[i]);
        }

        Solution solution = new Solution();
        int res = solution.majorityElement(nums);
        System.out.println(res);
    }
}
