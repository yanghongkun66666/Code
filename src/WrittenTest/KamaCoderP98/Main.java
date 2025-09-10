package WrittenTest.KamaCoderP98;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * https://kamacoder.com/problempage.php?pid=1170
 */
public class Main {

    private static List<List<Integer>> res = new ArrayList<>(); //收集所有有效路径
    private static List<Integer> path = new ArrayList<>(); //单条有效路径

    private static void dfs(List<List<Integer>> graph, int x, int n) {
        //x代表该找x下标的邻接点了，x作为路径中的一员在进入函数前已经加入到路径path中了
        //n代表最终下标，如果x == n就是中止条件，说明找到了一条路径了

        //边界条件
        if (x == n) {
            res.add(new ArrayList<>(path));
            return;
        }

         // 邻接矩阵写法
         for (int i = 1; i <= n; ++i) {
             if (graph.get(x).get(i) == 1) {
                 path.add(i);
                 dfs(graph, i, n);
                 path.remove(path.size() - 1);
             }
         }

//        //非边界条件
//        for (int neighbor : graph.get(x)) {
//            path.add(neighbor);
//            dfs(graph, neighbor, n);
//            path.remove(path.size() - 1); //走到这里说明当前路径中添加neighbor后，如果后续有结果已经存到res了，没结果就回退到这里了
//            //该回溯一下尝试处理下一个neighbor了
//        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt(); //代表有几个节点
        int m = sc.nextInt(); //代表有几条边

        //邻接矩阵  节点为1-n
        List<List<Integer>> graph = new ArrayList<>(n + 1);
        // n + 1行，每一行是一个列表，邻接矩阵的话就要每一行是一个n + 1 长度的列表，然后每个元素都初始化为0
        for (int i = 0; i <= n + 1; i++) {
            //这是遍历每一行,为了初始化每一行为一个长度为n + 1的列表
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j <= n; ++j) {
                row.add(0);
            }
            graph.add(row);
        }
        //至此邻接矩阵初始化完成

        //由于是有向图，因此邻接矩阵不是对称赋值的

        //m代表边的个数
        while (m != 0) {
            int x = sc.nextInt();
            int y = sc.nextInt(); //读取到 x->y
            graph.get(x).set(y, 1);  //注意这个语法
            m--;
        }

        path.add(1); //无论是什么路径一定是从1号节点开始遍历
        dfs(graph, 1, n); //开始遍历

        //输出结果
        if (res.isEmpty() == true) System.out.println(-1);
        for (List<Integer> pa : res) {
            n = pa.size();
            for (int i = 0; i < n - 1; ++i) {
                System.out.print(pa.get(i) + " ");
            }
            System.out.println(pa.get(n - 1));
        }



    }
}
