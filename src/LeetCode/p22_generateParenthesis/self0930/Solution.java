package LeetCode.p22_generateParenthesis.self0930;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    private List<String> res = new ArrayList<>();
    private StringBuilder sb = new StringBuilder();
    public List<String> generateParenthesis(int n) {
        dfs(n, n);
        return res;
    }

    //原问题：从left个左括号和right个右括号里构造合法括号对数
    private void dfs(int left, int right) {
        //边界条件
        if (left == 0 && right == 0) {
            //此时可以保存结果了
            res.add(sb.toString());
            return;
        }

        //每轮递归都要保证左括号的个数 <= 右括号的个数 想一下为什么只有这样你的最终结果才可能是有效括号

        //边界条件，一定是不合法的，不用接着向下走了  right放多了
        if (left > right) {
            return;
        }

        //当前操作，在还能选择左括号的情况下选择左括号
        //此时left <= right
        if (left > 0) {
            sb.append('(');
            dfs(left - 1, right); //子问题从left - 1个左括号和right个右括号构造最终合法括号字符串
            //走到这里说明当前层选择左括号的后续所有可能都搜索完了，撤销选择左括号，尝试选择右括号
            sb.deleteCharAt(sb.length() - 1);
        }


        //当前操作，在还有足够的右括号供你选择的情况下选择右括号，因为必须要选一个，不选左括号（或者说搜索完选择左括号的后续可能后），就该选右括号了
        //如果此时left == right 同时又选了右括号，没事的，下一层left > right直接判断是不合法括号对返回了
        if (right > 0) {
            sb.append(')');
            dfs(left, right - 1);
            sb.deleteCharAt(sb.length() - 1);
        }

    }
}
