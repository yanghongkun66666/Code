package WrittenTest.HWP2806;

import java.util.*;

public class Main {
    // 存储边关系：前序版本 -> 后续版本列表
    static Map<String, List<String>> edge = new HashMap<>();
    // 存储没有前序版本的根节点
    static List<String> roots = new ArrayList<>();
    // 全局变量记录当前最大深度和结果集合
    static int longest = 0;
    static List<String> res = new ArrayList<>();

    // DFS 遍历函数
    public static void dfs(String u, int depth) {
        if (depth > longest) {
            longest = depth;
            res.clear();
            res.add(u);
        } else if (depth == longest) {
            res.add(u);
        }
        // 遍历所有后续版本
        List<String> children = edge.get(u);
        if (children != null) {
            for (String child : children) {
                dfs(child, depth + 1);
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        
        for (int i = 0; i < n; i++) {
            String cur = scanner.next();
            String pre = scanner.next();
            if (pre.equals("NA")) {
                // 当前版本没有前序版本，为根节点
                roots.add(cur);
            } else {
                // 建立前序版本到后续版本的映射关系
                edge.computeIfAbsent(pre, k -> new ArrayList<>()).add(cur);
            }
        }
        scanner.close();
        
        // 从所有根节点开始 DFS 遍历
        for (String root : roots) {
            dfs(root, 1); // 初始深度为1
        }
        
        // 对结果按字典序排序
        Collections.sort(res);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < res.size(); i++) {
            if (i > 0)
                sb.append(" ");
            sb.append(res.get(i));
        }
        System.out.println(sb.toString());
    }
}

