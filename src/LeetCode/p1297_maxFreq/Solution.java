package LeetCode.p1297_maxFreq;

import java.util.HashMap;
import java.util.Map;

class Solution {
    public int maxFreq(String S, int maxLetters, int minSize, int maxSize) {
        char[] s = S.toCharArray();

        Map<String, Integer> cntStr = new HashMap<>(); //用来统计满足条件的子串频率

        int[] cntChar = new int[26];  //统计每个字符出现的次数 当次数从0变1，此时kind++  次数从1变0，此时kind--
        int kinds = 0; //统计窗口内不同字符的种类数量
        int res = 0; //最大满足要求子串频率

        for (int right = 0; right < s.length; ++right) {
            //1. 进入窗口
            char c = s[right];
            if (cntChar[c - 'a'] == 0) {
                ++kinds;
            }
            cntChar[c - 'a']++;

            int left = right - minSize + 1; //假设minSize == 1此时滑窗长度已经满足条件

            if (left < 0) {
                //此时滑窗还不够长，右边界继续扩展
                continue;
            }

            if (kinds <= maxLetters) {
                //滑窗此时够长了，同时滑窗内字符个数满足要求
                String subStr = S.substring(left, left + minSize);  //[)
                cntStr.put(subStr, cntStr.getOrDefault(subStr, 0) + 1);
                int cnt = cntStr.get(subStr);
                res = Math.max(res, cnt);
            }

            //为了保持固定滑窗，left该移出窗口了
            char to_del = s[left];
            if (cntChar[to_del - 'a'] == 1) {
                --kinds;
            }
            ++left;

        }

        return res;
    }
}