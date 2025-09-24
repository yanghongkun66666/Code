package WrittenTest.HWP2807.dfs_self_0924;

import javax.swing.plaf.IconUIResource;
import java.util.*;

public class Main {

    static class Edge { //代表某个站点到to站点的时间  辅助构建邻接表
        char to;
        int time;

        public Edge(char to, int time) {
            this.to = to;
            this.time = time;
        }
    }
    private static Map<Character, List<Edge>> graph = new HashMap<>();

    private static List<Character> path = new ArrayList<>(); //记录当前搜索路径
    private static List<Character> bestPath = new ArrayList<>(); // 记录最短路径
    private static Integer bestTime = Integer.MAX_VALUE; //记录start 到 end最短时间

    private static Set<Character> memo = new HashSet<>();
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        char start = sc.next().charAt(0);
        char end = sc.next().charAt(0);

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


        }

        //此时邻接表已经构建完成，开始dfs了
        //我们包括dfs内部逻辑也是先add当前节点，然后dfs内部处理下一个节点
        path.add(start);
        memo.add(start); //防止dfs搜索过程中成环，栈溢出
        dfs(start, end, 0);

        if (bestTime == Integer.MAX_VALUE) {
            System.out.println("不可达");
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bestPath.size(); ++i) {
            sb.append(bestPath.get(i));
            if (i != bestPath.size() - 1) {
                sb.append(" ");
            }
        }

        System.out.println(sb.toString());

    }

    private static void dfs(char cur, char end, int curTime) {
        //定义dfs为搜索从cur 到 end的最短时间
        //原问题从cur到end的最短时间
        //当前操作，从cur的相邻的边中选择一条边
        //子问题从选择后的节点到end的最短时间
        //需要定义一个path存放当前搜索到的路径
        //需要定义一个bestPath存放最佳路径
        //需要定义一个bestTime存放最短时间

        //边界条件  如果上一层已经将end加入到最终节点了(这一层start就是end)
        if (cur == end) {
            if (curTime < bestTime) {
                bestTime = curTime;
                bestPath = new ArrayList<>(path);
            }
            return;
        }

        //如果当前层start 已经到了图的边界，也该return了
        if (graph.get(cur) == null) {
            return;
        }

        //剪枝，当前时间如果超过了最短时间，不用搜了，这条路肯定不行
        if (curTime > bestTime) {
            return;
        }

        //路径中的节点不要重复加入，我们是要搜索一个路径，不是要一个环，stackoverflow就来自这里


        for (Edge edge : graph.get(cur)) {
            //非边界条件当前操作，选择一条边
            char to = edge.to;
            int time = edge.time;

            if (memo.contains(to)) {
                continue;
                //代表当前path中选中过这个to节点了
            }

            path.add(to);
            memo.add(to);
            curTime += time;

            //子问题，从to 到 end的最短时间
            dfs(to, end, curTime);

            //走到这里说明to 到 end所有可能都搜索了一遍了，结果也记录了，边界也返回了，该尝试start的其他邻接点了
            path.remove(path.size() - 1);
            memo.remove(to);
            curTime -= time;
        }
    }
}
