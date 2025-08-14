package leetcode.p1_twoSum;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        //读取数组长度
        int n = sc.nextInt();
        int[] nums = new int[n];

        //读取数组元素
        for (int i = 0; i < n; ++i) {
            nums[i] = sc.nextInt();
        }

        //读取目标值
        int target = sc.nextInt();

        //调用Solution
        Solution sol = new Solution();
        int[] ans = sol.twoSum(nums, target);

        //输出结果
        if (ans.length == 2) {
            System.out.println(ans[0] + " " + ans[1]);
        } else {
            System.out.println("no solution found");
        }


    }
}
