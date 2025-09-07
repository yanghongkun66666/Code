package LeetCode.p75sortColors;

public class Solution {
    public void sortColors(int[] nums) {
        int n = nums.length;
        int left = 0, right = n - 1, i = 0;
        while (i <= right) {
            if (nums[i] == 1) {
                ++i;
            } else if (nums[i] == 0) {
                swap(nums, i, left);
                ++i;
                ++left;
            } else {
                swap(nums, i, right);
                --right;
                //此时不能 ++i因为right换过来的元素不一定是几
            }
        }
    }

    private void swap(int[] nums, int index1, int index2) {
        int tmp = nums[index1];
        nums[index1] = nums[index2];
        nums[index2] = tmp;
    }
}
