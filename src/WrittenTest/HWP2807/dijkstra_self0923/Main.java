package WrittenTest.HWP2807.dijkstra_self0923;

import javax.print.DocFlavor;
import java.util.*;
import java.util.logging.Handler;

public class Main {

    static class Edge { //这是用来配合邻接表存放图中某个节点的邻接节点的辅助类，代表着从key节点到to节点的要画的时间的是time
        char to;
        int time;
        public Edge(char to, int time) {
            this.to = to;
            this.time = time;
        }
    }

    static class Node implements Comparable<Node>{ //辅助小根堆构建
        char to;
        int time;
        public Node(char to, int time) {
            this.to = to;
            this.time = time;
        }

        @Override
        public int compareTo(Node o) {
            return this.time - o.time;
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);  //试试改成bufferedreader token的形式

        int stationNums = sc.nextInt(); //站点总数
        char start = sc.next().charAt(0);
        char end = sc.next().charAt(0); //读取到开始站点和结束站点

        Map<Character, List<Edge>> graph = new HashMap<>();//用来存放邻接表类型的图
        Set<Character> memo = new HashSet<>(); //用来存放所有站点，方便初始化minDist，否则就要借助graph来初始化了

        while (sc.hasNext()) {
            String token = sc.next();
            if ("0000".equals(token)) {
                break;
            }

            char u = token.charAt(0);
            char v = sc.next().charAt(0);
            int time = sc.nextInt();
            //读取到一条边
            graph.computeIfAbsent(u, k -> new ArrayList<>()).add(new Edge(v, time));
            //这个函数是没有就创建一个新的arraylist来存放邻接节点
            graph.computeIfAbsent(v, k -> new ArrayList<>()).add(new Edge(u, time));
            //由于是无向图，两边都要存
            memo.add(u);
            memo.add(v);//方便后面初始化dijkstra要用到的minDist
        }

        //至此已经构建好图了，开始dijkstra
        Map<Character, Integer> minDist = new HashMap<>();//代表从源点到下标对应节点的最短时间
        Map<Character, Character> prev = new HashMap<>();//代表最终路径中最短路径中到当前节点的前一个节点是谁

        minDist.put(start, 0); //非常关键
        //初始化minDist
        for (Character c : memo) {
            minDist.put(c, Integer.MAX_VALUE);
        }

        //dijkstra第一步找到离远点最近节点，用小根堆的形式
        PriorityQueue<Node> pq = new PriorityQueue<>();  //堆顶代表目前已知的从源点到某个节点的最短时间
        pq.add(new Node(start, 0));  //初始化小根堆，从start 到 start
        //这里首先加入了一个start->start  最近距离是0，下面path那里用的到哦

        while (pq.isEmpty() == false) {
            //1. 从所有未访问的节点中找到离源点最近的节点
            Node cur = pq.poll();
            char cur_to = cur.to; //第一轮这个cur_to是start  cur_time是0
            int cur_time = cur.time; //已知的从源点到节点cur_to的最短时间

            // 懒惰删除：过期条目跳过
            if (cur_time > minDist.get(cur_to)) {
                //堆中弹出来一定是最短路径，从源点到目标to节点的最短路径，如果弹出来的比最短路径大，那就是冗余路径
                continue;
            }
            //2. 选中当前节点

            if (graph.containsKey(cur_to) == false) {
                //没有邻接边，跳过
                continue;
            }

            //3. 看能否借助当前新选中节点更新邻接点呢
            for (Edge edge : graph.get(cur_to)) {
                //找到cur_to的所有邻接边，尝试是否能通过cur_to更新一下
                int new_time = cur_time + edge.time;  //目前已知的从源节点到cur_to节点的最短时间 + cur_to 到其邻接点的时间
                if (new_time < minDist.get(edge.to)) {
                    //如果可以通过新加入的cur到cur.to进行更新的话
                    pq.offer(new Node(edge.to, new_time)); //已知的从源点到edgeto节点的最短时间
                    prev.put(edge.to, cur_to);  //从源点到edge.to的最短距离是从cur_to过来的
                    minDist.put(edge.to, new_time); //更新一下源点到某个站点的最短距离
                }
            }
        }

        //此时已经构建玩所有的minDist和prev
        if (minDist.get(end) == Integer.MAX_VALUE) {
            System.out.println("无法到达");
        }

        List<Character> path = new ArrayList<>();
        for (Character c = end; c != start; c = prev.get(c)) {
            //从end倒着向前
            path.add(c);
        }

        path.add(start);

        Collections.reverse(path);
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < path.size(); ++i) {
            //好好学这里处理空格的思路
            sb.append(path.get(i));
            if (i != path.size() - 1) {
                sb.append(" ");
            }
        }

        System.out.println(sb.toString());

    }
}
