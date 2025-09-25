package LeetCode.p3_lengthOfLongestSubstring;

import javax.print.DocFlavor;
import java.util.HashMap;
import java.util.Map;

public class Solution {
    public int lengthOfLongestSubstring(String s) {
        int n = s.length();
        int left = 0;

        int res = 0;
        Map<Character, Integer> cnt = new HashMap<>();//统计滑动窗口内每个字符的个数
        for (int right = 0; right < n; ++right) {
            char c = s.charAt(right);
            cnt.put(c, cnt.getOrDefault(c, 0) + 1);

            while (cnt.get(c) > 1) {
                char to_del = s.charAt(left);
                cnt.put(to_del, cnt.get(to_del) - 1);
                ++left;
            }

            res = Math.max(res, right - left + 1);
        }

        return res;
    }
}
