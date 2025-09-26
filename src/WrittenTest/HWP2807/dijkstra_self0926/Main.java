package WrittenTest.HWP2807.dijkstra_self0926;

import java.util.*;

public class Main {

    private static class Edge { //辅助邻接表的构建
        char to;
        int time;
        public Edge(char to, int time) {
            this.to = to;
            this.time = time;
        }
    }

    private static  class Node implements Comparable<Node>{  //辅助优先级队列的构建  代表目前已知的从源点到目标to点的最短距离
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

        int n = sc.nextInt();
        char start = sc.next().charAt(0);
        char end = sc.next().charAt(0);
        Map<Character, List<Edge>> graph = new HashMap<>();
        Set<Character> s = new HashSet<>();

        while (true) {
            String token = sc.next();
            if ("0000".equals(token)) {
                break;
            }
            char from = token.charAt(0);
            char to = sc.next().charAt(0);
            s.add(from);
            s.add(to);

            int time = sc.nextInt();

            if (graph.containsKey(from) == false) {
                graph.put(from, new ArrayList<>());
            }

            graph.get(from).add(new Edge(to, time));

            if (graph.containsKey(to) == false) {
                graph.put(to, new ArrayList<>());
            }

            graph.get(to).add(new Edge(from, time));
            //无向图

        }
        //此时构建好了图
        Map<Character,Integer> minDist = new HashMap<>(); //从源点到当前点的最短距离


        for (Character c : s) {
            minDist.put(c, Integer.MAX_VALUE);
        }

        minDist.put(start, 0);  //重要初始化，从start到start最远距离为0


        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.offer(new Node(start, 0));
        Map<Character, Boolean> visited = new HashMap<>();
        Map<Character, Character> prev = new HashMap<>(); //存放前驱节点

        while (pq.isEmpty() == false) {
            Node cur = pq.poll();
            char cur_to = cur.to;
            int cur_time = cur.time;
            //弹出来一定是最短的路径，直接标记为visited
            if (visited.containsKey(cur_to) == true) {
                continue;
            }

            visited.put(cur_to, true);

            for (Edge edge : graph.get(cur_to)) {
                if (cur_time + edge.time < minDist.get(edge.to)) {
                    //可以借助刚选中的cur_to来更新刚选中的节点的邻接点
                    minDist.put(edge.to, cur_time + edge.time);
                    pq.add(new Node(edge.to, cur_time + edge.time));
                    prev.put(edge.to, cur_to);
                }
            }
        }

        //此时minDist计算完毕
        if (minDist.get(end) == Integer.MAX_VALUE) {
            System.out.println("不可达");
            return;
        }

        List<Character> path = new ArrayList<>();
        for (Character c = end; c != start; c = prev.get(c)) {
            path.add(c);
        }

        path.add(start);
        Collections.reverse(path);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < path.size(); ++i) {
            sb.append(path.get(i));
            if (i != path.size() - 1) {
                sb.append(" ");
            }
        }

        System.out.println(sb.toString());

    }
}
