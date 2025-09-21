package LeetCode.p131_partition;

import java.util.ArrayList;
import java.util.List;

public class Solution2 {
    List<List<String>> res = new ArrayList<>();
    List<String> path = new ArrayList<>();
    public List<List<String>> partition(String s) {
        dfs(0, 0, s);
        return res;
    }

    private void dfs(int start, int i, String s) {
        //表示当前待分割回文子串从start开始，判断第i和i + 1之间是否加逗号
        //dfs函数定义为 考虑i后面的逗号怎么选来构造回文分割
        if (i == s.length()) { //s分割完毕
            res.add(new ArrayList<>(path));
            return;
        }

        //考虑i 和 i + 1之间的逗号选不选

        //这是不选的情况，直接考虑i + 1（包括）后面的逗号怎么选来构造回文分割，要特判一下i如果是n - 1那必然要选逗号，进行回文分割
        //不分割，不选i和i+1之间的逗号
        if (i < s.length() - 1) {// i=n-1 时只能分割
            //这时候还有权利选一下当前逗号是加还是不加，最后一个字符那紧跟着的逗号必要然加
            dfs(start,i + 1, s); // 考虑 i+1 后面的逗号怎么选
            //注意这里没分割，因此传递的还是start
        }

        //此时i 和 i + 1之间的逗号要加上
        //于是这里start派上用场，先判断区间是否是回文[start, i]
        //分割，选 i 和 i+1 之间的逗号（把S[i]作为子串最后一个字符）
        if (isPalindrome(s, start, i) == true) {
            path.add(s.substring(start, i + 1));  //截取字符串[start, i] 这段已经是回文了 说明在i和i+1之间加了逗号
            dfs(i + 1, i + 1, s); //分割之后，下一个回文子串起始位置就是i + 1，下一个待判断是否加逗号也是i + 1
            //走到这里说明在i和i + 1之间加了逗号之后 后续所有逗号选或者不选都已经全部搜索了一遍了，
            //因此去掉i和i + 1之间的逗号，尝试当前层下一个位置是否加逗号了
            path.remove(path.size() - 1);
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
