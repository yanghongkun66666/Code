package WrittenTest.HWP3298;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int m, n;
    static int[][] matrix, memo;
    static int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    private static int dfs(int i, int j) { //返回当前i j 出发的最长路径长度

        if (memo[i][j] != -1) {
            return memo[i][j];
        }

        int res = 1; //当前点算是一个单位路径长度
        for (int[] dir : dirs) {
            //子问题，问题规模更小
            int x = i + dir[0], y = j + dir[1];
            if (x >= 0 && x < m && y >= 0 && y < n && matrix[x][y] < matrix[i][j]) {
                res = Math.max(res, 1 + dfs(x, y));
            }
        }

        return memo[i][j] = res;
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        m = Integer.parseInt(st.nextToken());
        n = Integer.parseInt(st.nextToken());


        matrix = new int[m][n];
        memo = new int[m][n];

        for (int i = 0; i < m; ++i) {
            Arrays.fill(memo[i], -1);
        }

        for (int i = 0; i < m; ++i) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; ++j) {
                matrix[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int res = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                res = Math.max(res, dfs(i, j));
            }
        }

        System.out.println(res);
    }
}
