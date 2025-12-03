package WrittenTest.HWP3554;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static class ComponentInfo {
        int total; //当前权重网络总体权重
        int bestMaxWeight; //当前权重网络中最大的节点权重
        String bestName;  //当前分量重最大节点名称
        public ComponentInfo(int total, int bestMaxWeight, String bestName) {
            this.total = total;
            this.bestMaxWeight = bestMaxWeight;
            this.bestName = bestName;
        }
    }

    private static Map<String, Integer> weight = new HashMap<>();

    private static Map<String, List<String>> graph = new HashMap<>();

    private static Set<String> visited = new HashSet<>(); //用来标记是否访问过


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        //读节点
        for (int i = 0; i < n; ++i) {
            String[] parts = br.readLine().split("\\s+");
            String name = parts[0];
            int w = Integer.parseInt(parts[1]);

            weight.put(name, w);
            graph.put(name, new ArrayList<>());
        }

        //读边
        int m = Integer.parseInt(br.readLine());
        for (int i = 0; i < m; ++i) {
            String[] parts = br.readLine().split("\\s+");
            graph.get(parts[0]).add(parts[1]);
            graph.get(parts[1]).add(parts[0]);
        }

        int bestTotal = -1;
        String bestName = null; //权重最大的联通网络中权重最大的节点名称

        for (String node : weight.keySet()) {
            if (visited.contains(node) == false) {
                //找到一个新的分量
                ComponentInfo info = dfs(node);
                //获取到当前联通分量的汇总信息
                if (info.total > bestTotal) {
                    bestTotal = info.total;
                    bestName = info.bestName;
                }
            }
        }

        System.out.println(bestName + " " + bestTotal);

    }


    private static ComponentInfo dfs(String node) {
        //原问题：从当前联通分量中获取汇总信息
        visited.add(node);
        String bestName = node;
        int bestWeight = weight.get(node);
        int total = bestWeight;

        for (String nei : graph.get(node)) {
            if (visited.contains(nei) == false) {
                ComponentInfo info = dfs(nei);
                //获取到nei的汇总信息，把整个分量里面的最大值找出来
                total += info.total;

                if (info.bestMaxWeight > bestWeight) {
                    bestWeight = info.bestMaxWeight;
                    bestName = info.bestName;
                }
            }
        }

        return new ComponentInfo(total, bestWeight, bestName);
    }
}
