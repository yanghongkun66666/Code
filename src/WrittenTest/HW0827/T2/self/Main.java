package WrittenTest.HW0827.T2.self;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    private static List<Integer>  bestPartition = new ArrayList<>();

    private static Double bestStd = Double.MAX_VALUE; //最好的标准差

    private static int[] sum;

    private static int m; //代表要分的组数

    private static int n; //代表nums元素总数
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        String[] parts = br.readLine().split("\\s+");
        int[] nums = new int[n];
        for (int i = 0; i < n; ++i) {
            nums[i] = Integer.parseInt(parts[i]);
        }

        sum = new int[n + 1];
        for (int i = 1; i < n + 1; ++i) {
            sum[i] = sum[i - 1] + nums[i - 1];  //前缀和优化，可以快速获取取件和
        }

        dfs(0, 0, new ArrayList<>(), new ArrayList<>());

        for (int x : bestPartition) {
            System.out.print(x + " ");
        }

    }

    private static void dfs(int i, int k, List<Integer> curSum, List<Integer> curPar) {
        //原问题：对>=i的元素进行分组
        //当前操作，枚举当前元素j后加逗号进行分组
        //子问题 对 >= j + 1的元素进行分组

        //边界条件
        if (k == m - 1) {
            //说明已经插入了m - 1个逗号，分成了m组，最后一组就是i...n-1了
            int tmpSum = getSum(i, n - 1);
            curSum.add(tmpSum);
            curPar.add(n - i);

            double avg = curSum.stream().mapToDouble(x->x).sum() / curSum.size();
            //stream流获取平均数

            double curStd = 0;
            for (int num : curSum) {
                curStd += (num - avg) * (num - avg);
            }

            curStd /= 4;
            curStd = Math.sqrt(curStd);

            if (curStd < bestStd) {
                bestStd = curStd;
                bestPartition = new ArrayList<>(curPar);
            }

            curSum.remove(curSum.size() - 1);
            curPar.remove(curPar.size() - 1);

            return;

        }  else {
            for (int j = i; j < n; ++j) {
                int tmpSum = getSum(i, j);
                curSum.add(tmpSum);
                curPar.add(j - i + 1);

                dfs(j + 1, k + 1, curSum, curPar);

                //走到这里说明选择当前位置划分后续所有可能已经搜索完成了
                curPar.remove(curPar.size() - 1);
                curSum.remove(curSum.size() - 1);
            }
        }

    }

    private static int getSum(int left, int right) {
        //传入要求和的left right区间，然后返回区间和
        return sum[right + 1] - sum[left];
        //[0,right] - [0, left - 1]  [left, right]
    }
}
