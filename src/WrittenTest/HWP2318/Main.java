package WrittenTest.HWP2318;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
//        Scanner sc = new Scanner(System.in);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        //这是模块个数,我打算从1 到 n放弃0号下标
        Map<Integer, List<Integer>> graph = new HashMap<>(); //邻接表存储图之间的关系
        int[] inDegree = new int[n + 1];

        for (int i = 1; i <= n; ++i) {
            graph.put(i, new ArrayList<>()); //输入的是当前行依赖的模块，也就是当前行是被指向的那个，注意箭头的指向不要搞乱了
        }
        for (int i = 1; i <= n; ++i) {
            st = new StringTokenizer(br.readLine());
            int count = Integer.parseInt(st.nextToken()); //这是当前模块依赖的模块个数
            for (int j = 0; j < count; ++j) {
                int from = Integer.parseInt(st.nextToken());
                graph.get(from).add(i);
            }
            inDegree[i] = count; //这是这个模块依赖的模块个数
        }

        //这样邻接表就构建完成了


        //开始拓扑排序
        int cnt = 0;
        int[] depth = new int[n + 1];//代表第几批开始初始化，最终结果是取个最大值 + 1，因为 0 代表第一批初始化
        Deque<Integer> q = new ArrayDeque<>();
        for (int i = 1; i <= n; ++i) {
            if (inDegree[i] == 0) {
                q.addLast(i);
            }
        }

        while (q.isEmpty() == false) {
            int cur = q.removeFirst();
            cnt++; //统一从队列取出来计数，从队列拿出来一定是入度为0的
            for (int adj : graph.get(cur)) {
                //获取所有邻接节点
                --inDegree[adj];
                if (inDegree[adj] == 0) {
                    q.addLast(adj);
                    depth[adj] = depth[cur] + 1;
                }
            }
        }

        //拓扑排序完成
        if (cnt == n) {
            System.out.println(Arrays.stream(depth).max().getAsInt() + 1);
            //stream流真好用
        } else {
            System.out.println(-1);
        }

    }
}
