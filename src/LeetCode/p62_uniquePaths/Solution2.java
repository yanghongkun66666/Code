package LeetCode.p62_uniquePaths;

class Solution2 {
    private int[][] memo; //记忆化搜索，减去递归过程中很多无谓的计算
    
    public int uniquePaths(int m, int n) {
        memo = new int[m][n];
        return dfs(m - 1, n - 1);
    }

    private int dfs(int i, int j) {
        //dfs(i, j)表示从（0,0）到（i，j）的总路径个数
        if (i == 0 || j == 0) {
            //第一行第一列始终只有一条路径
            return 1;
        }

        if (memo[i][j] != 0) {
            return memo[i][j]; //到ij位置的路径个数我们之前如果已经计算过了，那么我们就无需重复计算了
        }

        return memo[i][j] = dfs(i - 1, j) + dfs(i, j - 1); //当前位置来自这两个方向
        //记忆化搜索保存一下已经计算的结果
    }
}