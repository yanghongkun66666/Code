package WrittenTest.HWP3298;

import java.io.*;
import java.util.*;

public class Main {
    static int m, n;
    static int[][] matrix, dp;
    // 四个方向：上、下、左、右
    static int[][] dirs = {{-1,0},{1,0},{0,-1},{0,1}};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        m = Integer.parseInt(st.nextToken());
        n = Integer.parseInt(st.nextToken());

        matrix = new int[m][n];
        dp = new int[m][n];
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                matrix[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int ans = 0;
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                ans = Math.max(ans, dfs(i, j));

        System.out.println(ans);
    }

    static int dfs(int i, int j) {
        // 如果已计算过，直接返回
        if (dp[i][j] != 0) return dp[i][j];
        int best = 1; // 至少包含自己
        for (int[] d : dirs) {
            int x = i + d[0], y = j + d[1];
            if (x >= 0 && x < m && y >= 0 && y < n 
                && matrix[x][y] < matrix[i][j]) {
                best = Math.max(best, 1 + dfs(x, y));
            }
        }
        dp[i][j] = best;
        return best;
    }
}
