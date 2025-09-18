package LeetCode.p62_uniquePaths;

class Solution {

    public int uniquePaths(int m, int n) {
        return dfs(m - 1, n - 1);
    }

    private int dfs(int i, int j) {
        //dfs(i, j)表示从（0,0）到（i，j）的总路径个数
        if (i == 0 || j == 0) {
            //第一行第一列始终只有一条路径
            return 1;
        }

        return dfs(i - 1, j) + dfs(i, j - 1); //当前位置来自这两个方向
    }
}