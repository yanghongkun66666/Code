package LeetCode.p216_combinationSum3;

import java.util.ArrayList;
import java.util.List;

public class Solution2 {
    private List<List<Integer>> res = new ArrayList<>();
    private List<Integer> path = new ArrayList<>();
    public List<List<Integer>> combinationSum3(int k, int n) {
        dfs(9, k, n);
        return res;
    }

    //定义dfs函数为原问题：从元素[1, i]中构造长度为k，和为n的path
    private void dfs(int i, int k, int n) {
        //边界条件
        if (path.size() == k && n == 0) {
            //路径和减到0了，满足要求，路径大小为k满足要求
            res.add(new ArrayList<>(path));
            return;
        }

        //剪枝
        if (n < 0 || i == 0) {
            return;
            //再向后递归n只会在负数的路上越走越远
        }

        //当前操作，从1<= j <= i的元素中选一个加入path中
        for (int j = i; j > 0; --j) {
            path.add(j);
            dfs(j - 1, k, n - j);
            //走到这里说明选择j的后续所有可能已经搜索完毕了，结果也该添加的都添加了剩下的就是该搜索选择<=j - 1的后续所有可能了
            path.remove(path.size() - 1);
        }
    }
}
