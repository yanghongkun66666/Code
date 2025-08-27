package Leetcode.p1_twoSum;
import java.util.HashMap;
import java.util.Map;

class Solution {
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();

        int n = nums.length;
        for (int i = 0; i < n; ++i) {
            int to_find = target - nums[i];
            if (map.containsKey(to_find)) {
                return new int[]{map.get(to_find), i};
            } else {
                map.put(nums[i], i);
            }
        }

        return new int[]{};
    }
}