package LeetCode.p62_uniquePaths;

class Solution3 {
    
    public int uniquePaths(int m, int n) {
        //记忆化搜索是先递下去，再归回来  先m - 1 n - 1递到 0 0  然后归到m - 1 n - 1
        //动态规划，直接走归的过程
        //定义dp[i][j]表示从[0, 0] 到 [i, j]的路径个数  返回结果就是dp[m - 1][n - 1] 因此定义dp数组大小为dp[m][n]
        int[][] dp = new int[m][n];

        //状态转移方程  来自于上方和左方 dp[i][j] = dp[i - 1][j] + dp[i][j - 1]

        //初始化第一行第一列只有一种路径，全初始化为1
        for (int i = 0; i < m; ++i) {
            dp[i][0] = 1;
        }

        for (int j = 0; j < n; ++j) {
            dp[0][j] = 1;
        }

        //遍历顺序 由于只依赖左和上，因此从上到下，从左到右遍历
        for (int i = 1; i < m; ++i) {
            for (int j = 1; j < n; ++j) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }

        return dp[m - 1][n - 1];


    }


}