package WrittenTest.HWP2306;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

/**
 * 垃圾站初始化为0，minDist代表到小区的最近距离，如果不为0，那么就已经有个垃圾站到该小区最近了，就不用更新
 * 将所有垃圾站的距离初始化为0，并加入队列中。用广度优先搜索算法逐层向外扩展，并用一个额外数组记录到达每个点的最短距离。如果一个点有值，说明某一个垃圾站离该点更近，则不在使用当前点进行该方向的扩展。
 * 扫描所有小区，累加值
 * 初始化：创建一个距离矩阵d，用-1表示不可到达，垃圾站的距离初始化为0，并将所有垃圾站的位置加入队列。
 * 广度优先搜索：从队列中的垃圾站开始，逐层向外扩展，更新每个可到达点的最短距离。如果一个点的距离已经被更新，表示该点到达的距离更短，则不再更新。
 * 计算总和：遍历所有小区，累加它们的距离，如果某个小区不可达则其值视为0。
 * 以垃圾站作为源点一圈一圈向外搜索
 * 4 4
 * 1 2 -1 1
 * 2 0 2 0
 * 2 2 -1 2
 * 1 2 1 1
 */
public class Main {
    private static int m, n;
    private static int[][] matrix; //表示输入数据，垃圾站小区和障碍物
    private static int[][] d; //表示每个位置到最近垃圾站的距离

    private static int[] dx = {0, 0, -1, 1};
    private static int[] dy = {1, -1, 0, 0};

    private static int solve() {
        int res = 0;
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (matrix[i][j] == 1 && d[i][j] != -1) {
                    res += d[i][j];
                }
            }
        }

        return res;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        m = sc.nextInt();
        n = sc.nextInt();
        matrix = new int[m][n];
        d = new int[m][n];

        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                matrix[i][j] = sc.nextInt();
                d[i][j] = -1;  //初始化距离为-1
            }
        }


        Deque<int[]> queue = new ArrayDeque<>();
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (matrix[i][j] == 0) {
                    queue.add(new int[]{i, j});
                    d[i][j] = 0;
                }
            }
        }

        while (queue.isEmpty() == false) {
            int[] cur = queue.removeFirst();
            int x = cur[0];
            int y = cur[1];
            for (int i = 0; i < 4; ++i) {
                int nx = cur[0] + dx[i];
                int ny = cur[1] + dy[i];

                if (nx >= 0 && nx < m && ny >= 0 && ny < n
                        && d[nx][ny] == -1 && matrix[nx][ny] != -1) {
                    //这个小区没有访问过同时这个位置不是障碍物
                    queue.addLast(new int[]{nx, ny});
                    d[nx][ny] = d[x][y] + 1;
                }
            }
        }

        System.out.println(solve());
    }
}
