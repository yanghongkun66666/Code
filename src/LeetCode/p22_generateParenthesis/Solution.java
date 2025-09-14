package LeetCode.p22_generateParenthesis;

import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<String> generateParenthesis(int n) {
        List<List<String>> res = new ArrayList<>(); //res[i]代表i对有效括号可能的所有组合

        for (int i = 0; i <= n; ++i) {
            res.add(new ArrayList<>());
        }

        res.get(0).add(""); //0对就是空

        for (int i = 1; i <= n; ++i) {
            for (int j = 0; j < i; ++j) {
                int k = i - j - 1;

                for (String p : res.get(j)) {
                    for (String q : res.get(k)) {
                        res.get(i).add("(" + p + ")" + q); //j 对 + k 对 + 1 对就是i对  j对的所有合法括号可能排列组合方式是res.get(j)
                    }
                }
            }
        }

        return res.get(n);
    }
}