package LeetCode.p238_productExceptSelf;

public class Solution {
    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] res = new int[n];
        res[0] = 1; //[0, 0)的前缀积

        for (int i = 1; i < n; ++i) {
            res[i] = res[i - 1] * nums[i - 1];  //res[n - 1] = res[n - 2] * nums[n - 2]就是除了n - 1下标的前缀乘积
            //res[1] 代表[0, 1)的前缀积
        }

        int R = 1; //R目前代表(n - 1, n - 1]的后缀积  前后缀积都代表除了当前下标之外的乘积
        for (int j = n - 1; j >= 0; --j) {
            res[j] = res[j] * R;
            R *= nums[j];
        }

        return res;
    }
}
