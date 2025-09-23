package WrittenTest.HWP2807.dfs_self0923;

import java.util.*;

public class Main {

    static class Edge{  //从某站到to站的时间time
        char to;
        int time;
        public Edge(char to, int time) {
            this.to = to;
            this.time = time;
        }
    }

    private static Map<Character, List<Edge>> graph = new HashMap<>(); //main初始化，dfs用
    private static List<Character> path = new ArrayList<>();  //这是临时路径，当前遍历过程中保存的路径
    private static List<Character> bestPath = new ArrayList<>(); //最终结果路径
    private static Integer bestTime = Integer.MAX_VALUE;
    private static Set<Character> visited = new HashSet<>(); //用来记录当前遍历路径中已经访问过的节点，防止成环


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); //站点总个数

        char start = sc.next().charAt(0);
        char end = sc.next().charAt(0);

        while (sc.hasNext()) {
            String token = sc.next();
            if ("0000".equals(token)) {
                break;
            }

            char u = token.charAt(0);
            char v = sc.next().charAt(0);
            int time = sc.nextInt();

            if (graph.containsKey(u) == false) {
                graph.put(u, new ArrayList<>());
            }
            graph.get(u).add(new Edge(v, time));

            if (graph.containsKey(v) == false) {
                graph.put(v, new ArrayList<>());
            }
            graph.get(v).add(new Edge(u, time));

        }

        //构建好了图的邻接表

        //开始dfs搜索最短路径  传入cur当前遍历到的节点,然后开始回溯三部曲，灵神三部曲
        //dfs搜索从cur到end的最短时间对应的路径
        path.add(start);
        dfs(start, end,0);

        //此时或许已经搜索到了start 到 end 的最短路径
         if(bestPath.size() == 0) {
             System.out.println("不可达");
             return;
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
        if (cur == end) {
            //边界条件，找到end节点，因此当前的path就是一个结果路径了，可能不是最短，上一层已经把cur加入到了路径中
            if (curTime < bestTime) {
                bestPath = new ArrayList<>(path);
                bestTime = curTime;
            }
            return; //找到一个结果，不管挑没挑战成功，也该回退上一层了
        }

        if (curTime >= bestTime) {
            //剪枝，如果当前时间已经大于最佳时间了，同时还没搜索到最终结果，就提前退出，这条路径不合适
            return;
        }

        if (graph.get(cur) == null) {
            //如果当前节点没有邻接节点，直接退出，也算是一个边界条件
            return;
        }

        //非边界条件  原问题 当前操作 子问题
        //原问题：从cur节点往后搜索到达end的最短路径  cur已经在上一层加入路径，加入选择了，回溯到最开始就是path.add(start); 然后才开始的dfs
        //当前操作：从cur节点的所有邻接节点中随机选择一个节点，枚举每个未选择的邻接节点加入到当前遍历路径中
        //子问题：从cur选中的邻接点cur_to开始往后搜索到达end的最短路径 dfs传入就是cur_to，而cur_to已经上一层加入了路径，加入了选择
        for (Edge edge : graph.get(cur)) {
            char cur_to = edge.to;//这是cur->cur_to的边
            int time = edge.time;  //这是cur->cur_to的边要花的时间

            if (visited.contains(cur_to)) {
                continue;
                //说明当前正在搜索的路径中已经加入过一次cur_to节点了,尝试加入别的节点
            }

            path.add(cur_to); //进入下一层的时候就从cur_to开始向后搜，这里已经将cur_to加入到path中了
            curTime += time;
            visited.add(cur_to); //防止成环，把已经存在于路径中的记录一下
            dfs(cur_to, end, curTime);

            //回溯，走到这里说明选中cur的其中一个邻接点cur_to的后续所有可能情况都搜索到了，可能的结果也保存下来了
            //此时开始尝试cur的其他邻接点了
            //回溯  恢复现场
            path.remove(path.size() - 1);
            curTime -= time;
            visited.remove(cur_to);
        }
    }
}
