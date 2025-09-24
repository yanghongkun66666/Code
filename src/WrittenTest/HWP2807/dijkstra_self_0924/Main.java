package WrittenTest.HWP2807.dijkstra_self_0924;

import java.util.*;

public class Main {

    static class Edge {
        //代表从某站点到to站点需要time
        char to;
        int time;
        public Edge(char to, int time) {
            this.to = to;
            this.time = time;
        }
    }

    static class Node implements Comparable<Node>{ //代表目前已知的从源点到to点的最近时间  用于辅助优先级队列
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
        Scanner sc = new Scanner(System.in);
        int numStations = sc.nextInt();
        char start = sc.next().charAt(0);
        char end = sc.next().charAt(0);
        Set<Character> memo = new HashSet<>();//用来辅助初始化minDist，记录都有哪些节点

        Map<Character, List<Edge>> graph = new HashMap<>();
        while (sc.hasNext()) {
            String token = sc.next();
            if ("0000".equals(token)) {
                break;
            }

            char from = token.charAt(0);
            char to = sc.next().charAt(0);
            int time = sc.nextInt();

            if (graph.containsKey(from) == false) {
                graph.put(from, new ArrayList<>());
            }
            graph.get(from).add(new Edge(to, time));

            if (graph.containsKey(to) == false) {
                graph.put(to, new ArrayList<>());
            }
            graph.get(to).add(new Edge(from, time));

            memo.add(from);
            memo.add(to);
        }

        //此时构建好了邻接表
        //minDist存放源点到当前节点的最短距离
        //prev存放源点到当前节点的最短距离的前一节点
        Map<Character, Integer> minDist = new HashMap<>();
        Map<Character, Character> prev = new HashMap<>();
        Map<Character, Boolean> visited = new HashMap<>();//用来判断顶点是否被访问过，是否定型了
        //优先级队列中弹出来的先判断是否访问过，然后再标记为访问过，因为第一次弹出一定是源点到目标点最近的，其他都是冗余的了

        //初始化minDist
        for (Character c : memo) {
            minDist.put(c, Integer.MAX_VALUE);
            visited.put(c, false);
        }

        minDist.put(start, 0); //当然源点到源点的最短时间是0

        //优先级队列实现dijkstra
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(start, 0)); //加入到优先级队列



        while (pq.isEmpty() == false) {
            Node cur = pq.poll();
            char cur_to = cur.to; //如果是第一个，那么这个cur_to就是start
            int cur_time = cur.time;

            if (visited.get(cur_to) == true) {
                continue;
                //如果访问过，说明最短路径已经固定下来了，下一位
            }

            visited.put(cur_to, true); //标记为访问过  此时已经完成dijkstra前两步

            for (Edge edge : graph.get(cur_to)) {
                //开始dijkstra第三步，借助刚选中的cur_to节点，看源点到其邻接点是否能更短
                char to = edge.to;
                int time = edge.time;
                if (cur_time + time < minDist.get(to)) {
                    minDist.put(to, cur_time + time); //借助刚选中的节点进行缩短更新成功
                    pq.add(new Node(to, cur_time + time)); //目前已知到to的最短距离是cur_time + time
                    prev.put(to, cur_to); //目前已知源点到当前节点最短路的前一个节点是cur_to，是从它过来的
                }
            }
        }

        //此时构建完成从源点到目标点的最短路了
        if (minDist.get(end) == Integer.MAX_VALUE) {
            System.out.println("不可达");
        }

        List<Character> path = new ArrayList<>();
        for (char c = end; c != start; c = prev.get(c)) {
            //从后向前找前序节点
            path.add(c);
        }

        //由于判断到start的时候退出循环了，最后加入一下start
        path.add(start);

        Collections.reverse(path);
        StringBuilder sb = new StringBuilder();//构建最终路径
        for (int i = 0; i < path.size(); ++i) {
            sb.append(path.get(i));
            if (i != path.size() - 1) {
                sb.append(" ");
            }
        }

        System.out.println(sb.toString());

    }
}
