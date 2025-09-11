package LeetCode.p797_allPathsSourceTarget;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    private List<List<Integer>> res = new ArrayList<>();
    private List<Integer> path = new ArrayList<>();

    private void dfs(int[][] graph, int i) {
        if (i == graph.length - 1) {
            res.add(new ArrayList<>(path));
            return;
        }

        for (int neighbor : graph[i]) {
            path.add(neighbor);
            dfs(graph, neighbor);
            path.remove(path.size() - 1);
        }
    }
    public List<List<Integer>> allPathsSourceTarget(int[][] graph) {
        path.add(0);
        dfs(graph, 0);
        return res;
    }
}
