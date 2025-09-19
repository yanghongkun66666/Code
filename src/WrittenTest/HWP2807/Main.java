package WrittenTest.HWP2807;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.security.interfaces.EdECKey;
import java.util.*;

/**
 * a 地铁站到 e地铁站的最短路径，单源点最短路径，dijkstra算法
 * 同时保存一下经过的最短路径，最后要输出一下
 * Map<Character, List<Edge>> 邻接表的形式，类似于List<List<Edge>>
 */
public class Main {

    static class Edge{
        //邻接表存储源地铁站到目标地铁站的距离
        char to; //目的站点
        int time; //乘坐时间
        public Edge(char to, int time) {
            this.to = to;
            this.time = time;
        }
    }


    static class Node implements Comparable<Node>{
        //存放的是从源站点到目标站点的最短距离，主要用于优先级队列中使用，跟minDist区分开来，这是个中间过程
        char to;
        int time;
        public Node(char to, int time) {
            this.to = to;
            this.time = time;
        }

        @Override
        public int compareTo(Node o) {
            //前减后就是从小到大排序，不知道为啥，就这样用就行
            return this.time - o.time;
            //return o.time - this.time; //这样就是从大到小
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //读取站点总数N
        int n = sc.nextInt();

        //读取出发点s和到达站点t
        char start = sc.next().charAt(0);
        char end = sc.next().charAt(0);

        //构造无向图，邻接表形式，使用hashmap存储每个站的邻接站点
        Map<Character, List<Edge>> graph = new HashMap<>(); //存储源站到目标地铁站之间的距离，双向图
        //邻接矩阵就直接用二维数组就好了
        while (sc.hasNext()) {
            //一直读取，直到读取到终止字符串
            String token = sc.next();
            if ("0000".equals(token)) {
                break;
            }

            char u = token.charAt(0);
            char v = sc.next().charAt(0);
            int time = sc.nextInt();
            //添加u->v的边，同时
            if (graph.containsKey(u) == false) {
                graph.put(u, new ArrayList<>());
            }
            graph.get(u).add(new Edge(v, time));

            //添加v->u的边
            if (graph.containsKey(v) == false) {
                graph.put(v, new ArrayList<>());
            }
            graph.get(v).add(new Edge(u, time));
        }

        //此处已经构建好邻接表


        //初始化距离映射和前驱映射
        Map<Character,Integer> minDist = new HashMap<>(); //代表源站点到目标站点的最短距离
        Map<Character, Character> prev = new HashMap<>();

        //所有涉及到的站点都初始化
        for (Character u : graph.keySet()) {
            minDist.put(u, Integer.MAX_VALUE);
            for (Edge edge : graph.get(u)) {
                if (minDist.containsKey(edge.to) == false) {
                    minDist.put(edge.to, Integer.MAX_VALUE);
                }
            }
        }

        /**
         * // 在 while 循环读取输入时 可以尝试用这种初始化minDist替换一下，我感觉这种好像更直观一些
         * while (sc.hasNext()) {
         *     // ...
         *     char u = token.charAt(0);
         *     char v = sc.next().charAt(0);
         *     // 把所有出现过的节点都加到 set 里
         *     allNodes.add(u);
         *     allNodes.add(v);
         *     // ...
         * }
         *
         * // 初始化时，直接遍历这个 set
         * for (Character node : allNodes) {
         *     minDist.put(node, Integer.MAX_VALUE);
         * }
         */
        //现在每个站点到源站点的最远距离已经初始化为无穷大

        minDist.put(start, 0);  //源站点到源站点之间的最短距离为0


        //使用优先级队列实现Dijkstra算法
        PriorityQueue<Node> pq = new PriorityQueue<Node>(); //pq中存放的就是当前可访问的邻接站点中最短的那个
        pq.add(new Node(start, 0));
        //PriorityQueue<Node> pq: 这是Dijkstra算法从朴素的O(V²)复杂度优化到O(E log V)复杂度的核心。
        // 你正确地使用了它来动态获取当前距离源点最近的节点。

        while (pq.isEmpty() == false) { //这个队列里都是还未被访问的节点
            Node curNode = pq.poll(); //找到当前最近的还未被访问到的站点 优先级队列队顶部
            //这一步相当于 1. 找到最短距离的且未被访问的节点
            char cur = curNode.to; //这是目标站点，离源站点最近的站点
            int cur_time = curNode.time; //这是离目标站点最近的站点需要花的时间
            if (cur_time > minDist.get(cur)) {
                continue; //如果优先级队列中（源站点到目标站点的距离大于已经确定下来的minDist数组源站点到cur站点的距离）那么此时说明cur站点是第二次入队
                //直接跳过，这里替代了visited数组
                //if (current_time > dist.get(cur)) continue; 这一行就是用来：
                //跳过旧的、冗余的路径。
                //确保每个节点只被处理一次（即只用它的最短路径来松弛邻居）
            }


            //这份代码没有使用 visited 数组。它使用了一个巧妙的技巧：if (current_time > dist.get(cur)) continue;。
            //
            //当一个节点被第一次从优先队列中取出时，它的 distance 值一定是最短的。
            // 因为如果它有更短的路径，那个带有更小 distance 的节点早就被优先队列排到前面并取出了。
            //
            //如果同一个节点 cur 被第二次取出，它的 current_time 会大于或等于 dist.get(cur)
            // （因为 dist.get(cur) 已经被更新为最短距离）。此时 if 条件成立，continue 跳过，这个节点就不会被重复处理。
            // 这就间接实现了“只访问一次”的效果。




            //3. 借助新访问的cur的光，尝试更新到别的站点花的最少时间 这部分逻辑完全相同。
            //
            //for (Edge edge : graph.get(cur)) { ... } 遍历 cur 的所有邻居。
            //
            //if (new_time < dist.get(next)) { ... } 进行松弛操作。
            //
            //关键区别: 当它发现一条更短的路径时 (new_time < dist.get(next))，
            // 除了更新 dist 之外，它还会把这个邻居节点 next 重新加入优先队列: pq.add(new Node(next, new_time));。
            //
            //这个**add 操作**是优先队列实现的关键。它告诉优先队列：
            // “嘿，我找到了一个更短的路径，请把这个节点重新排队，以便后续可以处理它。”
            if (graph.containsKey(cur)) {
                //这是第三步3. 借助刚选中的站点，看能否缩短到其他站点的时间
                for (Edge edge : graph.get(cur)) {
                    char to = edge.to; //这是从cur能访问到的站点，邻接表实现
                    int new_time = cur_time + edge.time; //这是相当于minDist[cur] + graph[cur][v]
                    if (new_time < minDist.get(to)) {
                        //如果能够借助cur来更新，那就更新一下最短时间
                        minDist.put(to, new_time); //从源站点到目标站点最短距离更新了
                        prev.put(to, cur); //记录下来从cur到to的路径
                        pq.add(new Node(to, new_time)); //这是将cur站点引入后新加入的能访问到的站点加入到pq判断中
                        //还记得我们pq的语义是从所有可到达的站点中找到时间最短的站点
                    }
                }
            }

        }

        //利用前驱映射回复最短路径
        List<Character> path = new ArrayList<>();
        /**
         * 经过 Dijkstra 算法的运行，我们的 prev 映射最终会是这样的：
         *
         * prev	值	含义
         * B	C	到达 B 的最短路径是从 C 过来的
         * C	A	到达 C 的最短路径是从 A 过来的
         * D	B	到达 D 的最短路径是从 B 过来的
         * A	null	起点没有前驱
         */

        for (char cur = end; cur != start; cur = prev.get(cur)) {
            path.add(cur);
        }

        path.add(start);
        Collections.reverse(path);

        //输出最短路径，各个站点之间用空格分割
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < path.size(); ++i) {
            sb.append(path.get(i));
            if (i != path.size() - 1) {
                sb.append(" ");
            }
        }

        System.out.println(sb.toString());
        sc.close();
    }
}
