package WrittenTest.Pshunfeng.T2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

//m * n网格 初始生命值为正整数, 格子数据 负数  则 cur+负数 格子有正 0 负值，计算最低初始生命值最小花费路径取正值
//dp[i][j]定义为从[0, 0]到 [i, j]的最小花费生命 最终返回结果是dp[m - 1][n - 1]  dp[m][n]
//递推公式 dp[i][j] = dp[i - 1][j] + nums[i - 1][j] dp[i][j - 1] + nums[i][j - 1]取一个最小值
//初始化dp[0][0]
//m * n的一个网格，左上角到右下角的过程中不能为负数，要求路径血量大于0，求初始血量最低值，也就是比路径中最虚弱的时候多一滴血的样子
//输入-2 -3 3
//   -5 -10 1
//   10 30 -5  结果是7  走的路径是-2  -3  3  1  -5
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int m = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());

        int[][] matrix = new int[m][n];
        for (int i = 0; i < m; ++i) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; ++j) {
                matrix[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int[][] dp = new int[m][n];
        for (int i = 1; i < m; ++i) {
            dp[i][0] = dp[i - 1][0] + matrix[i][0];
        }

        for (int j = 1; j < n; ++j) {
            dp[0][j] = dp[0][j - 1] + matrix[0][j];
        }

        for (int i = 1; i < m; ++i) {
            for (int j = 1; j < n; ++j) {
                dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
            }
        }

        if (dp[m - 1][n - 1] > 0) {
            System.out.println(0);
        } else {
            System.out.println(-dp[m - 1][n - 1]);
        }

    }
}
