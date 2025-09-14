package WrittenTest.test;

import java.util.*;

public class Solution {
    public int[] lessNums(int[] nums) {
        int n = nums.length;
        Map<Integer, Integer> cnt = new HashMap<>();
        Map<Integer, Integer> pos = new HashMap<>();
        for (int num : nums) {
            cnt.put(num, cnt.getOrDefault(num, 0) + 1);
        }

        List<Integer> keys = new ArrayList<>();
        for (Map.Entry<Integer,Integer> entry : cnt.entrySet()) {
            keys.add(entry.getKey());
        }

        Collections.sort(keys);

        int[] res = new int[n];
        for (int i = 1; i < keys.size(); ++i) {
            cnt.put(keys.get(i), cnt.get(keys.get(i)) + cnt.get(keys.get(i - 1)));
            pos.put(keys.get(i), i);
        }

        for (int i = 0; i < n; i++) {
            int p = pos.getOrDefault(nums[i], 0);
            if (p != 0) {
                res[i] = cnt.get(keys.get(p - 1));
            }
        }

        return res;
    }
}
