package WrittenTest.HWP2363;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {

    static class Point{
        int x;
        int y;
        int path;
        public Point(int x, int y, int path) {
            this.x = x;
            this.y = y;
            this.path = path;
        }
    }
    private static int bestRes = Integer.MAX_VALUE;

    //只能往上下右走
    private static int[] dx = {-1, 1, 0};
    private static int[] dy = {0, 0, 1};

    private static int[][] matrix;

    private static void BFS(int i, int m, int n) { //传入第几行，和矩阵维度
        Deque<Point> q = new ArrayDeque<>();
        q.addLast(new Point(i, 0, 0)); //当真正走出第一步的时候path才++ 变成1
        boolean[][] visited = new boolean[m][n];
        visited[i][0] = true;

        while (q.isEmpty() == false) {
            Point cur = q.removeFirst();

            if (cur.y == n - 1) { //到达最后一列尝试更新路径
                bestRes = Math.min(bestRes, cur.path);
                //这里是不是该退出了，bfs第一次到一定是最短路径吧//gpt给回答一下
                return; // 直接退出本次 BFS
            }

            for (int d = 0; d < 3; ++d) {

                    int nx = cur.x + dx[d];
                    int ny = cur.y + dy[d];

                    if (nx >= m || nx < 0 || ny >= n || ny < 0) {
                        continue;
                    }

                    if (visited[nx][ny] || matrix[nx][ny] != 1) {
                        continue;
                    }
                    visited[nx][ny] = true;

                    q.addLast(new Point(nx, ny, cur.path + 1) );
            }

        }
    }
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

        //这里构建好了01矩阵


        for (int i = 0; i < m; ++i) {
            //对第一列每个1调用一次BFS，找到最优路径
            if (matrix[i][0] == 1) {
                BFS(i, m, n);
            }
        }

        if (bestRes != Integer.MAX_VALUE) {
            System.out.println(bestRes);
        } else {
            System.out.println(-1);
        }
    }
}
