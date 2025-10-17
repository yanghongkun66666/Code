package WrittenTest.HWP2250;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * 安装监控器
 * N皇后问题，暴力搜索一下
 */
public class Main {
    private static int[] cols;
    private static int m;
    private static int n;
    private static int res = 0;

    private static Set<Integer> usedCols = new HashSet<>();
    private static Set<Integer> usedRight = new HashSet<>();
    private static Set<Integer> usedLeft = new HashSet<>(); //标记45度斜线用过
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        m = Integer.parseInt(st.nextToken());
        n = Integer.parseInt(st.nextToken());

        cols = new int[m]; //代表第m行皇后放在col[m]列

        dfs(0);
        System.out.println(res);
    }
    //构建>=i行的合理n皇后方案
    private static void dfs(int i) {
        //边界条件，构建 >= m行的n皇后方案
        if (i == m) {
            //说明[0, m - 1]行都构建成功了
            ++res;
            return;
        }

        //非边界条件
        for (int j = 0; j < n; ++j) {
            if (usedCols.contains(j) == false && usedLeft.contains(i + j) == false &&
            usedRight.contains(i - j) == false) {
                //那么i行的j列可以使用
                usedCols.add(j);
                usedLeft.add(i + j);
                usedRight.add(i - j);
                cols[i] = j;
                dfs(i + 1);  //子问题:构建行下标 >= i + 1的合法方案
                //看下n皇后怎么处理的
                usedRight.remove(i - j);
                usedLeft.remove(i + j);
                usedCols.remove(j);
            }
        }
    }

}
