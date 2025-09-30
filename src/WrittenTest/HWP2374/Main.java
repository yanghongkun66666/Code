package WrittenTest.HWP2374;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 我的问题在于，每次把BFS一个节点的相邻节点都加入到队列中，然后取出来的时候才放进路径吗，我不是很懂如何保存中间结果，怎么计算最终结果
 * BFS过程中第一次遇到终点一定是最近的，关键是我怎么知道走了多远？
 * 可以使用广度优先搜索（BFS）算法来解决此问题，通过构建星球间的图结构，逐层探索可达星球，以找到最短路径或判断不可达。
 * 朴素的bfs求最短路的题
 *
 * 算法步骤：
 * 图的表示：使用邻接表来表示图。对于每个通行证（边），将起点和终点加入到相应的列表中。
 * 初始化：创建一个数组 vis 用于标记每个星球是否被访问过，以及记录到达该星球所需的步数。
 * BFS搜索：
 * 初始化队列，将起点放入队列。
 * 进行循环，直到队列为空。在每一层中，遍历当前层的所有星球，并对每个星球的相邻星球进行处理。
 * 如果相邻星球尚未被访问，则更新其访问步数，并将其加入队列。
 * 步数在每一层结束时自增，确保每次都能正确计算到达每个星球的最小步数。
 * 结果输出：检查终点星球的访问步数，如果仍为 0，则表示不可达，输出 -1；否则输出访问步数。
 *
 * 可以通过自定义一个Edge节点，代表从from 到 to 需要的步数
 */
public class Main {

//    static class Edge {
//        int to;
//        int count; //到to需要花费的通行证（money）数量 实际上不需要这个类，因为每一步固定花费都是1
//    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int money = Integer.parseInt(st.nextToken());

        int start = Integer.parseInt(st.nextToken());
        int end = Integer.parseInt(st.nextToken());

        Map<Integer, Set<Integer>> graph = new HashMap<>();  //用来存放邻接表
        for (int i = 0; i < money; ++i) {
            //只能获得到to的money
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            if (graph.containsKey(from) == false) {
                graph.put(from, new HashSet<>());
            }

            graph.get(from).add(to);
            //有向图

        }
        //至此构建好了有向图，在这个有向图上BFS，找到最近路径

        //如果起点不在图中，无法直接访问起点，直接寄了
        if (graph.containsKey(start) == false) {
            System.out.println(-1);
            return;
        }


        int cnt = 0; //记录走过的步数  BFS每扫过一层就是向周围四面八方走了一步，直到找到终点，就是最近路径,所以每遍历完一层，就++cnt
        Set<Integer> visited = new HashSet<>(); //记录访问过的节点，防止成环
//        visited.add(start);
        Deque<Integer> q = new ArrayDeque<>();
        q.addLast(start);
        visited.add(start); //加入队列就标记为已访问
        while (q.isEmpty() == false) {
//            int from = q.removeFirst();
//            Set<Integer> next = graph.get(from);
//            //最后再++cnt，那中间遇到终点怎么办
//            //出队判断一下是否是终点，是重点就返回cnt，现在的含义是从起点走一步能到达的所有节点已经都放进队列了 所以这一层入队 ++cnt 代表从起点到入队的这些节点走了几步路
//
//            if (from == end) {
//                System.out.println(cnt);
//                return;
//            }
//
//            visited.add(from);
//
//            for (int x : next) {
//                if (visited.contains(x) == false) {
//                    q.addLast(x);
//                }
//            }
//
//            ++cnt;
            //一层一层的取节点
            int size = q.size(); //获取同一层的个数
            for (int i = 0; i < size; ++i) {
                int from = q.removeFirst(); //取出队列第一个结点
                if (from == end) {
                    System.out.println(cnt);
                    return;
                }

                visited.add(from);

                if (graph.containsKey(from)) {
                    //说明有后续邻接点
                    for (int x : graph.get(from)) {
                        if (visited.contains(x) == false) {
                            visited.add(x); //标记为已访问 防止重复走
                            q.addLast(x);
                        }
                    }
                }
            }
            ++cnt;  //取完同一层的节点后,说明起始点走一步能到的节点都加进路径中了

        }

        System.out.println(-1);

    }
}
