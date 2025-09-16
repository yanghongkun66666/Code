package WrittenTest.KamaCoderP47.adjList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    static class Edge{
        Integer to;
        Integer value;
        public Edge(Integer to, Integer value) {
            this.to = to;
            this.value = value;
        }
    }
    public static void main(String[] args) {
        //邻接表实现
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt(), m = sc.nextInt(); //n代表公交站的个数   m代表站点与站点之间的线路

        //邻接表 graph存的是图
        List<List<Edge>> graph = new ArrayList<>();
        for (int i = 0; i <= n; ++i) {
            List<Edge> row = new ArrayList<>();
            graph.add(row);
        }


        //开始构建邻接表
        for (int i = 0; i < m; ++i) {
            int p1 = sc.nextInt();
            int p2 = sc.nextInt();
            int val = sc.nextInt();

            graph.get(p1).add(new Edge(p2, val));
        }
        //此时构建好邻接表

        int start = 1;
        int end = n;
        //定义好源节点和目标节点

        int[] minDist = new int[n + 1]; //源点到每个节点的最短距离
        Arrays.fill(minDist, Integer.MAX_VALUE);

        boolean[] visited = new boolean[n + 1]; //标记每个节点是否被访问过
        minDist[start] = 0; //源点到源点的最短距离是0

        for (int i = 1; i <= n; ++i) {
            int minValue = Integer.MAX_VALUE;
            int cur = -1;

            //找到没访问的，最近的节点 遍历找到最近的没访问过的节点
            for (int v = 1; v <= n; ++v) {
                if (visited[v] == false && minDist[v] < minValue) {
                    minValue = minDist[v];
                    cur = v;
                }
            }

            // 如果找不到节点了，说明剩下的节点都不可达，可以直接结束
            if (cur == -1) {
                break;
            }

            //2. 标记为访问过
            visited[cur] = true;

            //3. 借助新访问的cur，来尝试缩短剩下未访问的节点到源点的路径
            // 遍历所有从 cur 出发的边
            for (Edge edge : graph.get(cur)) {
                if (visited[edge.to] == false && minDist[cur] + edge.value < minDist[edge.to]) {
                    //遍历cur所有邻接点,非常直观
                    minDist[edge.to] = minDist[cur] + edge.value;
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
