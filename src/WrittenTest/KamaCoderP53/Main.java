package WrittenTest.KamaCoderP53;

import java.util.*;

/**
 * 最小生成树算法prim算法
 * 得到所有节点的最小联通子图
 *
 * prrm算法
 * 1. 选距离生成树最近节点
 * 2. 最近节点加入生成树
 * 3. 更新非生成树节点到生成树的距离（即更新minDist数组）
 * minDist数组记录了所有非生成树节点距离生成树的最小距离
 */
public class Main {
    public static void main(String[] args) {
        //n代表有n个公交站，m代表有m条站与站之间的路线
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(), m = sc.nextInt();

        //grid存的是图，这里存的是站与站台之间的邻接矩阵
        int[][] griph = new int[n + 1][n + 1];
        for (int i = 0; i <= n; ++i) {
            Arrays.fill(griph[i], Integer.MAX_VALUE);
        }

        for (int i = 0; i < m; ++i) {
            int p1 = sc.nextInt();
            int p2 = sc.nextInt();
            int val = sc.nextInt();

            griph[p1][p2] = val;
        }
        //把图的边已经构建好了

        int start = 1;
        int end = n;
        //起点是第一个车站，终点是最后一个车站

        int[] minDist = new int[n + 1]; //存储从源点到每个节点的最短距离
        Arrays.fill(minDist, Integer.MAX_VALUE);

        //记录每个节点是否被访问过
        boolean[] visited = new boolean[n + 1];

        minDist[start] = 0; //起点到自身距离为0

        for (int i = 1; i <= n; ++i) {  //遍历所有节点
            //一旦某个点被“选出来”，它的最短路就已经是全局最优的。
            //每次循环能够确定一个点
            int minVal = Integer.MAX_VALUE;
            int cur = 1;

            //1. 选取距离源点最近并且还没被访问过的节点
            for (int v = 1; v <= n; ++v) {
                if (visited[v] == false && minDist[v] < minVal) {
                    minVal = minDist[v];
                    cur = v;
                }
            }

            //2. 标记该节点已经被访问
            visited[cur] = true;

            //3. 新选中节点后，基于此更新非访问节点到源点的距离,更新minDist数组
            for (int v = 1; v <= n; ++v) {
                if (visited[v] == false && griph[cur][v] != Integer.MAX_VALUE && minDist[cur] + griph[cur][v] < minDist[v]) {
                    //更新每个借助cur能缩短路径的节点
                    minDist[v] = minDist[cur] + griph[cur][v];
                }
            }

        }

        if (minDist[end] == Integer.MAX_VALUE) {
            System.out.println(-1); //不能到达终点
        } else {
            System.out.println(minDist[end]); //到达终点最短路径
        }

    }

}
