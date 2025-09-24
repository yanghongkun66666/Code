package WrittenTest.HWP3300;

import AWeiDianDong.SkipList.Node;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

//就是统计联通分量的个数，然后求两两组合数
public class Main {

    static class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private static int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
    private static int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); //用来读一行
        StringTokenizer st = new StringTokenizer(br.readLine()); //用来把一行的进行分词

        int m = Integer.parseInt(st.nextToken()); //获取到空格为止吗
        int n = Integer.parseInt(st.nextToken());

        int[][] graph = new int[m][n];
        for (int i = 0; i < m; ++i) {
            String line = br.readLine();

            st = new StringTokenizer(line);

            for (int j = 0; j < n; ++j) {
                graph[i][j] = Integer.parseInt(st.nextToken());
            }

        }

        //此时构建好了图

        //用BFS标记好每个联通分量
        long count = 0;
        boolean[][] visited = new boolean[m][n];  //默认都是false

        Deque<Point> q = new ArrayDeque<>();
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (visited[i][j] == false && graph[i][j] == 1) {
                    visited[i][j] = true;
                    ++count;
                    q.addLast(new Point(i, j));
                    while (q.isEmpty() == false) {
                        Point cur = q.removeFirst();
                        for (int row = 0; row < 8; ++row) {

                                int nextx = dx[row] + cur.x;
                                int nexty = dy[row] + cur.y;
                                //注意理清这些条件判断，稍不留神就出错

                                if (nextx < 0 || nextx >= m || nexty < 0 || nexty >= n) {
                                    continue;
                                }

                                if (visited[nextx][nexty] == true || graph[nextx][nexty] != 1) {
                                    continue;
                                }

                                visited[nextx][nexty] = true;
                                q.addLast(new Point(nextx, nexty));

                        }
                    }
                }
            }
        }


        long res = count * (count - 1) / 2;
        System.out.println(res);

    }
}
