package LeetCode.p131_partition;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    private List<List<String>> res = new ArrayList<>(); // 存放所有合适的分割
    private List<String> path = new ArrayList<>(); //存放当前可能的分割 （既然沿着路途走到这里的可能分割）
    public List<List<String>> partition(String s) {
        dfs(0, s);
        return res;
    }

    private void dfs(int i, String s) {
        //考虑 s[i] ~ s[n - 1]怎么分割 这是原问题

        if (i == s.length()) {  //边界条件
            //到这里说明前一层已经将s.length() - 1的字符划分进合适的子串放进path了
            res.add(new ArrayList<>(path));
            return;
        }

        //当前操作，枚举回文子串结束位置 s[i....j] i 已经有了，关键是枚举j的位置了
        for (int j = i; j < s.length(); ++j) {
            //先判断s[i....j]是否是回文子串，不是就判断下一个j
            if (isPalindrome(s, i, j) == true) {
                //这时候s[i]...s[j]已经是合法的回文子串了
                path.add(s.substring(i, j + 1));   //因为substring(i, j + 1)  是截取[i, j]
                dfs(j + 1, s);  //下一层的i是从j + 1开始，如果这一层的j == n - 1已经划分进合适的回文子串了，那么下一层i == n就可以退出了
                //到这里说明以j为结尾的回文子串添加进路径后已经搜索完毕了,在当前基础上所有可能的结果已经搜索了一遍了
                //此时取消当前回文子串，选择以i开头，其他j结尾的回文子串
                path.remove(path.size() - 1);
            }
        }
    }

    private boolean isPalindrome(String s, int left, int right) {
        while (left < right) {
            if (s.charAt(left++) != s.charAt(right--)) {
                return false;
            }
        }
        return true;
    }
}
