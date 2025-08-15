package leetcode.p56_mergeInterval;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    public int[][] merge(int[][] intervals) {

        Arrays.sort(intervals, (p, q)->(p[0] - q[0]));
        List<int[]> res = new ArrayList<>();
        for (int[] nums : intervals) {
            int n = res.size();
            if (n > 0 && nums[0] <= res.get(n - 1)[1]) {
                res.get(n - 1)[1] = Math.max(res.get(n - 1)[1], nums[1]);
            } else {
                res.add(new int[]{nums[0], nums[1]}); //防止修改原数组
            }
        }

        return res.toArray(new int[res.size()][]);  //list行转二维数组的行

    }
}