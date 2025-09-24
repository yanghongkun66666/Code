package WrittenTest.HWP2807.dfs_self_buffered_reader0924;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;

public class Main {

    static class Edge {
        char to;
        int time;
        Edge(char to, int time) { this.to = to; this.time = time; }
    }

    // 保持原来的全局状态
    private static final Map<Character, List<Edge>> graph = new HashMap<>();
    private static final List<Character> path = new ArrayList<>();
    private static List<Character> bestPath = new ArrayList<>();
    private static int bestTime = Integer.MAX_VALUE;
    private static final Set<Character> memo = new HashSet<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 第一行：n start end
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken()); // 用不用随意，这里保留读取
        char start = st.nextToken().charAt(0);
        char end   = st.nextToken().charAt(0);

        // 读边：每行一个 “u v time”，直到遇到 "0000"
        while (true) {
            String line = br.readLine();
            if (line == null) break;
            line = line.trim();
            if (line.isEmpty()) continue;
            if (line.equals("0000")) break;

            st = new StringTokenizer(line);
            char from = st.nextToken().charAt(0);
            char to   = st.nextToken().charAt(0);
            int time  = Integer.parseInt(st.nextToken());

            graph.computeIfAbsent(from, k -> new ArrayList<>()).add(new Edge(to, time));
            graph.computeIfAbsent(to,   k -> new ArrayList<>()).add(new Edge(from, time)); // 无向图
        }

        // DFS 启动
        path.add(start);
        memo.add(start);
        dfs(start, end, 0);

        if (bestTime == Integer.MAX_VALUE) {
            System.out.println("不可达");
            return; // 不再打印路径
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bestPath.size(); ++i) {
            sb.append(bestPath.get(i));
            if (i != bestPath.size() - 1) sb.append(' ');
        }
        System.out.println(sb.toString());
    }

    private static void dfs(char cur, char end, int curTime) {
        if (cur == end) { // 命中终点
            if (curTime < bestTime) {
                bestTime = curTime;
                bestPath = new ArrayList<>(path);
            }
            return;
        }
        if (!graph.containsKey(cur)) return; // 没有邻接边
        if (curTime >= bestTime) return;     // 剪枝

        for (Edge e : graph.get(cur)) {
            char to = e.to;
            int w = e.time;
            if (memo.contains(to)) continue; // 防环

            // 选择
            memo.add(to);
            path.add(to);

            // 递归
            dfs(to, end, curTime + w);

            // 回溯
            path.remove(path.size() - 1);
            memo.remove(to);
        }
    }
}
