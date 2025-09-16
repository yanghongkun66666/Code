package WrittenTest.KamaCoderP47.adjMatrix;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        //n代表有n个公交站， m代表有m个站与站之间的路线
        int n = sc.nextInt(), m = sc.nextInt();

        //griph存的是图
        List<List<Integer>> griph = new ArrayList<>();
        for (int i = 0; i <= n; ++i) {
            List<Integer> row = new ArrayList<>(n + 1);

            for (int j = 0; j <= n; ++j) {
                row.add(Integer.MAX_VALUE);
            }
            griph.add(row);
        }
        //至此邻接矩阵构建完成  边与边之间都不可达

        for (int i = 0; i < m; ++i) {
            int p1 = sc.nextInt();
            int p2 = sc.nextInt();
            int val = sc.nextInt();

            griph.get(p1).set(p2, val);
        }
        //此时具体的路径已经联通，具体的图构建完成

        int start = 1;
        int end = n;
        //起点是第一个车站，终点是最后一个车站

        int[] minDist = new int[n + 1];
        //源点到每个点的最短距离
        Arrays.fill(minDist, Integer.MAX_VALUE); //初始化都不可达

        boolean[] visited = new boolean[n + 1];  //标记每个节点是否访问过
        minDist[start] = 0;  //源点到自身的最短距离是0

        for (int i = 1; i <= n; ++i) {
            int minVal = Integer.MAX_VALUE;
            int cur = 1;

            //1. 选取距离源点最近并且还没被访问过的节点
            for (int v = 1; v <= n; ++v) {
                if (visited[v] == false && minDist[v] < minVal) {
                    //这一步是要找到距离源点最近的
                    minVal = minDist[v];
                    cur = v; //标记即将要纳入的节点
                }
            }

            // 2. 标记该节点为被访问
            visited[cur] = true;

            //3. 借助刚访问的节点，又增加了很多可以达到的节点，更新源点借助cur到达他们的最短距离
            // 新选中节点后，基于此更新非访问节点到源点的距离,更新minDist数组
            for (int v = 1; v <= n; ++v) {
                if (visited[v] == false && griph.get(cur).get(v) != Integer.MAX_VALUE && minDist[cur] + griph.get(cur).get(v) < minDist[v]) {
                    //如果v节点没被访问过，同时刚加入的cur节点和v节点之间有边相连，同时源点到v的距离可以借助刚加入的cur缩短，此时就缩短一下
                    minDist[v] = minDist[cur] + griph.get(cur).get(v);
                }
            }
        }

        if (minDist[end] == Integer.MAX_VALUE) {
            System.out.println(-1);
        } else {
            System.out.println(minDist[end]);
        }


    }
}
