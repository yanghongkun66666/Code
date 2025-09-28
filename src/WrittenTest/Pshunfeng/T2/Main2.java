package WrittenTest.Pshunfeng.T2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
//这其实是网易游戏测开的笔试题
//m * n的一个网格，左上角到右下角的过程中不能为负数，要求路径血量大于0，求初始血量最低值，也就是比路径中最虚弱的时候多一滴血的样子
//输入-2 -3 3
//   -5 -10 1
//   10 30 -5  结果是7  走的路径是-2 -3 3 1 -5

/*
-2 -3 3
-5 -10 1
10 30 -5
 */
public class Main2 {

    private static  int res = Integer.MAX_VALUE;
    private static int[][] matrix;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int m = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());

        matrix = new int[m][n];
        for (int i = 0; i < m; ++i) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; ++j) {
                matrix[i][j] = Integer.parseInt(st.nextToken());
            }
        }


        dfs (0, 0, 0, 0);

        System.out.println(res);


    }

    private static void dfs(int i, int j, int curSum, int tmpRes) {
        if (i == matrix.length && j == matrix[0].length) {
            //到达了最后一个节点  边界条件
            curSum += matrix[i][j];
            if (curSum < 0) {
                tmpRes = Math.max(tmpRes, -curSum + 1);
            }

            res = Math.min(tmpRes, res); //挑战一下结果 用这条路径上的最低通过血量
            return;
        }

        //非边界条件
        //处理完当前节点，进入下一层，进入邻接节点
        curSum += matrix[i][j];
        if (curSum < 0) {
            tmpRes = Math.max(tmpRes, -curSum + 1);
        }
        dfs(i, j + 1, curSum, tmpRes);
        dfs(i + 1, j, curSum, tmpRes);
        //因为只能往右走，往下走

    }
}
